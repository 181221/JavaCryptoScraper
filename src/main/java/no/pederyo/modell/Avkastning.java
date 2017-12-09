package no.pederyo.modell;

import static no.pederyo.util.CoinUtil.rengUtProsent;

public class Avkastning {
    private int antall;
    private Node<Double> start;

    public Avkastning() {
        antall = 0;
        start = null;
    }

    public void leggTil(Double ny) {
        Node<Double> nyNode = new Node<>(ny);
        nyNode.setNeste(start);
        start = nyNode;
        antall++;
    }

    public void leggTilAvkastning(Coin coin) {
        double avkasning = rengUtProsent(coin.getTotal(), coin.getInvestment());
        leggTil(avkasning);
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

    public Node<Double> getStart() {
        return start;
    }

    public void setStart(Node<Double> start) {
        this.start = start;
    }
}
