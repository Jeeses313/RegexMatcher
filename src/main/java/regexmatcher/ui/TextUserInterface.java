package regexmatcher.ui;

import java.util.Scanner;
import regexmatcher.util.Matcher;
import regexmatcher.util.MatcherFactory;

/**
 * Luokka toteuttaa ohjelman tekstikäyttöliittymän toiminnan
 */
public class TextUserInterface {

    private Scanner reader;
    private MatcherFactory mfactory;

    /**
     * Luokan konstruktori, jolle annetaan Scanner-olio, josta syötettä halutaan
     * lukea.
     *
     * @param reader Scanner, josta syötettä luetaan.
     */
    public TextUserInterface(Scanner reader) {
        this.reader = reader;
        this.mfactory = new MatcherFactory();
    }

    /**
     * Käynnistää tekstikäyttöliittymän.
     */
    public void start() {
        printInstructions();
        while (true) {
            String expression = requestInput("Give regular expression: ");
            if (expression.equals("-q")) {
                break;
            }
            if (expression.equals("-i")) {
                printInstructions();
                continue;
            }
            Matcher matcher;
            boolean printDfa = false;
            boolean printNfa = requestBoolean("Print NFA?(y/n): ");
            boolean useDfa = requestBoolean("Use DFA instead of NFA?(y/n): ");
            if (useDfa) {
                printDfa = requestBoolean("Print DFA?(y/n): ");
            }
            matcher = mfactory.createMatcher(expression, useDfa, printNfa, printDfa);
            if (matcher.getWorks()) {
                boolean end = matchStrings(matcher);
                if (end) {
                    break;
                }
            } else {
                System.out.println("Give real regular expression");
            }
        }
        System.out.println("\nExiting...");
    }

    /**
     * Tulostaa ohjeita ohjelman käyttöön.
     */
    private void printInstructions() {
        System.out.println("Regular expression matcher");
        System.out.println("Syntax:");
        System.out.println("- * = previous 0-n times");
        System.out.println("- + = previous 1-n times");
        System.out.println("- ? = previous 0-1 times");
        System.out.println("- | = or");
        System.out.println("- [a-z], [A-Z] and [0-9]");
        System.out.println("- ()");
        System.out.println("- . = {0, ..., 9, a, ..., z, A, ..., Z}");
        System.out.println("Type -q at any time to quit");
        System.out.println("Type -r to change regular expression");
        System.out.println("Type -i at any time to get these instructions");
    }

    /**
     * Tulostaa parametrina annetun kysymyksen ja palauttaa Scannerista luetun
     * vastauksen.
     *
     * @param request String, joka esitetään kysymyksenä ennen syötteen antoa.
     * @return Scannerista luettu syöterivi.
     */
    private String requestInput(String request) {
        System.out.println(request);
        return reader.nextLine();
    }

    /**
     * Tulostaa parametrina annetun kysymyksen ja palauttaa vastauksen
     * totuusarvona.
     *
     * @param request String, joka esitetään kysymyksenä ennen syötteen antoa.
     * @return Totuusarvo, jota luettu vastaus vastaa.
     */
    private boolean requestBoolean(String request) {
        String answer = requestInput(request);
        return (answer.equals("Y") || answer.equals("y"));
    }

    /**
     * Katsoo kuuluuko merkkijon annetun säännöllisen lausekkeen kieleen ja
     * tulostaa vastauksen.
     *
     * @param string String, jonka kuuluvuutta tarkistetaan.
     * @param matcher Matcher, jolla tarkistetaan annetun merkkijonon kieleen
     * kuuluminen.
     */
    private void match(String string, Matcher matcher) {
        boolean matches = matcher.match(string);
        if (matches) {
            System.out.println("String matches");
        } else {
            System.out.println("String does not match");
        }
    }

    /**
     * Pyytää merkkijonoja, joista tarkistetaan, kuuluvatko ne annetun
     * säännöllisen lausekkeen kieleen.
     *
     * @param matcher Matcher, jolla tarkistetaan annetun merkkijonon kieleen
     * kuuluminen.
     * @return Totuusarvo, joka kertoo lopetetaanko ohjelma vai pyydetäänkö
     * uutta säännöllistä lauseketta.
     */
    private boolean matchStrings(Matcher matcher) {
        while (true) {
            String string = requestInput("Give string: ");
            if (string.equals("-q")) {
                return true;
            }
            if (string.equals("-r")) {
                return false;
            }
            if (string.equals("-i")) {
                printInstructions();
                continue;
            }
            match(string, matcher);
        }
    }
}
