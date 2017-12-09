package no.pederyo.scraper;

import no.api.jsoup.JsoupScrape;
import no.pederyo.logg.Logg;
import no.pederyo.modell.Coin;
import no.pederyo.util.CoinUtil;

import static no.pederyo.util.CoinUtil.formaterTall;

public class ScrapeHjelper {
    /**
     * Setter opp argumene fra main.
     * @param args antall coin -> args[0]. investering -> args[1]
     * @return Coin
     */
    public static Coin lagCoinFraArgs(String[] args) {
        Coin c = new Coin();
        if (args.length - 1 == 2) {
            c.setAntall(Integer.parseInt(args[0]));
            c.setInvestment(Double.parseDouble(args[1]));
        }
        return c;
    }

    /**
     * Skraper worldcoinindex for IoTa priser. Fant ingen api til IoTa.
     *
     * @return verdien
     */
    public static double scrape() {
        return CoinUtil.konverterTilTall(JsoupScrape.connect());
    }

    /**
     * Skriver ut alle verdiene.
     * @param verdi
     * @param coin
     */
    public static void skrivTilLogg(double verdi, Coin coin) {
        int antall = coin.getVerdier().size();
        String melding = " ";
        if (antall >= 2) {
            melding = "\nForje verdi: " + coin.seForjePris() + " kl " + coin.getVerdier().get(antall - 2).getTid();
            System.out.println();
        }
        Logg.logger.info("Verdien til IoTa: " + verdi + " USD" +
                "\n" + "Verdien til IoTa: " + verdi + " USD" +
                "\n" + "Din investering: " + formaterTall(coin.getInvestment()) + " kr" +
                "\n" + "Din totale beholdning: " + formaterTall(coin.getTotal()) + " kr " +
                "\n" + "Avkasning: " + formaterTall(coin.getAvkasning()) + "%" + melding);
    }

    public static String lagMelding(Coin coin) {
        String melding = "Dagens Høyeste: " + formaterTall(coin.dagensHoeste().getPris()) + "$ kl: " + coin.dagensHoeste().getTid() +
                "\nDin investering: " + formaterTall(coin.getInvestment()) + "kr" +
                "\nTotal beholdning: " + formaterTall(coin.getTotal()) + "kr" +
                "\nAvkasning: " + formaterTall(coin.getAvkasning()) + "%" +
                "\nAntall Iota's: " + coin.getAntall() +
                "\nIoTa verdi: " + formaterTall(coin.seForjePris());

        Logg.logger.info(melding);
        return melding;
    }

}
