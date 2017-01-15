import org.jsoup.Jsoup;

import java.io.IOException;

public class HtmlUtil {

    public String getTitle(String url) {
        try {
            return Jsoup.connect(url).get().title();
        } catch (IOException e) {
            return "COULDN'T RETRIEVE TITLE";
        }
    }

}
