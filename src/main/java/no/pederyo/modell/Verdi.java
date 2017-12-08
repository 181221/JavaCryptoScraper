package no.pederyo.modell;

public class Verdi {
    private double pris;
    private String tid;

    public Verdi() {
        this(0.0, "");
    }

    public Verdi(double pris, String tid) {
        this.pris = pris;
        this.tid = tid;
    }


    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
