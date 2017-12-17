package no.pederyo.modell;

import static no.pederyo.util.CoinUtil.rengUtProsent;
import static no.pederyo.util.DatoUtil.lagCurrentDate;

public class AvkastningArkiv {
    private int antall;
    private Node<Coin> start;

    public AvkastningArkiv() {
        antall = 0;
        start = null;
    }

    public static Avkastning opprettAvkastning(Coin coin) {
        String tid = lagCurrentDate();
        double AvkastningVerdi = rengUtProsent(coin.getTotal(), coin.getInvestment());
        return new Avkastning(AvkastningVerdi, tid);
    }

    public void leggTil(Coin ny) {
        Node<Coin> nyNode = new Node<>(ny);
        nyNode.setNeste(start);
        start = nyNode;
        antall++;
    }

    public void leggTilAvkastning(Coin coin) {
        Avkastning a = opprettAvkastning(coin);
        coin.setAvkasning(a);
        leggTil(coin);
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public Node<Coin> getStart() {
        return start;
    }

    public void setStart(Node<Coin> start) {
        this.start = start;
    }
}
