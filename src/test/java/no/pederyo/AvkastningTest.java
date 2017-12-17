package no.pederyo;

import no.pederyo.modell.Avkastning;
import no.pederyo.modell.AvkastningArkiv;
import no.pederyo.modell.Coin;
import no.pederyo.scraper.VerdiSjekker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AvkastningTest {
    AvkastningArkiv avk;
    Coin c;
    Coin c1;
    Coin c2;
    Coin c3;
    Coin c4;
    Coin c5;
    Coin c6;
    Coin c7;
    Coin c8;

    Avkastning a0 = new Avkastning(0.0, "");
    Avkastning a1 = new Avkastning(7.0, "");
    Avkastning a2 = new Avkastning(4.0, "");
    Avkastning a3 = new Avkastning(3.2, "");
    Avkastning a4 = new Avkastning(20.5, "");
    Avkastning a5 = new Avkastning(27.5, "");
    Avkastning a6 = new Avkastning(39.0, "");
    Avkastning a7 = new Avkastning(-35.0, "");
    Avkastning a8 = new Avkastning(-43.0, "");

    @Before
    public void setUp() {
        avk = new AvkastningArkiv();
        c = new Coin();
        c1 = new Coin();
        c2 = new Coin();
        c3 = new Coin();
        c4 = new Coin();
        c5 = new Coin();
        c6 = new Coin();
        c7 = new Coin();
        c8 = new Coin();

        c.setAvkasning(a0);
        c1.setAvkasning(a1);
        c2.setAvkasning(a2);
        c3.setAvkasning(a3);
        c4.setAvkasning(a4);
        c5.setAvkasning(a5);
        c6.setAvkasning(a6);
        c7.setAvkasning(a7);
        c8.setAvkasning(a8);

    }

    @Test
    public void sjekkAtForjeMinusAvkastningStemmer() {
        avk.leggTil(c);
        avk.leggTil(c1);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c2);
        avk.leggTil(c3);
        assertFalse(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c4);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c5);
        avk.leggTil(c6);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c7);
        avk.leggTil(c8);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
    }

    @Test
    public void sjekkAtForjeAvkastningStemmer() {
        avk.leggTil(c);
        assertFalse(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c1);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c2);
        avk.leggTil(c3);
        assertFalse(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c4);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c5);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c6);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
        avk.leggTil(c8);
        avk.leggTil(c7);
        assertTrue(VerdiSjekker.sjekkAvkastning(avk));
    }


}
