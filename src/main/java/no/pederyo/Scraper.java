package no.pederyo;

import no.pederyo.coin.Coin;
import no.pederyo.coin.CoinUtil;
import no.pederyo.jsoup.JsoupScrape;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static no.pederyo.coin.CoinUtil.formaterTall;


public class Scraper {
    public static void main(String[] args) throws IOException {
        Element elem = JsoupScrape.connect();
        Coin coin = new Coin(98, 4000.1234);
        start(elem, coin);

    }

    private static void start(Element elem, Coin coin) {
        double verdi = CoinUtil.konverterTilTall(elem);
        if (verdi != -1) {
            coin.setTotal(CoinUtil.totalVerdiINOK(verdi, coin));
            double avkasning = (coin.getTotal() - coin.getInvestment()) / coin.getInvestment() * 100;
            coin.setAvkasning(avkasning);
            skrivUt(verdi, coin);
        }
    }

    private static void skrivUt(double verdi, Coin coin) {
        System.out.println("Verdien til IoTa: " + verdi + " USD");
        System.out.println("Din investering: " + formaterTall(coin.getInvestment()) + " kr");
        System.out.println("Din totale beholdning: " + formaterTall(coin.getTotal()) + " kr ");
        System.out.println("Avkasning: " + formaterTall(coin.getAvkasning()) + "%");
    }


}
