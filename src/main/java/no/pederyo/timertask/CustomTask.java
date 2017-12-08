package no.pederyo.timertask;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTask extends TimerTask {
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
    public static void setOppPlanlegger(int time, int minutt) {
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
        timer.schedule(
                new CustomTask(),
                date.getTime(),
                1000 * 60 * 60 * 24 * 7
        );
    }

    /**
     * Blir kjørt 2 ganger i døgnet. Her kan mail implementeres.
     */
    public void run() {
        try {
            //TODO
            System.out.println("kjører");
        } catch (Exception ex) {

            System.out.println("error running thread " + ex.getMessage());
        }
    }
}
