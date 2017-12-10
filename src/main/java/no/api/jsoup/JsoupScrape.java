package no.api.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class JsoupScrape {
    public static final String IOTA_URL = "https://www.worldcoinindex.com/coin/iota";
    /**
     * Connecter til internett sted og finner prisen til iota.
     *
     * @return Element tag
     */
    public static Element connect() {
        Document doc = null;
        try {
            doc = Jsoup.connect(IOTA_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getElementsByClass("number price").first();
    }

    public static String hoyestIdag() {
        Document doc = null;
        try {
            doc = Jsoup.connect(IOTA_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.getElementsByClass("col-md-6 col-xs-6 coin-high").first().children().text();
    }

    public static void main(String[] args) {
        System.out.println(hoyestIdag().substring(0, 6));
    }
}
