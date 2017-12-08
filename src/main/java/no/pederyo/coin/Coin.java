package no.pederyo.coin;

import java.util.ArrayList;

public class Coin {
    private int antall;
    private double investment;
    private double avkasning;
    private double total;
    private double oekning;
    private ArrayList<Double> priser;

    public Coin() {
        this(0, 0.0);
        priser = new ArrayList<>();
    }

    private Coin(int antall, double investment) {
        this.antall = antall;
        this.investment = investment;
        priser = new ArrayList<>();
    }

    void leggTil(Double pris) {
        priser.add(pris);
    }

    public double seForjePris() {
        if (priser.size() >= 2) {
            return priser.get(priser.size() - 2);
        } else {
            return -1.0;
        }
    }

    public double getOekning() {
        return oekning;
    }

    public void setOekning(double oekning) {
        this.oekning = oekning;
    }

    int getAntall() {
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

    void setAvkasning(double avkasning) {
        this.avkasning = avkasning;
    }

    public double getTotal() {
        return total;
    }

    void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Double> getPriser() {
        return priser;
    }

    public void setPriser(ArrayList<Double> priser) {
        this.priser = priser;
    }
}
