package regexmatcher;

/**
 * Säännöllisen lausekkeen kieleen kuulumisen tarkistamiseen käytettävä luokka.
 */
public class Matcher {

    private List<Node> automate;
    private boolean useDFA;
    /**
     * Tyhjien merkkien siirtymillä käytyjen tilojen ja ei tyhjien merkkien
     * siirtymillä käytyjen tilojen, jotka johtivat umpikujaan, muistissa
     * pitämiseen tarkoitettu lista.
     */
    private List<Integer> stateMemory;

    /**
     * Luokan konstruktori, jolle annetaan parametriksi Nodeja sisältävä lista,
     * joka toimii automaattina/verkkona ja jolla kieleen kuulumisen tarkistus
     * suoritetaan ja totuusarvon, joka kertoo, onko annettu lista NFA vai DFA.
     *
     * @param automate List, jolla kielen kuulumisen tarkistus suoritetaan.
     * @param useDFA boolean, joka kertoo, onko annettu lista DFA vai NFA.
     */
    public Matcher(List<Node> automate, boolean useDFA) {
        this.automate = automate;
        this.useDFA = useDFA;
    }

    /**
     * Tarkistaa kuuluuko annettu merkkijono annetun automaatin kieleen.
     *
     * @param string String, jonka kuulumista tarkistetaan.
     * @return Totuusarvo, joka kertoo, kuuluuko merkkijono kieleen.
     */
    public boolean match(String string) {
        if (automate == null) {
            return false;
        }
        if (useDFA) {
            return matchDFA(string);
        } else {
            return matchNFA(string);
        }
    }

    /**
     * Tarkistaa merkkijonon kieleen kuulumisen NFA:ssa, eli tekee syvyyshaun
     * autoimaatissa.
     *
     * @param string String, jonka kuulumista tarkistetaan.
     * @return Totuusarvo, joka kertoo, kuuluuko merkkijono kieleen.
     */
    private boolean matchNFA(String string) {
        if (string.length() == 0) {
            if (automate.get(0).isEnd()) {
                return true;
            }
            string = Character.toString((char) 0);
        }
        stateMemory = new List<>();
        return search(string, 0, 0);
    }

    /**
     * Tarkistaa merkkijonon kieleen kuulumisen DFA:ssa, eli tekee syvyyshaun
     * autoimaatissa ilman peruuttamista.
     *
     * @param string String, jonka kuulumista tarkistetaan.
     * @return Totuusarvo, joka kertoo, kuuluuko merkkijono kieleen.
     */
    private boolean matchDFA(String string) {
        if (string.length() == 0) {
            if (automate.get(0).isEnd()) {
                return true;
            }
            return false;
        }
        int currentState = 0;
        for (int i = 0; i < string.length(); i++) {
            boolean edgeFound = false;
            Node currentStateNode = automate.get(currentState);
            for (int j = 0; j < currentStateNode.getEdgeList().size(); j++) {
                Edge currentEdge = currentStateNode.getEdgeList().get(j);
                if (currentEdge.getCaharacter() == string.charAt(i)) {
                    edgeFound = true;
                    currentState = currentEdge.getGoalNode();
                }
            }
            if (!edgeFound) {
                return false;
            }
        }
        return automate.get(currentState).isEnd();
    }

    /**
     * Kertoo, saatiinko annetusta säännöllisestä lausekkeesta tehtyä
     * automaattia.
     *
     * @return Totuusarvo, joka kertoo, onnistuiko automaatin muodostus.
     */
    public boolean getWorks() {
        return automate != null;
    }

    /**
     * Palauttaa automaatin.
     *
     * @return List, joka sisältää automaatin tilat.
     */
    public List<Node> getAutomate() {
        return automate;
    }

    /**
     * Kertoo, onko annettu merkki tavallinen merkki, eli 0-9, a-z tai A-Z.
     *
     * @param character char, jota tutkitaan.
     * @return Totuusarvo, joka kertoo, onko merkki tavallinen.
     */
    private boolean isStandardChar(char character) {
        if ((character >= 48 && character <= 57) || (character >= 65 && character <= 90) || (character >= 97 && character <= 122)) {
            return true;
        }
        return false;
    }

    /**
     * Käy automaattia läpi syvyyshaulla.
     *
     * @param string String, jonka kuulumista tarkistetaan.
     * @param index int, joka kertoo tutkittavan merkin indeksin.
     * @param state int, joka kertoo tämän hetkisen tilan.
     * @return Totuusarvo, joka kertoo ollaanko päästy hyväksyvään tilaan
     * kaikkien merkkien läpikäymisen jälkeen.
     */
    private boolean search(String string, int index, int state) {
        char currentChar = (char) 174;
        if (index >= string.length()) {
            if (automate.get(state).isEnd()) {
                return true;
            }
        } else {
            currentChar = string.charAt(index);
            if (!isStandardChar(currentChar) && (int) currentChar != 0) {
                return false;
            }
        }
        boolean result = false;
        for (int i = 0; i < automate.get(state).getEdgeList().size(); i++) {
            Edge edge = automate.get(state).getEdgeList().get(i);
            if (result) {
                break;
            }
            if (edge.getCaharacter() == currentChar) {
                stateMemory = new List<>();
                result = search(string, index + 1, edge.getGoalNode());
            } else if (edge.getCaharacter() == (char) 0 && !stateMemory.contains(edge.getGoalNode())) {
                stateMemory.add(edge.getGoalNode());
                result = search(string, index, edge.getGoalNode());
            }
        }
        stateMemory.add(state);
        return result;
    }

    /**
     * Tulostaa automaatin.
     */
    public void printAutomate() {
        for (int i = 0; i < automate.size(); i++) {
            Node node = automate.get(i);
            System.out.println("State: " + i + " Is end: " + node.isEnd());
            System.out.println("Edges(char --> goal state): ");
            for (int j = 0; j < node.getEdgeList().size(); j++) {
                Edge edge = node.getEdgeList().get(j);
                System.out.println(edge.getCaharacter() + " --> " + edge.getGoalNode());
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
