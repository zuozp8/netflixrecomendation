package pl.baumgart.netflixrecomendation;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        RecomendationUtility recomendationUtility = new RecomendationUtility();

        ArrayList<ScoredMovie> usersInput = new ArrayList<>();
        /*                 --- DRAGON BALL RECOMENDATION ---
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(103), 5));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(120), 4));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(130), 3));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(110), 5));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(126), 4));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(150), 5));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(510), 2));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(410), 3));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(210), 1));*/

        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(1643), 3));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(2320), 4));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(4430), 3));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(1550), 5));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(1326), 4));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(1750), 5));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(5470), 2));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(4880), 3));
        usersInput.add(new ScoredMovie(recomendationUtility.getMovies().get(2440), 1));

        ArrayList<ScoredMovie> scoredMovies = recomendationUtility.recommend(usersInput);
        Collections.sort(scoredMovies);
        Collections.reverse(scoredMovies);

        for (int i = 0; i < 10; i++) {
            System.out.println(scoredMovies.get(i).score + "\t" + scoredMovies.get(i).movie.title);
        }

    }

}
