package no.pederyo.modell;

public class Node<T> {
    private Node<T> neste;
    private T element;

    /**********************************************************
     Oppretter en tom node
     **********************************************************/
    public Node() {
        neste = null;
        element = null;
    }

    /**********************************************************
     Oppretter en node med et element.
     **********************************************************/
    public Node(T elem) {
        neste = null;
        element = elem;
    }

    /**********************************************************
     Returnerer etterfølger.
     **********************************************************/
    public Node<T> getNeste() {
        return neste;
    }

    /**********************************************************
     Setter neste til node
     **********************************************************/
    public void setNeste(Node<T> node) {
        neste = node;
    }

    /**********************************************************
     Returnerer elementet til denne noden
     **********************************************************/
    public T getElement() {
        return element;
    }

    /**********************************************************
     Setter nytt element i denne noden.
     **********************************************************/
    public void setElement(T elem) {
        element = elem;
    }

}
