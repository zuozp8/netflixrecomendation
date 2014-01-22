package pl.baumgart.netflixrecomendation;

import org.la4j.LinearAlgebra;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class RecomendationUtility {

    //User should give more own scores than number of features
    public static final int NUMBER_OF_FEATURES_USED = 5;

    private ArrayList<Movie> movies = new ArrayList<>();

    public RecomendationUtility() {
        readData();
    }

    public ArrayList<ScoredMovie> recommend(ArrayList<ScoredMovie> inputScores) {
        Basic2DMatrix data = new Basic2DMatrix(inputScores.size(), NUMBER_OF_FEATURES_USED);
        Vector targetScores = new BasicVector(inputScores.size());

        for (int i = 0; i < inputScores.size(); i++) {
            for (int j = 0; j < NUMBER_OF_FEATURES_USED; j++) {
                data.set(i, j, inputScores.get(i).movie.features[j]);
            }
            targetScores.set(i, inputScores.get(i).score - inputScores.get(i).movie.avg);
        }

        Vector usersCoefficients = data.transpose().multiply(data)
                .withInverter(LinearAlgebra.InverterFactory.GAUSS_JORDAN).inverse()
                .multiply(data.transpose()).multiply(targetScores);

        ArrayList<ScoredMovie> result = new ArrayList<>();
        for (Movie movie : movies) {
            double score = usersCoefficients.toRowMatrix().multiply(new BasicVector(movie.features)).sum() + movie.avg;
            result.add(new ScoredMovie(movie, score));
        }

        return result;
    }

    private void readData() {
        try (FileInputStream titlesInput = new FileInputStream("data/movieTitles.txt");
             FileInputStream featuresInput = new FileInputStream("data/movieCoeffs.txt");
             FileInputStream avgsInput = new FileInputStream("data/movieAvgs.txt");
             Scanner titlesScanner = new Scanner(titlesInput);
             Scanner featuresScanner = new Scanner(featuresInput);
             Scanner avgsScanner = new Scanner(avgsInput)
        ) {
            featuresScanner.useLocale(Locale.US);
            avgsScanner.useLocale(Locale.US);

            //ignore next lines
            featuresScanner.nextLine();
            avgsScanner.nextLine();

            while (titlesScanner.hasNextLine()) {
                Movie movie = new Movie();

                movie.title = titlesScanner.nextLine();
                //TODO process title

                avgsScanner.next(); // ignore movie number
                movie.avg = avgsScanner.nextDouble();

                movie.features = new double[NUMBER_OF_FEATURES_USED];
                featuresScanner.next(); // ignore movie number
                for (int i = 0; i < NUMBER_OF_FEATURES_USED; i++)
                    movie.features[i] = featuresScanner.nextDouble();
                featuresScanner.nextLine();

                movies.add(movie);
            }

        } catch (IOException e) {
            throw new RuntimeException("Problem reading data", e);
        }
    }

    /**
     * List of movies with names, features and average score
     *
     * @return list of movies
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
