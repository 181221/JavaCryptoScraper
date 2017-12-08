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
    public static final double MILEPEL = 4.5;
    public static final double MILEPELFEM = 5.5;
    public static final double MILEPELSEKS = 6.5;
    public static boolean naaddfire;
    public static boolean naaddfem;
    public static boolean naaddseks;

    public Scraper() {
        naaddfire = false;
        naaddfem = false;
        naaddseks = false;
        PushBullet pb = new PushBullet("asdasdasasd");
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Coin coin = lagCoinFraArgs(args);
        new PushBullet(args[2]);
        CustomTask.setOppPlanlegger(9, 00, coin);
        int i = 0;
        double verdi = scrape();
        double oldValue = verdi;
        while (true) {
            try {
                i++;
                verdi = scrape();
                regnUtVerdier(verdi, coin);
                leggTilVerdi(verdi, coin);
                sjekkValletPushNot(coin, verdi);
                skrivUt(verdi, coin);
                if (i % 3 == 0) { //lagrer gammel verdi vært 30 sek
                    oldValue = verdi;
                }
                sjekkforjeVerdi(oldValue, verdi);
                sjekkMilePeler(verdi);
                System.out.println();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean sjekkMilePeler(double current) {
        String melding = "milepel nådd verdien er nå ";
        int i = 0;
        if (current >= MILEPEL && current < MILEPEL + 0.5 && !naaddfire) { //mellom 4.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current));
            System.out.println(melding + formaterTall(current));
            naaddfire = true;
            i++;
        }
        if (current >= (MILEPELFEM) && current < (MILEPELFEM + 0.5) && !naaddfem) { //mellom 5.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current));
            System.out.println(melding + formaterTall(current));
            naaddfem = true;
            i++;
        }
        if (current >= (MILEPELSEKS) && current < (MILEPELSEKS + 0.5) && !naaddseks) { //mellom 6 og 5.5
            PushBullet.client.sendNotePush(melding, formaterTall(current));
            System.out.println(melding + formaterTall(current));
            naaddseks = true;
            i++;
        }
        return i >= 1;
    }

    public static boolean sjekkforjeVerdi(double forje, double current) {
        boolean nypris = false;
        if (current >= forje + .02) {
            System.out.println("ny verdi");
            PushBullet.client.sendNotePush("ny verdi", formaterTall(current));
            nypris = true;
        }
        return nypris;
    }

    /**
     * Regner ut økning av IoTa. Regner ut om det er en forksjell på midten og siste i pristabellen.
     * @param coin
     * @return true om det er over 0 prosent økning.
     */
    public static boolean oekning(Coin coin) {
        boolean oekning = false;
        ArrayList<Verdi> coins = coin.getVerdier();
        int antall = coins.size();
        if (antall >= 2) {
            double currentVerdi = coins.get(antall - 1).getPris();
            double forjeVerdi = coins.get(antall - 2).getPris();
            double differanse = CoinUtil.formaterDouble(rengUtProsent(currentVerdi, forjeVerdi));
            if (differanse >= 3) {
                coin.setOekning(differanse);
                PushBullet.client.sendNotePush("ØkningVarsel", formaterTall(coin.getOekning()));
                oekning = true;
            }
        }
        return oekning;
    }
}
