package no.api.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class JsoupScrape {
    /**
     * Connecter til internett sted og finner prisen til iota.
     *
     * @return Element tag
     */
    public static Element connect() {
        String url = "https://www.worldcoinindex.com/coin/iota";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getElementsByClass("number price").first();
    }
}
