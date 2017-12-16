package no.pederyo.modell;

/**
 * Created by Peder on 16.12.2017.
 */
public class Avkastning {
    double verdi;
    String tid;

    public Avkastning(double verdi, String tid) {
        this.verdi = verdi;
        this.tid = tid;
    }

    public double getVerdi() {
        return verdi;
    }

    public void setVerdi(double verdi) {
        this.verdi = verdi;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
