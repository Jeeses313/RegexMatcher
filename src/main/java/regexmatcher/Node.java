package regexmatcher;

/**
 * Luokka, joka toimii verkon solmuna.
 */
public class Node {

    /**
     * Vieruslista.
     */
    private List<Edge> edgeList;
    /**
     * Totuusarvo, joka kertoo, onko solmu automaatin hyväksyvä tila
     */
    private boolean isEnd;

    /**
     * Luokan konstruktori.
     */
    public Node() {
        this.edgeList = new List<>();
        this.isEnd = false;
    }

    /**
     * Palauttaa solmun vieruslistan.
     *
     * @return List, joka on solmun vieruslista.
     */
    public List<Edge> getEdgeList() {
        return edgeList;
    }

    /**
     * Asettaa annetun vieruslistan solmun vieruslistaksi.
     *
     * @param edgeList List, joka on solmun uusi vieruslista.
     */
    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
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
