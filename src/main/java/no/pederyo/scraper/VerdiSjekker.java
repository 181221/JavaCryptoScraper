package no.pederyo.scraper;

import no.pederyo.logg.Logg;
import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.util.CoinUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

import static no.pederyo.util.CoinUtil.*;

public class VerdiSjekker {
    public static final double MILEPEL = 4.5;
    public static final double MILEPELFEM = 5.5;
    public static final double MILEPELSEKS = 6.5;

    public static boolean naaddfire;
    public static boolean naaddfem;
    public static boolean naaddseks;

    public VerdiSjekker() {
        naaddfire = false;
        naaddfem = false;
        naaddseks = false;
        // PushBullet pb = new PushBullet(System.getenv("pushApi"));
    }

    public static boolean sjekkMilePeler(double current) {
        String melding = "milepel nådd verdien er nå ";
        int i = 0;
        if (current >= MILEPEL && current < MILEPEL + 0.5 && !naaddfire) { //mellom 4.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            naaddfire = true;
            i++;
        }
        if (current >= (MILEPELFEM) && current < (MILEPELFEM + 0.5) && !naaddfem) { //mellom 5.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            naaddfem = true;
            i++;
        }
        if (current >= (MILEPELSEKS) && current < (MILEPELSEKS + 0.5) && !naaddseks) { //mellom 6 og 5.5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            naaddseks = true;
            i++;
        }
        return i >= 1;
    }

    public static double gjorOmDoubleTilBC(double forje, String operator, double differanse) {
        BigDecimal b = new BigDecimal(forje);
        BigDecimal b1 = new BigDecimal(0.2);
        BigDecimal b2;
        if (operator.equals("pluss")) {
            b2 = b.add(b1);
        } else if (operator.equals("minus")) {
            b2 = b.subtract(b1);
        } else {
            return -1;
        }
        return formaterDouble(b2.doubleValue(), "##.0000");
    }

    public static boolean sjekkforjeVerdi(double forje, double current) {
        boolean nypris = false;
        double plussforje = gjorOmDoubleTilBC(forje, "pluss", 0.2);
        double minusforje = gjorOmDoubleTilBC(forje, "minus", 0.2);
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
     * Regner ut økning av IoTa. Regner ut om det er en forksjell på midten og siste i pristabellen.
     *
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
                PushBullet.client.sendNotePush("ØkningVarsel", formaterTall(coin.getOekning()) + "%");
                Logg.logger.info("ØkningVarsel" + formaterTall(coin.getOekning()) + "%");
                oekning = true;
            }
        }
        return oekning;
    }
}
