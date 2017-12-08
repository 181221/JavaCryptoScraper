package no.pederyo.scrape;

import no.pederyo.coin.Coin;
import no.pederyo.coin.CoinUtil;

import java.io.IOException;
import java.util.ArrayList;

import static no.pederyo.coin.CoinUtil.regnUtVerdier;
import static no.pederyo.coin.CoinUtil.rengUtProsent;
import static no.pederyo.scrape.ScrapeHjelper.*;

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
                double verdi = scrape();
                regnUtVerdier(verdi, coin);
                skrivUt(verdi, coin);
                i++;
                if (i % 2 == 0) {
                    if (oekning(coin)) {
                        System.out.println("Det har skjedd en økning siden forjegang: " + coin.getOekning() + "%");
                    }
                }
                System.out.println();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Regner ut økning av IoTa. Regner ut om det er en forksjell på midten og siste i pristabellen.
     *
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
