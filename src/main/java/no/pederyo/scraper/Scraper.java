package no.pederyo.scraper;

import no.pederyo.logg.Logg;
import no.pederyo.modell.AvkastningArkiv;
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
        AvkastningArkiv avkastning = new AvkastningArkiv();
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

    /**
     * En iterasjon Skjer hvert tiende sekund. Iota Verdi blir hentet fra internett og parametere sjekkes.
     *
     * @param coin
     * @param avk
     */
    private static void enScrapeIterasjon(Coin coin, AvkastningArkiv avk) {
        iterasjon++;

        double verdi = scrape(); // Henter Iota verdi fra internett.

        regnUtSetTotalOgAvkastning(verdi, coin); // regner ut avkasning og total.

        if (iterasjon % 3 == 0) { //lagrer gammel verdi vært 30 sek.
            oldValue = verdi;
        }

        if (iterasjon % 6 == 0) {
            skrivTilLogg(verdi, coin); // skriver til log vært 60 sek.
        }

        halvTimeSjekkVerdiEndring(coin, verdi); // sjekker verdi endring på IoTa

        halvTimeSjekkAvkastning(coin, verdi, avk); // sjekker avkastningsedring på beholding.

        sjekkforjeVerdi(oldValue, verdi); // sjekker om det har skjedd en endring på 4% på 30 sekunder.

        sjekkMilePeler(verdi); // Sjekker om noen av milepælene er nådd. 4.5, 5.5, 6.5.

        DagSammendrag(coin, verdi, avk);  // hver 24 time send summary.

    }

    /**
     * @param coin
     * @param verdi
     */
    public static void DagSammendrag(Coin coin, double verdi, AvkastningArkiv avk) {
        if (iterasjon % 86400 == 0) {
            avk.leggTilAvkastning(coin);
            PushBullet.client.sendNotePush("24 Timers varsel.", ScrapeHjelper.lagMelding(coin, verdi));
        }
    }

    /**
     * Sjekker hver halvtime om det har skjedd en endring på 8% på IoTa verdi.
     *
     * @param coin
     * @param verdi
     * @return
     */
    public static boolean halvTimeSjekkVerdiEndring(Coin coin, double verdi) {
        boolean leggTil = false;
        if (iterasjon % 180 == 0) {
            leggTil = sjekkVerdiOgPushNotifikasjon(coin, verdi); //sjekker gammel mot current verdi.
            if (leggTil) {
                leggTilVerdi(verdi, coin); //legger til ny verdi om det har vært en økning på 8% siden forjegang.
            }
        }
        return leggTil;
    }

    /**
     * Sjekker hver halvtime om det har skjedd en endring på 7 prosent i avkastningen siden forje sjekk.
     * Om det har skjedd en endring legges den til i linken ellers skjer ingen ting.
     * Første halvtimen i programmet legges verdien til og da er det 2 verdier i linken.
     *
     * @param coin
     * @param verdi
     * @param avk
     * @return
     */
    public static boolean halvTimeSjekkAvkastning(Coin coin, double verdi, AvkastningArkiv avk) {
        boolean avkastning = false;
        if (iterasjon % 180 == 0) {
            if (iterasjon == 180) { // første halvtimen i programmet legges verdi til nå er det 2 verdier i linken.
                avk.leggTilAvkastning(coin);
            }
            avkastning = sjekkAvkastning(avk); // sjekker avkastningen.
            if (avkastning) { // om det har vært en endring på 8% legges en ny avkastning inn i linken.
                avk.leggTilAvkastning(coin);
            }
        }
        return avkastning;
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
