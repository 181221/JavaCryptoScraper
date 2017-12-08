package no.pederyo.modell;

import java.util.ArrayList;

public class Coin {
    private int antall;
    private double investment;
    private double avkasning;
    private double total;
    private double oekning;
    private ArrayList<Verdi> verdier;
    private ArrayList<Double> priser;

    public Coin() {
        this(0, 0.0);
        priser = new ArrayList<>();
        verdier = new ArrayList<>();
    }

    public Coin(int antall, double investment) {
        this.antall = antall;
        this.investment = investment;
        priser = new ArrayList<>();
        verdier = new ArrayList<>();
    }

    public void leggTil(Double pris) {
        priser.add(pris);
    }

    public double seForjePris() {
        if (priser.size() >= 2) {
            return priser.get(priser.size() - 2);
        } else {
            return -1.0;
        }
    }

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

    public double getAvkasning() {
        return avkasning;
    }

    public void setAvkasning(double avkasning) {
        this.avkasning = avkasning;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Double> getPriser() {
        return priser;
    }

    public void setPriser(ArrayList<Double> priser) {
        this.priser = priser;
    }
}
