import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class MoviesToEmail extends Movie {
    String url;
    String trailer;
    String rT;

    MoviesToEmail(){
        super();
        setURL();
        setTrailer();
        setRT();
    }

    MoviesToEmail(String title, int length, String genre, double rating, int overallScore, String description){
        super(title, length, genre, rating, overallScore, description);
        setURL();
        setTrailer();
        setRT();
    }

    private void setURL (){
        this.url = "https://www.google.com/search?q=" + this.title.replaceAll(" ", "+");
    }

    private void setTrailer(){
        try {
            url = url + "+trailer";
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            this.trailer = doc.select("#rso > div:nth-child(1) > div " +
                    "> div.EyBRub.kp-blk.Wnoohf.OJXvsb > div.xpdopen > div.ifM9O > div > div > div > div.twQ0Be > a")
                    .attr("href");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setRT(){
        try {
            url = url + "+rotten+tomato";
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            this.rT = doc.select("#rso > div:nth-child(1) > div > div > div > div.r > a")
                    .attr("href");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getRT(){
        return this.rT;
    }

    public String getTrailer(){
        return this.trailer;
    }

    public String formatMovieString(){
        return getTitle() + "\nIMDB Rating: " + getRating() + "\t\tOverall Rating: " + getOverallScore()
                + "\n" + getLength() + " min\nGenre: " + getGenre()
                + "\n" + getDescription() + "\nTrailer: " + getTrailer()
                + "\nRotten Tomato Link: " + getRT() + "\n\n";
    }

}
