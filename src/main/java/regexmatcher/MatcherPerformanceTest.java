package regexmatcher;

import regexmatcher.util.Matcher;
import regexmatcher.util.NFAfactory;
import regexmatcher.util.DFAfactory;
import regexmatcher.domain.List;

/**
 * Merkkijonojen kieleen kuulumisen tarkistamisen suorituskykyä testaava luokka.
 */
public class MatcherPerformanceTest {

    /**
     * Päämetodi, joka aloittaa testauksen.
     *
     * @param args String[], jossa on käynnistyksen yhteydessä annettuja
     * ohjeita, joilla ei tehdä mitään.
     */
    public static void main(String[] args) {
        List<List<String>> strings = generateListOfStrings();
        testSimpleExpression(strings);
        testSimpleExpressionWithOr(strings);
        testDifficultExpression(strings);
        testDifficultExpressionWithOr(strings);
    }

    /**
     * Testaa yksinkertaista säännöllistä lauseketta.
     */
    private static void testSimpleExpression(List<List<String>> strings) {
        String expression = "a*b*c*";
        testMatching(expression, "Simple expression(1)", strings, 1);
        testMatching(expression, "Simple expression(2)", strings, 2);
        testMatching(expression, "Simple expression(3)", strings, 3);
        testMatching(expression, "Simple expression(4)", strings, 4);
        testMatching(expression, "Simple expression(5)", strings, 5);
        testMatching(expression, "Simple expression(6)", strings, 6);
        testMatching(expression, "Simple expression(7)", strings, 7);
        System.out.println("------------------------");
    }

    /**
     * Testaa yksinkertaista säännöllistä lauseketta, jossa on tai-osioita.
     */
    private static void testSimpleExpressionWithOr(List<List<String>> strings) {
        String expression = "ab*c|a*bc|abc*";
        testMatching(expression, "Simple expression with or(1)", strings, 1);
        testMatching(expression, "Simple expression with or(2)", strings, 2);
        testMatching(expression, "Simple expression with or(3)", strings, 3);
        testMatching(expression, "Simple expression with or(4)", strings, 4);
        testMatching(expression, "Simple expression with or(5)", strings, 5);
        testMatching(expression, "Simple expression with or(6)", strings, 6);
        testMatching(expression, "Simple expression with or(7)", strings, 7);
        System.out.println("------------------------");
    }

    /**
     * Testaa vaikeaa säännöllistä lauseketta.
     */
    private static void testDifficultExpression(List<List<String>> strings) {
        String expression = "([a-z]*[0-9])+";
        testMatching(expression, "Difficult expression(1)", strings, 1);
        testMatching(expression, "Difficult expression(2)", strings, 2);
        testMatching(expression, "Difficult expression(3)", strings, 3);
        testMatching(expression, "Difficult expression(4)", strings, 4);
        testMatching(expression, "Difficult expression(5)", strings, 5);
        testMatching(expression, "Difficult expression(6)", strings, 6);
        testMatching(expression, "Difficult expression(7)", strings, 7);
        System.out.println("------------------------");
    }

    /**
     * Testaa vaikeaa säännöllistä lauseketta, jossa on tai-osioita.
     */
    private static void testDifficultExpressionWithOr(List<List<String>> strings) {
        String expression = "(abc|[0-9]*|a*b*c*)+";
        testMatching(expression, "Difficult expression with or(1)", strings, 1);
        testMatching(expression, "Difficult expression with or(2)", strings, 2);
        testMatching(expression, "Difficult expression with or(3)", strings, 3);
        testMatching(expression, "Difficult expression with or(4)", strings, 4);
        testMatching(expression, "Difficult expression with or(5)", strings, 5);
        testMatching(expression, "Difficult expression with or(6)", strings, 6);
        testMatching(expression, "Difficult expression with or(7)", strings, 7);
        System.out.println("------------------------");
    }

    /**
     * Muodostaa listan merkkijonoja, joiden enimmäispituus ja sisältämät
     * kirjaimet on annettu.
     *
     * @param charList List, joka sisältää kirjaimet, joita merkkijonoissa
     * voidaan käyttää.
     * @param length int, merkkijonojen enimmäispituus.
     * @return List, joka sisältää muodostetut merkkijonot.
     */
    private static List<List<String>> generateListOfStrings() {
        List<Character> charList = new List<>();
        charList.add('a');
        charList.add('b');
        charList.add('c');
        charList.add('d');
        charList.add('0');
        charList.add('1');
        charList.add('2');
        charList.add('3');
        List<List<String>> strings = new List<>();
        for (int i = 0; i <= 7; i++) {
            strings.add(new List<>());
        }
        fillListOfStringWithStringOfCertainLength(strings, charList, "", 7);
        return strings;
    }

    /**
     * Täyttää annetun listan halutun pituisilla merkkijonoilla, jotka on
     * muodostettu annetuista merkeistä.
     *
     * @param strings List, joka täytetään muodostetuilla merkkijonoilla.
     * @param charList List, joka sisältää kirjaimet, joita merkkijonoissa
     * voidaan käyttää.
     * @param currentString String, joka lisätään listaan ja josta muodostetaan
     * pidempiä merkkijonoja.
     * @param length int, merkkijonojen enimmäispituus.
     */
    private static void fillListOfStringWithStringOfCertainLength(List<List<String>> strings, List<Character> charList, String currentString, int length) {
        strings.get(currentString.length()).add(currentString);
        if (currentString.length() == length) {
            return;
        }
        for (int i = 0; i < charList.size(); i++) {
            fillListOfStringWithStringOfCertainLength(strings, charList, currentString + charList.get(i), length);
        }
    }

    /**
     * Testaa ja tulostaa annetun lausekkeen NFA:ksi ja DFA:ksi muuttamiseen ja
     * kaikkien annettujen merkkijonojen tarkistamiseen kuluvan ajan mediaanin.
     *
     * @param expression String, joka on säännöllinen lauseke, josta automaatti
     * muodostetaan.
     * @param testName String, joka on testin nimi.
     * @param strings List, joka sisältää tarkistettavat merkkijonot.
     */
    private static void testMatching(String expression, String testName, List<List<String>> strings, int maxLength) {
        long[] nfaTimes = new long[1000];
        long[] dfaTimes = new long[1000];
        for (int j = 0; j < 1000; j++) {
            long start = System.nanoTime();
            Matcher matcher = new Matcher(new NFAfactory().generateNFA(expression), false);
            for (int i = 0; i < maxLength; i++) {
                for (int k = 0; k < strings.get(i).size(); k++) {
                    matcher.match(strings.get(i).get(k));
                }
            }
            long end = System.nanoTime();
            nfaTimes[j] = (end - start);
            start = System.nanoTime();
            matcher = new Matcher(new DFAfactory().generateDFA(new NFAfactory().generateNFA(expression)), true);
            for (int i = 0; i < maxLength; i++) {
                for (int k = 0; k < strings.get(i).size(); k++) {
                    matcher.match(strings.get(i).get(k));
                }
            }
            end = System.nanoTime();
            dfaTimes[j] = (end - start);
        }
        sort(nfaTimes);
        sort(dfaTimes);
        System.out.println("NFA: " + testName + ": " + (nfaTimes[1000 / 2]) + "ns");
        System.out.println("DFA: " + testName + ": " + (dfaTimes[1000 / 2]) + "ns");
        System.out.println("Difference(NFA-DFA): " + ((nfaTimes[1000 / 2]) - (dfaTimes[1000 / 2])));
    }

    /**
     * Järjestää annetun long-taulukon lisäysjärjestyksellä.
     *
     * @param array Taulukko, joka järjestetään.
     */
    private static void sort(long[] array) {
        for (int i = 1; i < array.length; ++i) {
            long current = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > current) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = current;
        }
    }
}
