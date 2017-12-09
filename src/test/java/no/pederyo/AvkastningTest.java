package no.pederyo;

import no.pederyo.modell.Avkastning;
import no.pederyo.util.CoinUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AvkastningTest {
    Avkastning avk;
    private double a0 = 0.0;
    private double a1 = 5.0;
    private double a2 = 4.5;
    private double a3 = 3.2;
    private double a4 = 20.5;
    private double a5 = 25.5;
    private double a6 = 39.0;
    private double a7 = -35.0;
    private double a8 = -41.0;

    @Before
    public void setUp() {
        avk = new Avkastning();
    }

    @Test
    public void sjekkAtForjeMinusAvkastningStemmer() {
        avk.leggTil(a1);
        avk.leggTil(a0);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a4);
        avk.leggTil(a3);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a2);
        assertFalse(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a6);
        avk.leggTil(a5);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a7);
        avk.leggTil(a8);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
    }

    @Test
    public void sjekkAtForjeAvkastningStemmer() {
        avk.leggTil(a0);
        assertFalse(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a1);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a2);
        avk.leggTil(a3);
        assertFalse(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a4);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a5);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a6);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
        avk.leggTil(a8);
        avk.leggTil(a7);
        assertTrue(CoinUtil.sjekkAvkastning(avk));
    }


}
