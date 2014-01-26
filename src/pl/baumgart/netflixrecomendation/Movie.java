package pl.baumgart.netflixrecomendation;

public class Movie {
    public int id;
    public String title;
    public String year;
    public double avg;
    public double[] features;

    public String toString()
    {
        return title;
    }
}