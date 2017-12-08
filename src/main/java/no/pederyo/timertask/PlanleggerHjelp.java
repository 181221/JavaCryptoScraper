package no.pederyo.timertask;

import no.pederyo.modell.Coin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanleggerHjelp {

    public static int formaterTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(new Date()).toString();
        int tid = Integer.parseInt(formattedDate);
        if (pattern.equals("mm")) {
            tid += 1;
        }
        return tid;
    }

    public static void settOppplanlegger(Coin c) {
        int time = formaterTime("h");
        int min = formaterTime("mm");
        CustomTask.setOppPlanlegger(time, min, c);
    }
}
