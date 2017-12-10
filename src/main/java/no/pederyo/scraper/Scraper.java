package no.pederyo.scraper;

import no.pederyo.logg.Logg;
import no.pederyo.modell.Avkastning;
import no.pederyo.modell.Coin;

import static no.pederyo.scraper.ScrapeHjelper.scrape;
import static no.pederyo.scraper.ScrapeHjelper.skrivTilLogg;
import static no.pederyo.scraper.VerdiSjekker.sjekkMilePeler;
import static no.pederyo.scraper.VerdiSjekker.sjekkforjeVerdi;
import static no.pederyo.util.CoinUtil.*;

public class Scraper {
    private static final String MELDING_START = "IoTa-Tracking har nå startet. Current Verdi: ";
    private static double oldValue = 0.0;
    private static int iterasjon = 0;

    public static void kjorProgram(Coin c) {
        oldValue = scrape();
        PushBullet.client.sendNotePush("TRACKING STARTET", MELDING_START + oldValue + " USD");
        leggTilVerdi(oldValue, c);
        leggTilVerdi(oldValue, c);
        Avkastning avkastning = new Avkastning();
        regnUtSetTotalOgAvkastning(oldValue, c);
        avkastning.leggTilAvkastning(c); // Oppretter node.
        while (true) {
            try {
                Thread.sleep(10000);
                enScrapeIterasjon(c, avkastning);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void enScrapeIterasjon(Coin coin, Avkastning avk) {
        iterasjon++;
        double verdi = scrape();
        regnUtSetTotalOgAvkastning(verdi, coin);
        if (iterasjon % 3 == 0) { //lagrer gammel verdi vært 30 sek
            oldValue = verdi;
        }
        if (iterasjon % 6 == 0) {
            skrivTilLogg(verdi, coin); // skriver til log vært 60 sek
        }
        if (iterasjon % 180 == 0) { //sjekker mot gammel verdi. Hver halv time.
            boolean leggTil = sjekkValletPushNot(coin, verdi); //sjekker gammel mot current verdi.
            avk.leggTilAvkastning(coin);
            sjekkAvkastning(avk);
            if (leggTil) {
                leggTilVerdi(verdi, coin); //legger til ny verdi om det har vært en økning på 0.3 siden forjegang.
            }
        }
        sjekkforjeVerdi(oldValue, verdi);
        sjekkMilePeler(verdi);
        if (iterasjon % 4320 == 0) { // hver 12 time send summary.
            PushBullet.client.sendNotePush("12 Timers varsel.", ScrapeHjelper.lagMelding(coin));
        }
    }

    /**
     * Setter opp pb, logg og planlegger.
     * @param args
     * @param coin
     */
    public static void setUp(String[] args, Coin coin) {
        new PushBullet(args[2]);
        new Logg();
        //PlanleggerHjelp.settOppplanlegger(coin);// Har ikke testet metode.
    }

}
