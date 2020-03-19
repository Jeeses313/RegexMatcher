package regexmatcher;

import java.util.ArrayList;

/**
 * Luokka, joka toimii verkon solmuna.
 */
public class Node {

    private ArrayList<Edge> edgeList;
    private boolean isEnd;

    /**
     * Luokan konstruktori, joka saa parametrina totuusarvon, onko solmu
     * automaatin hyväksyvä tila.
     *
     * @param isEnd boolean, joka kertoo, onko solmu automaatin hyväksyvä tila
     */
    public Node(boolean isEnd) {
        this.edgeList = new ArrayList<>();
        this.isEnd = isEnd;
    }

    /**
     * Palauttaa solmun vieruslistan.
     *
     * @return ArrayList, joka on solmun vieruslista.
     */
    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * Palauttaa totuusarvon, joka kertoo, onko solmu hyväksyvä tila.
     *
     * @return boolean, joka kertoo, onko solmu hyväksyvä tila.
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * Tekee solmusta hyväksyvän tilan.
     */
    public void setEnd() {
        this.isEnd = true;
    }
}
