package no.pederyo;

import no.pederyo.modell.Coin;
import no.pederyo.scrapeKlient.Scraper;
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
    private Scraper scraper;
    private Coin c;

    @Before
    public void setup() {
        scraper = new Scraper();
        c = new Coin();
        CoinUtil.leggTilVerdi(4.0, c);
        CoinUtil.leggTilVerdi(8.0, c);
        CoinUtil.leggTilVerdi(12.0, c);
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(14.0, c);
    }

    @Test
    public void sjekkAtNyVerdiErSann() {
        assertTrue(Scraper.sjekkforjeVerdi(e0, e1));
        assertTrue(Scraper.sjekkforjeVerdi(e0, e2));
        assertTrue(Scraper.sjekkforjeVerdi(e0, e3));
        assertFalse(Scraper.sjekkforjeVerdi(e1, e0));
        assertFalse(Scraper.sjekkforjeVerdi(e4, e5));
        assertFalse(Scraper.sjekkforjeVerdi(e3, e2));
    }

    @Test
    public void sjekkAtMilePelFIREBlirNaad() {
        assertFalse(Scraper.naaddfire);
        assertTrue(Scraper.sjekkMilePeler(4.5));
        assertFalse(Scraper.sjekkMilePeler(4.5));
        assertFalse(Scraper.sjekkMilePeler(4.3));
        assertFalse(Scraper.sjekkMilePeler(4.2));
        assertTrue(Scraper.naaddfire);
    }

    @Test
    public void sjekkAtMilePelFEMBlirNaad() {
        assertFalse(Scraper.naaddfem);
        assertTrue(Scraper.sjekkMilePeler(5.5));
        assertFalse(Scraper.sjekkMilePeler(5.5));
        assertFalse(Scraper.sjekkMilePeler(5.3));
        assertFalse(Scraper.sjekkMilePeler(5.2));
        assertTrue(Scraper.naaddfem);
    }

    @Test
    public void sjekkAtMilePelSEKSBlirNaad() {
        assertFalse(Scraper.naaddseks);
        assertTrue(Scraper.sjekkMilePeler(6.5));
        assertFalse(Scraper.sjekkMilePeler(6.5));
        assertFalse(Scraper.sjekkMilePeler(6.3));
        assertFalse(Scraper.sjekkMilePeler(6.2));
        assertTrue(Scraper.naaddseks);
    }

    @Test
    public void sjekkerOekning() {
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(19.0, c);
        assertTrue(Scraper.oekning(c));
        CoinUtil.leggTilVerdi(15.0, c);
        CoinUtil.leggTilVerdi(16.0, c);
        CoinUtil.leggTilVerdi(124.0, c);
        CoinUtil.leggTilVerdi(125.0, c);
        assertFalse(Scraper.oekning(c));
        CoinUtil.leggTilVerdi(100.0, c);
        CoinUtil.leggTilVerdi(103.0, c);
        assertTrue(Scraper.oekning(c));
    }

    @Test
    public void antallErTom() {
        Coin k = new Coin();
        assertTrue(k.getVerdier().size() == 0);
        assertFalse(Scraper.oekning(k));
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
