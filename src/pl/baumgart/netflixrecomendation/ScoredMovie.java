package pl.baumgart.netflixrecomendation;

import java.text.DecimalFormat;

public class ScoredMovie implements Comparable<ScoredMovie> {
    public Movie movie;
    public double score;

    public ScoredMovie(Movie movie, double score) {
        this.movie = movie;
        this.score = score;
    }

    @Override
    public int compareTo(ScoredMovie o) {
        if (score < o.score)
            return -1;
        if (score == o.score)
            return 0;
        return 1;
    }
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.##");
        return movie.title + "    " + movie.year + "    " + df.format(score);
    }
}
