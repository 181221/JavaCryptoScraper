package no.pederyo.scraper;

import no.pederyo.logg.Logg;
import no.pederyo.modell.Avkastning;
import no.pederyo.modell.Coin;
import no.pederyo.timertask.PlanleggerHjelp;

import static no.pederyo.scraper.ScrapeHjelper.scrape;
import static no.pederyo.scraper.ScrapeHjelper.skrivTilLogg;
import static no.pederyo.scraper.VerdiSjekker.sjekkMilePeler;
import static no.pederyo.scraper.VerdiSjekker.sjekkforjeVerdi;
import static no.pederyo.util.CoinUtil.*;

public class Scraper {
    private static double oldValue = 0.0;
    private static int iterasjon = 0;
    public static void kjorProgram(Coin c) {
        oldValue = scrape();
        leggTilVerdi(oldValue, c);
        Avkastning avkastning = new Avkastning();
        regnUtSetTotalOgAvkastning(oldValue, c);
        avkastning.leggTilAvkastning(c); // Oppretter node.
        while (true) {
            try {
                enScrapeIterasjon(c, avkastning);
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void enScrapeIterasjon(Coin coin, Avkastning avk) {
        iterasjon++;
        double verdi = scrape();
        regnUtSetTotalOgAvkastning(verdi, coin);
        skrivTilLogg(verdi, coin);
        if (iterasjon % 3 == 0) { //lagrer gammel verdi vært 30 sek
            oldValue = verdi;
        }
        if (iterasjon % 180 == 0) { //sjekker mot gammel verdi. Hver halv time.
            sjekkValletPushNot(coin, verdi); //sjekker gammel mot current verdi.
            avk.leggTilAvkastning(coin); // legger til nyAvkastning
            sjekkAvkastning(coin, avk); // sjekker avkastning.
            leggTilVerdi(verdi, coin);
        }
        sjekkforjeVerdi(oldValue, verdi);
        sjekkMilePeler(verdi);
        if (iterasjon % 4320 == 0) { // hver 12 time send summary.
            PushBullet.client.sendNotePush("Summary!", ScrapeHjelper.lagMelding(coin));
        }
    }

    /**
     * Setter opp pb, logg og planlegger.
     *
     * @param args
     * @param coin
     */
    public static void setUp(String[] args, Coin coin) {
        new PushBullet(args[2]);
        new Logg();
        PlanleggerHjelp.settOppplanlegger(coin);// Har ikke testet metode.
    }

}
