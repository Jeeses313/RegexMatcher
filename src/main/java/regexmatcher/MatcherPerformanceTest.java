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
        testSimpleExpression();
        testSimpleExpressionWithOr();
        testDifficultExpression();
        testDifficultExpressionWithOr();
    }

    /**
     * Testaa yksinkertaista säännöllistä lauseketta.
     */
    private static void testSimpleExpression() {
        String expression = "a*b*c*";
        List<String> strings = new List<>();
        strings.add("");
        strings.add("a");
        strings.add("aa");
        strings.add("b");
        strings.add("bb");
        strings.add("c");
        strings.add("cc");
        strings.add("d");
        strings.add("abc");
        strings.add("aabbcc");
        strings.add("ccbbaa");
        strings.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccccccccc");
        strings.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccccccccca");
        testMatching(expression, "Simple expression", strings);
        System.out.println("------------------------");
    }

    /**
     * Testaa yksinkertaista säännöllistä lauseketta, jossa on tai-osioita.
     */
    private static void testSimpleExpressionWithOr() {
        String expression = "ab*c|a*bc|abc*";
        List<String> strings = new List<>();
        strings.add("");
        strings.add("ab");
        strings.add("bc");
        strings.add("ac");
        strings.add("abc");
        strings.add("aaaaaaaaaaaaaaaaaaaabc");
        strings.add("abbbbbbbbbbbbbbbbbbbbc");
        strings.add("abcccccccccccccccccccc");
        strings.add("aaaaaaaaaaaaaaaaaaaaaaaaaabcccccccccccccccccccc");
        strings.add("aaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbc");
        strings.add("abbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccc");
        strings.add("cba");
        testMatching(expression, "Simple expression with or", strings);
        System.out.println("------------------------");
    }

    /**
     * Testaa vaikeaa säännöllistä lauseketta.
     */
    private static void testDifficultExpression() {
        String expression = "([a-z]*[0-9])+";
        List<String> strings = new List<>();
        strings.add("");
        strings.add("1");
        strings.add("9");
        strings.add("a1");
        strings.add("z9");
        strings.add("A");
        strings.add("a1b2C3");
        strings.add("aaaaaaaaaaaaaaaaaaaaaa");
        strings.add("aaaaaaaaaaaaaaaaaaaaaa1");
        strings.add("aaaaaaaaaaaaaaaaaaaaaa1aaaaaaaaaaaaaaaaaaa");
        strings.add("aaaaaaaaaaaaaaaaaaaaaa1aaaaaaaaaaaaaaaaaaa1");
        strings.add("abcdefghijklmnopqrstuvwxyz0123456789");
        testMatching(expression, "Difficult expression", strings);
        System.out.println("------------------------");
    }

    /**
     * Testaa vaikeaa säännöllistä lauseketta, jossa on tai-osioita.
     */
    private static void testDifficultExpressionWithOr() {
        String expression = "(abc|[0-9]*|a*b*c*)+";
        List<String> strings = new List<>();
        strings.add("");
        strings.add("ac");
        strings.add("abc");
        strings.add("cba");
        strings.add("cbabc");
        strings.add("cbabcc");
        strings.add("d");
        strings.add("aabbcc");
        strings.add("abcabcabc");
        strings.add("0123456789");
        strings.add("abcabcaabbccaaabbbcccaaaabbbbcccc");
        strings.add("abc0123456789abcccccccccccccccccc");
        testMatching(expression, "Difficult expression with or", strings);
        System.out.println("------------------------");
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
    private static void testMatching(String expression, String testName, List<String> strings) {
        long[] nfaTimes = new long[1000];
        long[] dfaTimes = new long[1000];
        for (int j = 0; j < 1000; j++) {
            long start = System.nanoTime();
            Matcher matcher = new Matcher(new NFAfactory().generateNFA(expression), false);
            for (int i = 0; i < strings.size(); i++) {
                matcher.match(strings.get(i));
            }
            long end = System.nanoTime();
            nfaTimes[j] = (end - start);
            start = System.nanoTime();
            matcher = new Matcher(new DFAfactory().generateDFA(new NFAfactory().generateNFA(expression)), true);
            for (int i = 0; i < strings.size(); i++) {
                matcher.match(strings.get(i));
            }
            end = System.nanoTime();
            dfaTimes[j] = (end - start);
        }
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
