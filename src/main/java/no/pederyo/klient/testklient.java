package no.pederyo.klient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class testklient {
    public void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

//Change here for the hour you want ----------------------------------.at()
        Long midnight = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);
        scheduler.scheduleAtFixedRate((Runnable) this, midnight, 1440, TimeUnit.MINUTES);
    }
}
