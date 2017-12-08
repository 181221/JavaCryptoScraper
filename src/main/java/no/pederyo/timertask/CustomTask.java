package no.pederyo.timertask;

import no.pederyo.modell.Coin;
import no.pederyo.scraper.PushBullet;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static no.pederyo.util.CoinUtil.formaterTall;

public class CustomTask extends TimerTask {
    private static Coin coin;
    public CustomTask() {
        int i = 0;
        if (i >= 1) {
            run();
        }
        i++;
    }

    /**
     * Setter opp en planlegger som kjører 2 ganger i døgnet
     *
     * @param time   heltall mellom 0-9
     * @param minutt heltall mellom 0-60
     */
    public static void setOppPlanlegger(int time, int minutt, Coin c) {
        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(
                Calendar.HOUR,
                Calendar.AM_PM
        );
        date.set(Calendar.HOUR, time);
        date.set(Calendar.MINUTE, minutt);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        coin = c;
        timer.schedule(
                new CustomTask(),
                date.getTime(),
                1000 * 60 * 60 * 24 * 7
        );
    }

    /**
     * Blir kjørt 2 ganger i døgnet. Her kan mail implementeres eller pushbullet.
     */

    public void run() {
        try {
            String melding = lagMelding();
            PushBullet.client.sendNotePush("Summary!", melding);
            coin = null;
        } catch (Exception ex) {
            System.out.println("error running thread " + ex.getMessage());
        }
    }

    public String lagMelding() {
        String melding = "Dagens Høyeste: " + formaterTall(coin.dagensHoeste().getPris()) + "$ kl: " + coin.dagensHoeste().getTid() +
                "\nDin investering: " + formaterTall(coin.getInvestment()) + "kr" +
                "\nTotal beholdning: " + formaterTall(coin.getTotal()) + "kr" +
                "\nAvkasning: " + formaterTall(coin.getAvkasning()) + "%" +
                "\nAntall Iota's: " + coin.getAntall();
        return melding;
    }
}
