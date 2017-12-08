package no.pederyo;

import no.pederyo.modell.Coin;
import no.pederyo.scrapeKlient.ScrapeHjelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ScrapeHjelperTest {
    String[] args;
    String antall = "10";
    String invest = "3000";

    @Before
    public void setup() {
        args = new String[2];
        args[0] = antall;
        args[1] = invest;
    }

    @Test
    public void lagCoinFraArgs() {
        String[] fake = new String[4];
        Coin c = ScrapeHjelper.lagCoinFraArgs(args);
        Coin c1 = ScrapeHjelper.lagCoinFraArgs(fake);
        assertNotNull(c);
        assertTrue(c1.getInvestment() == 0);
        assertTrue(c1.getAntall() == 0);
        assertTrue(c.getAntall() == 10);
        assertTrue(c.getInvestment() == 3000.0);
    }


}
