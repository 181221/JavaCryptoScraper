package no.pederyo.scraper;

import no.pederyo.modell.Coin;
import no.pederyo.timertask.PlanleggerHjelp;

import static no.pederyo.scraper.ScrapeHjelper.scrape;
import static no.pederyo.scraper.VerdiSjekker.sjekkMilePeler;
import static no.pederyo.scraper.VerdiSjekker.sjekkforjeVerdi;
import static no.pederyo.util.CoinUtil.*;

public class Scraper {
    private static double oldValue = 0.0;
    private static int iterasjon = 0;
    public static void kjorProgram(Coin c) {
        oldValue = scrape();
        leggTilVerdi(oldValue, c);
        while (true) {
            try {
                enScrapeIterasjon(c);
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setUp(String[] args, Coin coin) {
        new PushBullet(args[2]);
        PlanleggerHjelp.settOppplanlegger(coin);// Har ikke testet metode.
    }

    private static void enScrapeIterasjon(Coin coin) {
        iterasjon++;
        double verdi = scrape();
        regnUtVerdier(verdi, coin);
        //skrivUt(verdi, coin);
        if (iterasjon % 3 == 0) { //lagrer gammel verdi vært 30 sek
            oldValue = verdi;
        }
        if (iterasjon % 180 == 0) { //sjekker mot gammel verdi. Hver halv time.
            sjekkValletPushNot(coin, verdi);
            leggTilVerdi(verdi, coin);
        }
        sjekkforjeVerdi(oldValue, verdi);
        sjekkMilePeler(verdi);
        if (iterasjon % 4320 == 0) { // hver 12 time send summary.
            PushBullet.client.sendNotePush("Summary!", ScrapeHjelper.lagMelding(coin));
        }
        System.out.println();
    }

}
