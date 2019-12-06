public class Movie {
    protected String title;
    protected int length;
    protected String genre;
    protected double rating;
    protected int overallScore;
    protected String description;

    // default constructor
    Movie(){
        this("", 0, "", 0.0, 0, "");
    }

    Movie(String title, int length, String genre, double rating, int overallScore, String description){
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.rating = rating;
        this.overallScore = overallScore;
        this.description = description;
    }

    public String getTitle(){
        return this.title;
    }

    public int getLength(){
        return this.length;
    }

    public String getGenre(){
        return this.genre;
    }

    public double getRating(){
        return this.rating;
    }

    public int getOverallScore(){
        return this.overallScore;
    }

    public String getDescription(){
        return this.description;
    }

}
