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
        List<Integer> stateList = new List<>();
        stateList.add(0);
        formCurrentNode(stateList);
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
     * päässä olevan tilan, jossa annetuissa NFA:n tiloissa ollaan.
     *
     * @param stateList List, joka viittaa NFA:n tiloihin, joissa tällä hetkellä
     * ollaan.
     * @return int, joka viittaa muodostettuun yhdistettyyn tilaan yhdistettyjen
     * tilojen listassa.
     */
    private int formCurrentNode(List<Integer> stateList) {
        DFAnode currentNode = new DFAnode();
        for (int i = 0; i < stateList.size(); i++) {
            if (!currentNode.getNodeList().contains(stateList.get(i))) {
                currentNode.getNodeList().add(stateList.get(i));
                nodeSearch(stateList.get(i), currentNode);
            }
        }
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
        List<Character> edgeCharacters = new List<>();
        List<List<Integer>> nfaGoalList = new List<>();
        collectEdges(state, edgeCharacters, nfaGoalList);
        for (int i = 0; i < edgeCharacters.size(); i++) {
            int dfaGoal = formCurrentNode(nfaGoalList.get(i));
            dfa.get(state).getEdgeList().add(new Edge(dfaGoal, edgeCharacters.get(i)));
            if (!dfaFormed.contains(dfaGoal)) {
                dfaFormed.add(dfaGoal);
                dfaGoalStack.push(dfaGoal);
            }
        }
        while (!dfaGoalStack.isEmpty()) {
            formDFA(dfaGoalStack.pop());
        }
    }

    /**
     * Käy yhdistetyn tilan sisältämien solmujen kaaria ja lisää niiden
     * kirjaimet ja kirjaimia vastaavat maalisolmut annetuille listoille.
     *
     * @param state int, joka viittaa yhdistettyyn tilaan, jonka kaaria käydään
     * läpi.
     * @param edgeCharacters List, johon kaarien kirjaimet talletetaan.
     * @param nfaGoalList List, jonka sisältämiin listoihin kaarien kirjaimia
     * vastaavat maalisomut talletetaan.
     */
    private void collectEdges(int state, List<Character> edgeCharacters, List<List<Integer>> nfaGoalList) {
        for (int i = 0; i < dfa.get(state).getNodeList().size(); i++) {
            Node node = nfa.get(dfa.get(state).getNodeList().get(i));
            for (int j = 0; j < node.getEdgeList().size(); j++) {
                Edge edge = node.getEdgeList().get(j);
                if (edge.getCaharacter() != (char) 0) {
                    int index = edgeCharacters.indexOf(edge.getCaharacter());
                    if (index != -1 && !nfaGoalList.get(index).contains(edge.getGoalNode())) {
                        nfaGoalList.get(index).add(edge.getGoalNode());
                    } else if (index == -1) {
                        edgeCharacters.add(edge.getCaharacter());
                        nfaGoalList.add(new List<>());
                        nfaGoalList.get(nfaGoalList.size() - 1).add(edge.getGoalNode());
                    }
                }
            }
        }
    }

}
