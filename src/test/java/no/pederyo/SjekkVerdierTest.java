package no.pederyo;

import no.pederyo.modell.Coin;
import no.pederyo.scraper.VerdiSjekker;
import no.pederyo.util.CoinUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SjekkVerdierTest {
    private double e0 = 4.0;
    private double e1 = 4.3;
    private double e2 = 4.6;
    private double e3 = 4.7;
    private double e4 = 4.9;
    private double e5 = 4.8;
    private VerdiSjekker verdiSjekker;
    private Coin c;

    @Before
    public void setup() {
        verdiSjekker = new VerdiSjekker();
        c = new Coin();
        CoinUtil.leggTilVerdi(4.0, c);
        CoinUtil.leggTilVerdi(8.0, c);
        CoinUtil.leggTilVerdi(12.0, c);
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(14.0, c);
    }

    @Test
    public void sjekkAtNyVerdiErSannpluss() {
        assertTrue(VerdiSjekker.sjekkforjeVerdi(4.3123, 4.5123));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(4.5432, 5));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(2.2, 2.4));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(2.312, 2.512));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(0.0, 2.512));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(2.0, 2.1));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(2.1, 2.1));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(2, 2));
    }

    @Test
    public void sjekkAtNyVerdiErSannminus() {
        assertTrue(VerdiSjekker.sjekkforjeVerdi(4.2, 4));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(4.300, 4.1));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(5.221, 4));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(10.3412, 10.1412));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(2.0, 2.1));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(0, 0));
    }

    @Test
    public void gjoromdobletilbigdecimal() {
        double f1 = 2.0000;
        double f2 = 2.3000;
        double f3 = 2.5000;
        double f4 = 3.6000;
        double diff = 0.2;
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f1, "pluss", diff) == 2.2000);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f2, "pluss", diff) == 2.5000);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f3, "pluss", diff) == 2.7000);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f4, "pluss", diff) == 3.8000);

        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f4, "feil", diff) == -1);

        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f1, "minus", diff) == 1.8000);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f2, "minus", diff) == 2.1000);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f3, "minus", diff) == f2);
        assertTrue(VerdiSjekker.gjorOmDoubleTilBC(f4, "minus", diff) == 3.4);

    }

    @Test
    public void sjekkAtMilePelFIREBlirNaad() {
        assertFalse(VerdiSjekker.naaddfire);
        assertTrue(VerdiSjekker.sjekkMilePeler(4.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(4.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(4.3));
        assertFalse(VerdiSjekker.sjekkMilePeler(4.2));
        assertTrue(VerdiSjekker.naaddfire);
    }

    @Test
    public void sjekkAtMilePelFEMBlirNaad() {
        assertFalse(VerdiSjekker.naaddfem);
        assertTrue(VerdiSjekker.sjekkMilePeler(5.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(5.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(5.3));
        assertFalse(VerdiSjekker.sjekkMilePeler(5.2));
        assertTrue(VerdiSjekker.naaddfem);
    }

    @Test
    public void sjekkAtMilePelSEKSBlirNaad() {
        assertFalse(VerdiSjekker.naaddseks);
        assertTrue(VerdiSjekker.sjekkMilePeler(6.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(6.5));
        assertFalse(VerdiSjekker.sjekkMilePeler(6.3));
        assertFalse(VerdiSjekker.sjekkMilePeler(6.2));
        assertTrue(VerdiSjekker.naaddseks);
    }

    @Test
    public void sjekkerOekning() {
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(19.0, c);
        assertTrue(VerdiSjekker.oekning(c));
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(16.0, c);
        CoinUtil.leggTilVerdi(124.0, c);
        CoinUtil.leggTilVerdi(125.0, c);
        assertFalse(VerdiSjekker.oekning(c));
        CoinUtil.leggTilVerdi(100.0, c);
        CoinUtil.leggTilVerdi(103.0, c);
        assertTrue(VerdiSjekker.oekning(c));
    }

    @Test
    public void antallErTom() {
        Coin k = new Coin();
        assertTrue(k.getVerdier().size() == 0);
        assertFalse(VerdiSjekker.oekning(k));
    }

    @Test
    public void leggerTilCoinVerdi() {
        assertTrue(c.getVerdier().get(0).getPris() == 4.0);
        assertTrue(c.getVerdier().get(1).getPris() == 8.0);
        assertTrue(c.getVerdier().get(2).getPris() == 12.0);
        assertTrue(c.getVerdier().get(3).getPris() == 15.0);
        assertFalse(c.getVerdier().get(3).getPris() == 16.0);
        assertFalse(c.getVerdier().get(2).getPris() == 1.0);
        assertTrue(c.getVerdier().size() == 5);
    }

}
