package no.pederyo.scraper;

import no.pederyo.logg.Logg;
import no.pederyo.modell.AvkastningArkiv;
import no.pederyo.modell.Coin;
import no.pederyo.util.CoinUtil;

import java.util.ArrayList;

import static no.pederyo.util.CoinUtil.*;

/**
 * Klasse for alle verdisjekkene. Her ligger alle sjekker mot endring på IoTa.
 */
public class VerdiSjekker {
    public static final double MILEPEL = 5.0;
    public static final double MILEPELSEKS = 6.0;
    public static final double MILEPELSYV = 7.0;
    public static boolean naaddfire;
    public static boolean naaddfem;
    public static boolean naaddseks;
    private static ArrayList<Double> coins = new ArrayList<>(); //for øknignsjekker

    public VerdiSjekker() {
        naaddfire = false;
        naaddfem = false;
        naaddseks = false;
    }

    public static boolean sjekkMilePeler(double current) {
        String melding = "Milepel nådd!";
        String body = "Verdi nå: ";
        int i = 0;
        if (current >= MILEPEL && current < MILEPEL + 0.5 && !naaddfire) {
            PushBullet.client.sendNotePush(melding, body + formaterTall(current) + " USD");
            naaddfire = true;
            i++;
        }
        if (current >= (MILEPELSEKS) && current < (MILEPELSEKS + 0.5) && !naaddfem) {
            PushBullet.client.sendNotePush(melding, body + formaterTall(current) + " USD");
            naaddfem = true;
            i++;
        }
        if (current >= (MILEPELSYV) && current < (MILEPELSYV + 0.5) && !naaddseks) {
            PushBullet.client.sendNotePush(melding, body + formaterTall(current) + " USD");
            naaddseks = true;
            i++;
        }
        return i >= 1;
    }

    public static boolean sjekkforjeVerdi(double forje, double current) {
        boolean nypris = false;
        double plussforje = gjorOmDoubleTilBC(forje, "gange", 1.04);
        double minusforje = gjorOmDoubleTilBC(forje, "gange", 0.96);
        if (current >= plussforje && plussforje != -1) {
            PushBullet.client.sendNotePush("Ny +Verdi", formaterTall(current) + " USD");
            Logg.logger.info("Ny +Verdi " + formaterTall(current) + " USD");
            nypris = true;
        } else if (current <= minusforje && minusforje != -1) {
            PushBullet.client.sendNotePush("Ny -Verdi", formaterTall(current) + " USD");
            Logg.logger.info("Ny -Verdi " + formaterTall(current) + " USD");
            nypris = true;
        }
        return nypris;
    }

    /**
     * Regner ut differansen mellom current verdi og forje.
     * sjekker verditabellen om det er prisendring
     * @param c
     * @param verdi realtime iota verdi i dollar
     * @return returnerer true om det har vært en endring på +-0.8.
     */
    public static boolean sjekkVerdiOgPushNotifikasjon(Coin c, double verdi) {
        int antall = c.getVerdier().size();
        double plussforje = gjorOmDoubleTilBC(c.seForjePris(), "gange", 1.08);
        double minusforje = gjorOmDoubleTilBC(c.seForjePris(), "gange", 0.92);
        String prosent = formaterTall(plussforje - verdi);
        System.out.println(prosent);
        boolean nyVerdi = false;
        if (verdi >= plussforje && antall >= 2) {
            PushBullet.client.sendNotePush("Stigning! IoTaVerdi er nå " + formaterTall(verdi) + " USD", "Økning på over 8% siden " + c.getVerdier().get(antall - 2).getTid());
            nyVerdi = true;
        } else if (verdi <= minusforje && antall >= 2) {
            PushBullet.client.sendNotePush("Nedgang! IoTaVerdi er nå " + formaterTall(verdi) + " USD", "Nedgang på over 8% siden " + c.getVerdier().get(antall - 2).getTid());
            nyVerdi = true;
        }
        return nyVerdi;
    }

    /**
     * Regner ut avkasntning.
     *
     * @param avk
     * @return
     */
    public static boolean sjekkAvkastning(AvkastningArkiv avk) {
        boolean nyAvkasning = false;
        if (avk.getAntall() >= 2) {
            double forje = avk.getStart().getNeste().getElement().getAvkasning().getVerdi();
            double current = avk.getStart().getElement().getAvkasning().getVerdi();
            double plussforje = gjorOmDoubleTilBC(forje, "pluss", 7);
            double minusforje = gjorOmDoubleTilBC(forje, "minus", 7);
            double diff;
            if (current >= plussforje) {
                diff = gjorOmDoubleTilBC(forje, "minus", current);
                PushBullet.client.sendNotePush("Avkastning Steget", " Avkastning er nå: " + formaterTall(current) + "%. Avkastning har steget med " + formaterTall(Math.abs(diff)) + "%");
                nyAvkasning = true;
            } else if (current <= minusforje) {
                diff = gjorOmDoubleTilBC(forje, "minus", current);
                PushBullet.client.sendNotePush("Avkastning reduksjon", "Avkastning er nå: " + formaterTall(current) + "%. Fortjenelsen har synket med " + formaterTall(diff) + "%");
                nyAvkasning = true;
            }
        }
        return nyAvkasning;
    }

    /**
     * Regner ut økning av IoTa.
     * @param coin
     * @return true om det er over 5 prosent økning.
     */
    public static boolean oekning(Coin coin, Double verdi) {
        boolean oekning = false;
        int antall = coins.size();
        if (antall >= 2) {
            double currentVerdi = coins.get(antall - 1);
            double forjeVerdi = coins.get(antall - 2);
            double differanse = CoinUtil.formaterDouble(rengUtProsent(currentVerdi, forjeVerdi));
            System.out.println(differanse);
            if (differanse >= 7) {
                coin.setOekning(differanse);
                PushBullet.client.sendNotePush("Økning på 30 min", formaterTall(coin.getOekning()) + "%");
                Logg.logger.info("ØkningVarsel" + formaterTall(coin.getOekning()) + "%");
                oekning = true;
            } else if (differanse <= -7) {
                coin.setOekning(differanse);
                PushBullet.client.sendNotePush("Nedgang på 30 min", "-" + formaterTall(coin.getOekning()) + "%");
                Logg.logger.info("Nedgang på 30 min" + formaterTall(coin.getOekning()) + "%");
                oekning = true;
            }
        }
        coins.add(verdi);
        return oekning;
    }
}
