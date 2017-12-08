package no.pederyo.scrapeKlient;

import no.pederyo.modell.Coin;
import no.pederyo.util.CoinUtil;

import java.io.IOException;
import java.util.ArrayList;

import static no.pederyo.scrapeKlient.ScrapeHjelper.*;
import static no.pederyo.util.CoinUtil.regnUtVerdier;
import static no.pederyo.util.CoinUtil.rengUtProsent;

/**
 * @author Peder
 * Program for å tracke prisen til IoTa.
 */
public class Scraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = lagCoinFraArgs(args);
        int i = 0;
        while (true) {
            try {
                i++;
                double verdi = scrape();
                regnUtVerdier(verdi, coin);
                skrivUt(verdi, coin);
                if (i % 6 == 0 && oekning(coin)) {
                    System.out.println("Det har skjedd en økning siden forjegang: " + coin.getOekning() + "%");
                }
                System.out.println();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Regner ut økning av IoTa. Regner ut om det er en forksjell på midten og siste i pristabellen.
     * @param coin
     * @return true om det er en 0.5 prosent økning.
     */
    private static boolean oekning(Coin coin) {
        boolean oekning = false;
        ArrayList<Double> coins = coin.getPriser();
        int antall = coins.size();
        double currentVerdi = coins.get(antall - 1);
        double forjeVerdi = coins.get(antall / 2);
        double differanse = CoinUtil.formaterDouble(rengUtProsent(currentVerdi, forjeVerdi));
        if (differanse > 0.5) {
            coin.setOekning(differanse);
            oekning = true;
        }
        return oekning;
    }


}
