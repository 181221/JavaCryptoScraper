package no.pederyo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Scraper {
    public static void main(String[] args) throws IOException {
        String url = "https://www.worldcoinindex.com/coin/iota";
        Document doc = Jsoup.connect(url).get();
        Element elem1 = doc.getElementsByClass("number price").first();
        System.out.println(elem1.text().substring(2,8));
    }

}
