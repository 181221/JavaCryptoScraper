package no.pederyo;

import no.pederyo.modell.Coin;
import no.pederyo.scraper.PushBullet;
import org.junit.Before;
import org.junit.Test;

public class PlanleggerTest {
    Coin coin;
    PushBullet pb;

    @Before
    public void setup() {
        coin = new Coin(98, 3000);
        pb = new PushBullet(System.getenv("pushApi"));

    }

    @Test
    public void senderMeldingTilGittTid() {

        //CustomTask.setOppPlanlegger(time, min, coin);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
