import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MovieViewer {

    public static void main(String[] args) {

        System.out.println("Running...");
        ArrayList<Movie> allMovies = viewMovies();
        ArrayList<MoviesToEmail> emailMovies = checkScore(allMovies);

//        testArr(emailMovies);
        EmailSender email = new EmailSender();
        email.send(emailMovies);
    }

    private static ArrayList<Movie> viewMovies(){
        System.out.println("Looking Up In Theaters Now - Box Office Top Ten  ");
        Document doc;
        ArrayList<Movie> objArr = new ArrayList<Movie>();
        try {
            doc = Jsoup.connect("https://www.imdb.com/movies-in-theaters/?ref_=cs_inth").get();
            Elements row = doc.select("#main  > div > div:nth-child(5)");
            String[] array = row.text().substring(38,row.text().length() - 1).split("Watch Trailer Get Tickets ");
            parse(objArr, array);
        } catch (IOException e){
            e.printStackTrace();
        }

        return objArr;
    }

    private static void parse(ArrayList<Movie> objArr, String[] array){
        for (int i = 0 ; i < array.length; i++){
            String elem = array[i];

            String title = elem.substring(0, elem.indexOf(" (2019)"));
            int length = Integer.parseInt(elem.substring(elem.indexOf("(2019)") + 7,
                    elem.indexOf(" min")));
            String genre = elem.substring(elem.indexOf("   -   ") + 7,
                    elem.indexOf("1 2 3 4 5 6 7 8 9 10") - 5);
            double rating = Double.parseDouble(elem.substring(elem.indexOf("10 ") + "10 ".length(),
                    elem.indexOf("/10")));
            int overall = Integer.parseInt(elem.substring(elem.indexOf("X   ") + "X   ".length(),
                    elem.indexOf(" Metascore")));
            String desc = elem.substring(elem.indexOf("Metascore ") + "Metascore ".length(), elem.indexOf("Director"));

            objArr.add(new Movie(title, length, genre, rating, overall, desc));
        }
    }

    private static ArrayList<MoviesToEmail> checkScore(ArrayList<Movie> arr){
        ArrayList<MoviesToEmail> obj = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).getRating() >= 7.0 || arr.get(i).getOverallScore() >= 70){
                Movie elem = arr.get(i);
                obj.add(new MoviesToEmail(elem.getTitle(), elem.getLength(), elem.getGenre(),
                        elem.getRating(), elem.getOverallScore(), elem.getDescription()));
            }
        }

        return obj;
    }

    private static void testArr(ArrayList<MoviesToEmail> arr){
        for (int i = 0; i < arr.size(); i++){
            System.out.println(arr.get(i).getTitle());
            System.out.println(arr.get(i).getRating());
            System.out.println(arr.get(i).getOverallScore());
            System.out.println(arr.get(i).getGenre());
            System.out.println(arr.get(i).formatMovieString());
        }
    }
}
