package regexmatcher;

/**
 * Säännöllisen lausekkeen kieleen kuulumisen tarkistamiseen käytettävä luokka.
 */
public class Matcher {

    private List<Node> automate;

    /**
     * Luokan konstruktori, jolle annetaan parametriksi Nodeja sisältävä lista,
     * joka toimii automaattina/verkkona ja jolla kieleen kuulumisen tarkistus
     * suoritetaan.
     *
     * @param automate List, jolla kielen kuulumisen tarkistus suoritetaan.
     */
    public Matcher(List<Node> automate) {
        this.automate = automate;
    }

    /**
     * Tarkistaa kuuluuko annettu merkkijono annetun atomaatin kieleen.
     *
     * @param string String, jonka kuulumista tarkistetaan.
     * @return Totuusarvo, joka kertoo, kuuluuko merkkijono kieleen.
     */
    public boolean match(String string) {
        if (automate == null) {
            return false;
        }
        if (string.length() == 0) {
            if (automate.get(0).isEnd()) {
                return true;
            }
            string = Character.toString((char) 0);
        }
        return search(string, 0, 0);
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
                result = search(string, index + 1, edge.getGoalNode());
            } else if (edge.getCaharacter() == (char) 0) {
                result = search(string, index, edge.getGoalNode());
            }
        }
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
