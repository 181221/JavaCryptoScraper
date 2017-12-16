package no.pederyo;

import no.pederyo.modell.Avkastning;
import no.pederyo.modell.Coin;
import no.pederyo.modell.Verdi;
import no.pederyo.scraper.VerdiSjekker;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static no.pederyo.util.CoinUtil.sjekkVerdiOgPushNotifikasjon;
import static org.junit.Assert.assertTrue;

public class CoinUtilTest {
    Verdi v0 = new Verdi(4.0, "10:40");
    Verdi v1 = new Verdi(5.0, "10:40");
    Verdi v3 = new Verdi(6.0, "10:40");
    Verdi v4 = new Verdi(4.0, "10:40");
    private Coin c;

    @Before
    public void setUp() {
        VerdiSjekker v = new VerdiSjekker();
        c = new Coin();
        c.setAvkasning(new Avkastning(0, ""));
    }
    @Test
    public void sjekkAtValletPushNotNaarDiffMinusRiktig() {
        c.leggTil(v1);
        c.leggTil(v3);
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 4.70000000));
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 4.324323));
        assertFalse(sjekkVerdiOgPushNotifikasjon(c, 5));
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 3.211212));
        assertFalse(sjekkVerdiOgPushNotifikasjon(c, 5));
    }

    @Test
    public void sjekkAtValletPushNotNaarDiffPLussErRiktig() {
        c.leggTil(v0);
        c.leggTil(v1);
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 4.3));
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 4.5));
        c.leggTil(v3);
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 5.3));
        assertTrue(sjekkVerdiOgPushNotifikasjon(c, 5.555));
        assertFalse(sjekkVerdiOgPushNotifikasjon(c, 4.81));
    }

    @Test
    public void seSistereturnererriktig() {
        c.leggTil(v0);
        assertTrue(c.seForjePris() == -1);
        c.leggTil(v1);
        assertTrue(c.seForjePris() == v0.getPris());
        assertFalse(c.seForjePris() == v1.getPris());
    }

}
