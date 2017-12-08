package no.pederyo.scraper;

import no.pederyo.modell.Coin;
import no.pederyo.timertask.PlanleggerHjelp;

import static no.pederyo.scraper.ScrapeHjelper.scrape;
import static no.pederyo.scraper.ScrapeHjelper.skrivUt;
import static no.pederyo.scraper.VerdiSjekker.sjekkMilePeler;
import static no.pederyo.scraper.VerdiSjekker.sjekkforjeVerdi;
import static no.pederyo.util.CoinUtil.*;

public class Scraper {
    private static double oldValue = 0.0;

    public static void kjorProgram(Coin c) {
        int i = 0;
        double verdi = scrape();
        oldValue = verdi;
        while (true) {
            try {
                enScrapeIterasjon(i, verdi, c);
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setUp(String[] args, Coin coin) {
        new PushBullet(args[2]);
        PlanleggerHjelp.settOppplanlegger(coin);
    }

    private static void enScrapeIterasjon(int i, double verdi, Coin coin) {
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
    }

}
