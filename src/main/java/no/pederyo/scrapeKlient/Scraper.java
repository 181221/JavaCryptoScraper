package no.pederyo.scrapeKlient;

import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.timertask.CustomTask;
import no.pederyo.util.CoinUtil;

import java.io.IOException;
import java.util.ArrayList;

import static no.pederyo.scrapeKlient.ScrapeHjelper.*;
import static no.pederyo.util.CoinUtil.*;

/**
 * @author Peder
 * Program for å tracke prisen til IoTa.
 */
public class Scraper {
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = lagCoinFraArgs(args);
        CustomTask.setOppPlanlegger(9, 00, coin);
        int i = 0;
        while (true) {
            try {
                i++;
                double verdi = scrape();
                regnUtVerdier(verdi, coin);
                skrivUt(verdi, coin);
                leggTilogSjekkOekning(verdi, coin, i);
                System.out.println();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * legger til en verdi hver halvtime
     *
     * @param verdi
     * @param coin
     * @param i
     */
    public static void leggTilogSjekkOekning(double verdi, Coin coin, int i) {
        if (i % 180 == 0) {
            leggTilVerdi(verdi, coin);
            if (coin.getOekning() > 3.0) {
                // send melding.
            }
        }
    }

    /**
     * Regner ut økning av IoTa. Regner ut om det er en forksjell på midten og siste i pristabellen.
     * @param coin
     * @return true om det er over 0 prosent økning.
     */
    private static boolean oekning(Coin coin) {
        boolean oekning = false;
        ArrayList<Verdi> coins = coin.getVerdier();
        int antall = coins.size();
        double currentVerdi = coins.get(antall - 1).getPris();
        double forjeVerdi = coins.get(antall - 2).getPris();
        double differanse = CoinUtil.formaterDouble(rengUtProsent(currentVerdi, forjeVerdi));
        if (differanse > 0) {
            coin.setOekning(differanse);
            oekning = true;
        }
        return oekning;
    }


}
