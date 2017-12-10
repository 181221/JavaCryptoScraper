package no.pederyo.modell;

import static no.pederyo.util.CoinUtil.rengUtProsent;

public class Avkastning {
    private int antall;
    private Node<Coin> start;

    public Avkastning() {
        antall = 0;
        start = null;
    }

    public void leggTil(Coin ny) {
        Node<Coin> nyNode = new Node<>(ny);
        nyNode.setNeste(start);
        start = nyNode;
        antall++;
    }

    public void leggTilAvkastning(Coin coin) {
        double avkasning = rengUtProsent(coin.getTotal(), coin.getInvestment());
        coin.setAvkasning(avkasning);
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
