package no.pederyo;

import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.scraper.VerdiSjekker;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static no.pederyo.util.CoinUtil.sjekkValletPushNot;
import static org.junit.Assert.assertTrue;

public class CoinUtilTest {
    Verdi v0 = new Verdi(4.0, "10:40");
    Verdi v1 = new Verdi(5.0, "10:40");
    Verdi v3 = new Verdi(6.0, "10:40");
    Verdi v4 = new Verdi(4.0, "10:40");
    Verdi v5 = new Verdi(5.0, "10:40");
    Verdi v6 = new Verdi(6.0, "10:40");
    Verdi v7 = new Verdi(5.0, "10:40");
    Verdi v8 = new Verdi(5.2, "10:40");
    private Coin c;

    @Before
    public void setUp() {
        VerdiSjekker v = new VerdiSjekker();
        c = new Coin();
    }

    @Test
    public void sjekkAtValletPushNotNaarDiffErRiktig() {
        c.leggTil(v0);
        c.leggTil(v1);
        assertTrue(sjekkValletPushNot(c, 4.3));
        assertTrue(sjekkValletPushNot(c, 4.5));
        c.leggTil(v3);
        assertTrue(sjekkValletPushNot(c, 4.3));
        assertTrue(sjekkValletPushNot(c, 4.5));
    }

    @Test
    public void seSistereturnererriktig() {
        c.leggTil(v0);
        assertTrue(c.seForjePris() == -1);
        c.leggTil(v1);
        assertTrue(c.seForjePris() == v0.getPris());
        assertFalse(c.seForjePris() == v1.getPris());
    }

    @Test
    public void sjekkAtValletPushNotNaarAvkasningErRiktig() {
        c.setAvkasning(2);
        assertTrue(sjekkValletPushNot(c, 0));
        c.setAvkasning(0);
        assertFalse(sjekkValletPushNot(c, 0.0));
        c.setAvkasning(2.0);
        assertTrue(sjekkValletPushNot(c, 0));
        c.setAvkasning(10.0);
        assertTrue(sjekkValletPushNot(c, 0));
    }

}
