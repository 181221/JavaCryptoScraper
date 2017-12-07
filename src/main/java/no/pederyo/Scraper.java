package no.pederyo;

import no.pederyo.coin.Coin;
import no.pederyo.coin.CoinUtil;
import no.pederyo.jsoup.JsoupScrape;

import java.io.IOException;

import static no.pederyo.coin.CoinUtil.formaterTall;


public class Scraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = new Coin(98, 4000.1234);
        while (true) {
            double verdi = scrape();
            start(verdi, coin);
            System.out.println();
            Thread.sleep(10000);
        }
    }

    private static double scrape() {
        return CoinUtil.konverterTilTall(JsoupScrape.connect());
    }

    private static void start(double verdi, Coin coin) {
        if (verdi != -1) {
            coin.leggTil(verdi);
            coin.setTotal(CoinUtil.totalVerdiINOK(verdi, coin));
            double avkasning = (coin.getTotal() - coin.getInvestment()) / coin.getInvestment() * 100;
            coin.setAvkasning(avkasning);
            skrivUt(verdi, coin);
        } else {
            System.out.println("noe gikk galt");
        }
    }

    private static void skrivUt(double verdi, Coin coin) {
        System.out.println("Verdien til IoTa: " + verdi + " USD");
        System.out.println("Din investering: " + formaterTall(coin.getInvestment()) + " kr");
        System.out.println("Din totale beholdning: " + formaterTall(coin.getTotal()) + " kr ");
        System.out.println("Avkasning: " + formaterTall(coin.getAvkasning()) + "%");
    }


}
