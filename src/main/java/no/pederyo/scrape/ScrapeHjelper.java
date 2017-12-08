package no.pederyo.scrape;

import no.pederyo.coin.Coin;
import no.pederyo.coin.CoinUtil;
import no.pederyo.jsoup.JsoupScrape;

import static no.pederyo.coin.CoinUtil.formaterTall;

class ScrapeHjelper {
    static Coin lagCoinFraArgs(String[] args) {
        Coin c = new Coin();
        if (args.length == 3) {
            c.setAntall(Integer.parseInt(args[0]));
            c.setInvestment(Double.parseDouble(args[1]));
        }
        return c;
    }

    static double scrape() {
        return CoinUtil.konverterTilTall(JsoupScrape.connect());
    }

    static void skrivUt(double verdi, Coin coin) {
        System.out.println("Verdien til IoTa: " + verdi + " USD");
        System.out.println("Din investering: " + formaterTall(coin.getInvestment()) + " kr");
        System.out.println("Din totale beholdning: " + formaterTall(coin.getTotal()) + " kr ");
        System.out.println("Avkasning: " + formaterTall(coin.getAvkasning()) + "%");
    }
}
