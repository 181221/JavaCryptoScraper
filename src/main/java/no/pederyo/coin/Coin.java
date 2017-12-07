package no.pederyo.coin;

public class Coin {
    private int antall;
    private double investment;
    private double avkasning;
    private double total;

    public Coin(int antall, double investment) {
        this.antall = antall;
        this.investment = investment;
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

    public void setAvkasning(double avkasning) {
        this.avkasning = avkasning;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
