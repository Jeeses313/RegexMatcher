package regexmatcher;

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
     * kaikkien annettujen merkkijonojen tarkistamiseen kuluvan ajan keskiarvon.
     *
     * @param expression String, joka on säännöllinen lauseke, josta automaatti
     * muodostetaan.
     * @param testName String, joka on testin nimi.
     * @param string List, joka sisältää tarkistettavat merkkijonot.
     */
    private static void testMatching(String expression, String testName, List<String> strings) {
        long nfaTimesSum = 0;
        long dfaTimesSum = 0;
        for (int j = 0; j < 1000; j++) {
            long start = System.nanoTime();
            Matcher matcher = new Matcher(new NFAfactory().generateNFA(expression), false);
            for (int i = 0; i < strings.size(); i++) {
                matcher.match(strings.get(i));
            }
            long end = System.nanoTime();
            nfaTimesSum += end - start;
            start = System.nanoTime();
            matcher = new Matcher(new DFAfactory().generateDFA(new NFAfactory().generateNFA(expression)), true);
            for (int i = 0; i < strings.size(); i++) {
                matcher.match(strings.get(i));
            }
            end = System.nanoTime();
            dfaTimesSum += end - start;
        }
        System.out.println("NFA: " + testName + ": " + (nfaTimesSum / 100) + "ns");
        System.out.println("DFA: " + testName + ": " + (dfaTimesSum / 100) + "ns");
        System.out.println("Difference(NFA-DFA): " + ((nfaTimesSum / 100) - (dfaTimesSum / 100)));
    }
}
