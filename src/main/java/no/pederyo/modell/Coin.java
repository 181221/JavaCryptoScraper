package no.pederyo.modell;

import java.util.ArrayList;

/**
 * @antall antall Iota Coins.
 * @investment din investering.
 * @avkastning din total-invest/invest.
 * @total Ditt belop i NOK basert på Iota verdi.
 * @oekning økningen på iota.
 * @verdier liste med Verdi, pris og datoen.
 */
public class Coin {
    private int antall;
    private double investment;
    private double total;
    private double oekning;
    private Avkastning avkastning;
    private ArrayList<Verdi> verdier;

    public Coin() {
        this(0, 0.0);
        verdier = new ArrayList<>();
    }

    public Coin(int antall, double investment) {
        this.antall = antall;
        this.investment = investment;
        verdier = new ArrayList<>();
    }

    public void leggTil(Verdi verdi) {
        verdier.add(verdi);
    }

    public double seForjePris() {
        if (verdier.size() >= 2) {
            return verdier.get(verdier.size() - 2).getPris();
        } else {
            return -1.0;
        }
    }

    // ------------- GETTERS OG SETTERS ---------------------

    public ArrayList<Verdi> getVerdier() {
        return verdier;
    }

    public void setVerdier(ArrayList<Verdi> verdier) {
        this.verdier = verdier;
    }

    public double getOekning() {
        return oekning;
    }

    public void setOekning(double oekning) {
        this.oekning = oekning;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public double getInvestment() {
        return investment;
    }

    public void setInvestment(double investment) {
        this.investment = investment;
    }

    public Avkastning getAvkasning() {
        return avkastning;
    }

    public void setAvkasning(Avkastning avkasning) {
        this.avkastning = avkasning;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
