package regexmatcher;

import regexmatcher.ui.TextUserInterface;
import java.util.Scanner;

/**
 * Pääluokka, joka aloittaa ohjelman toiminnan.
 */
public class RegexMatcher {

    /**
     * Päämetodi, joka aloittaa ohjelman toiminnan tekemällä uuden
     * TextUserInterface, jolle annetaan Scanner, joka lukee syötetä
     * käyttäjältä, ja sitten käynnistää sen.
     *
     * @param args String[], jossa on käynnistyksen yhteydessä annettuja
     * ohjeita, joilla ei tehdä mitään.
     * @see regexmatcher.TextUserInterface
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        TextUserInterface tui = new TextUserInterface(reader);
        tui.start();
    }
}
