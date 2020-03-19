package regexmatcher;

import java.util.ArrayList;

/**
 * Säännöllisen lausekkeen kieleen kuulumisen tarkistamiseen käytettävä luokka.
 */
public class Matcher {

    private ArrayList<Node> automate;
    private int currentState;
    private int index;

    /**
     * Luokan konstruktori, jolle annetaan parametriksi Nodeja sisältävä lista,
     * joka toimii automaattina/verkkona ja jolla kieleen kuulumisen tarkistus
     * suoritetaan.
     *
     * @param automate ArrayList, jolla kielen kuulumisen tarkistus suoritetaan.
     */
    public Matcher(ArrayList<Node> automate) {
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
        index = 0;
        currentState = 0;
        while (true) {
            char character = string.charAt(index);
            boolean found = searchEdges(character);
            if (!found || !isStandardChar(character)) {
                return false;
            }
            index++;
            if (index >= string.length()) {
                break;
            }
        }
        return automate.get(currentState).isEnd();
    }

    /**
     * Käy läpi tällä hetkellä olevan tilan vieruslistan kaaria ja etsii, onko
     * siellä kaarta, jolla voidaan edetä.
     *
     * @param character char, jonka sopivuutta merkkijonon kyseisessä kohdassa
     * tutkitaan.
     * @return Totuusarvo, joka kertoo sopiiko merkki, eli voidaanko edetä.
     */
    private boolean searchEdges(char character) {
        boolean edgeFound = false;
        for (Edge edge : automate.get(currentState).getEdgeList()) {
            if (edge.getCaharacter() == character || edge.getCaharacter() == '.') {
                currentState = edge.getGoalNode();
                edgeFound = true;
                break;
            } else if (edge.getCaharacter() == 0) {
                currentState = edge.getGoalNode();
                edgeFound = true;
                index--;
                break;
            }
        }
        return edgeFound;
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

}
