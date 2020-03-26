package regexmatcher;

/**
 * Luokka, jonka avulla muodostetaan DFA.
 */
public class DFAfactory {

    /**
     * Muodostettava/valmis DFA.
     */
    private List<Node> automate;
    /**
     * Yhdistettyjen tilojen lista.
     */
    private List<DFAnode> dfa;
    private List<Node> nfa;
    /**
     * Käsiteltyjen yhdistettyjen tilojen lista.
     */
    private List<Integer> dfaFormed;

    /**
     * Muodostaa DFA:n annetusta NFA:sta.
     *
     * @param nfa List, eli nfa, josta dfa muodostetaan.
     * @return List, eli muodostettu DFA. Jos annettu nfa on tyhjä tai null,
     * palauttaa null.
     */
    public List<Node> generateDFA(List<Node> nfa) {
        if (nfa == null || nfa.isEmpty()) {
            return null;
        }
        this.nfa = nfa;
        automate = new List<>();
        dfa = new List<>();
        dfaFormed = new List<>();
        formCurrentNode(0);
        formDFA(0);
        formAutomate();
        return automate;
    }

    /**
     * Muodostaa lopullisen DFA:n, eli muuttaa NFA:sta tehdyn yhdistettyjen
     * solmujen automaatiin yksinkertaisten solmujen automaatiksi.
     */
    private void formAutomate() {
        for (int i = 0; i < dfa.size(); i++) {
            Node node = new Node();
            DFAnode dfaNode = dfa.get(i);
            node.setEdgeList(dfaNode.getEdgeList());
            if (dfaNode.isEnd()) {
                node.setEnd();
            }
            automate.add(node);
        }
    }

    /**
     * Muodostaa yhdistetyn tilan, eli kaikkien tyhjien merkkien siirtymien
     * päässä olevan tilan, jossa annetussa NFA:n tilassa ollaan.
     *
     * @param state int, joka viittaa NFA:n tilaan, jossa tällä hetkellä ollaan.
     * @return int, joka viittaa muodostettuun yhdistettyyn tilaan yhdistettyjen
     * tilojen listassa.
     */
    private int formCurrentNode(int state) {
        DFAnode currentNode = new DFAnode();
        currentNode.getNodeList().add(state);
        nodeSearch(state, currentNode);
        if (dfa.contains(currentNode)) {
            for (int i = 0; i < dfa.size(); i++) {
                if (dfa.get(i).equals(currentNode)) {
                    currentNode = dfa.get(i);
                    return i;
                }
            }
        } else {
            checkIfCurrentIsGoal(currentNode);
            dfa.add(currentNode);
            return dfa.size() - 1;
        }
        return -1;
    }

    /**
     * Etsii syvyyshaulla kaikki tilat, joihin päästään tyhjillä merkeillä ja
     * lisää ne muodostettavan yhdistetyn tilan tilojen listaan.
     *
     * @param state int, joka viittaa NFA:n tilaan, jossa tällä hetkellä ollaan.
     * @param currentNode DFAnode, eli yhdistetty tila, jota ollaan
     * muodostamassa.
     */
    private void nodeSearch(int state, DFAnode currentNode) {
        for (int i = 0; i < nfa.get(state).getEdgeList().size(); i++) {
            Edge edge = nfa.get(state).getEdgeList().get(i);
            if (edge.getCaharacter() == (char) 0 && !currentNode.getNodeList().contains(edge.getGoalNode())) {
                currentNode.getNodeList().add(edge.getGoalNode());
                nodeSearch(edge.getGoalNode(), currentNode);
            }
        }
    }

    /**
     * Tarkistaa, onko yhdistetyn tilan tiloissa hyväksyviä tiloja ja jos on,
     * niin merkitsee yhdistetyn tilan hyväksyväksi tilaksi.
     *
     * @param node DFAnode, eli yhditetty tila, jota tarkistetaan.
     */
    private void checkIfCurrentIsGoal(DFAnode node) {
        for (int i = 0; i < node.getNodeList().size(); i++) {
            if (nfa.get(node.getNodeList().get(i)).isEnd()) {
                node.setEnd();
                return;
            }
        }
    }

    /**
     * Mudostaa DFA:n käyden yhdistettyjen tilojen sisältämien solmujen kaaria
     * läpi.
     *
     * @param state int, joka viittaa yhdistettyyn tilaan, jonka kaaria käydään
     * läpi.
     */
    private void formDFA(int state) {
        Stack<Integer> dfaGoalStack = new Stack<>();
        for (int i = 0; i < dfa.get(state).getNodeList().size(); i++) {
            Node node = nfa.get(dfa.get(state).getNodeList().get(i));
            for (int j = 0; j < node.getEdgeList().size(); j++) {
                Edge edge = node.getEdgeList().get(j);
                if (edge.getCaharacter() != (char) 0) {
                    int dfaGoal = formCurrentNode(edge.getGoalNode());
                    dfa.get(state).getEdgeList().add(new Edge(dfaGoal, edge.getCaharacter()));
                    if (!dfaFormed.contains(dfaGoal)) {
                        dfaFormed.add(dfaGoal);
                        dfaGoalStack.push(dfaGoal);
                    }
                }
            }
        }
        while (!dfaGoalStack.isEmpty()) {
            formDFA(dfaGoalStack.pop());
        }
    }

}
