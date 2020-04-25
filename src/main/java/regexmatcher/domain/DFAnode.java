package regexmatcher.domain;

/**
 * Luokka, joka toimii verkon solmuna DFA:ta muodostaessa, eli yhdistettyjen
 * tilojen solmuna.
 */
public class DFAnode {

    /**
     * Vieruslista.
     */
    private List<Edge> edgeList;
    /**
     * Totuusarvo, joka kertoo, onko solmu automaatin hyväksyvä tila
     */
    private boolean isEnd;
    /**
     * Solmun käsittämien DFA:n solmujen lista.
     */
    private List<Integer> nodeList;

    /**
     * Luokan konstruktori.
     */
    public DFAnode() {
        this.edgeList = new List<>();
        this.nodeList = new List<>();
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

    /**
     * Palauttaa solmun käsittämien DFA:n solmujen listan.
     *
     * @return List, joka sisältää solmun käsittämien DFA solmujen listan.
     */
    public List<Integer> getNodeList() {
        return nodeList;
    }

    /**
     * Palauttaa totuusarvon, joka kertoo, onko verrattava objekti sama, eli
     * sama luokka ja käsittävätkö solmut samat DFA:n solmut.
     *
     * @param obj Objekti, johon verrataan.
     * @return Totuusarvo, joka kertoo, ovatko objektit sama luokka ja
     * käsittävätkö solmut samat DFA:n solmut.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        DFAnode node = (DFAnode) obj;
        if (nodeList.size() != node.getNodeList().size()) {
            return false;
        }
        for (int i = 0; i < nodeList.size(); i++) {
            if (!node.getNodeList().contains(nodeList.get(i))) {
                return false;
            }
        }
        return true;
    }

}
