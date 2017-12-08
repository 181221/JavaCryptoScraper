package no.pederyo.scraper;

import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.util.CoinUtil;

import java.util.ArrayList;

import static no.pederyo.util.CoinUtil.formaterTall;
import static no.pederyo.util.CoinUtil.rengUtProsent;

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
        PushBullet pb = new PushBullet(System.getenv("pushApi"));
    }

    public static boolean sjekkMilePeler(double current) {
        String melding = "milepel nådd verdien er nå ";
        int i = 0;
        if (current >= MILEPEL && current < MILEPEL + 0.5 && !naaddfire) { //mellom 4.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            System.out.println(melding + formaterTall(current) + "USD");
            naaddfire = true;
            i++;
        }
        if (current >= (MILEPELFEM) && current < (MILEPELFEM + 0.5) && !naaddfem) { //mellom 5.5 og 5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            System.out.println(melding + formaterTall(current) + "USD");
            naaddfem = true;
            i++;
        }
        if (current >= (MILEPELSEKS) && current < (MILEPELSEKS + 0.5) && !naaddseks) { //mellom 6 og 5.5
            PushBullet.client.sendNotePush(melding, formaterTall(current) + " USD");
            System.out.println(melding + formaterTall(current) + "$");
            naaddseks = true;
            i++;
        }
        return i >= 1;
    }

    public static boolean sjekkforjeVerdi(double forje, double current) {
        boolean nypris = false;
        if (current >= forje + .03) {
            System.out.println("Ny Verdi");
            PushBullet.client.sendNotePush("Ny Verdi", formaterTall(current) + " USD");
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
                oekning = true;
            }
        }
        return oekning;
    }
}
