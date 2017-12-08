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
    public void sjekkAtNyVerdiErSann() {
        assertTrue(VerdiSjekker.sjekkforjeVerdi(e0, e1));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(e0, e2));
        assertTrue(VerdiSjekker.sjekkforjeVerdi(e0, e3));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(e1, e0));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(e4, e5));
        assertFalse(VerdiSjekker.sjekkforjeVerdi(e3, e2));
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
