package regexmatcher;

/**
 * Automaattien muodostamisen suorituskykyä testaava luokka.
 */
public class FactoryPerformanceTest {

    /**
     * Päämetodi, joka aloittaa testauksen.
     *
     * @param args String[], jossa on käynnistyksen yhteydessä annettuja
     * ohjeita, joilla ei tehdä mitään.
     */
    public static void main(String[] args) {
        testExpressionWithBrackets();
        testExpressionWithStar();
        testExpressionWithPlus();
        testExpressionWithQuestionmark();
        testExpressionWithOr();
    }

    /**
     * Testaa säännöllisiä lausekkeita, joissa on sulkuja.
     */
    private static void testExpressionWithBrackets() {
        int scale = 1;
        while (scale <= 100) {
            String expression = generateBracketExpression(scale);
            testAutomateForming(expression, "Brackets(" + scale + ")");
            scale *= 10;
        }
        System.out.println("------------------------");
    }

    /**
     * Muodostaa annetun pituisen säännöllisenlausekkeen, jossa on sulkuja.
     *
     * @param scale int, joka kertoo muodostettavan säännöllisen lausekkeen
     * pituuden.
     * @return String, joka on muodostettu säännöllinen lauseke.
     */
    private static String generateBracketExpression(int scale) {
        String expression = "";
        for (int i = 0; i < scale; i++) {
            expression += "(a";
        }
        for (int i = 0; i < scale; i++) {
            expression += ")";
        }
        return expression;
    }

    /**
     * Testaa säännöllisiä lausekkeita, joissa on tähtiä.
     */
    private static void testExpressionWithStar() {
        int scale = 1;
        while (scale <= 100) {
            String expression = generateStarExpression(scale);
            testAutomateForming(expression, "Star(" + scale + ")");
            scale *= 10;
        }
        System.out.println("------------------------");
    }

    /**
     * Muodostaa annetun pituisen säännöllisenlausekkeen, jossa on tähtiä.
     *
     * @param scale int, joka kertoo muodostettavan säännöllisen lausekkeen
     * pituuden.
     * @return String, joka on muodostettu säännöllinen lauseke.
     */
    private static String generateStarExpression(int scale) {
        String expression = "";
        for (int i = 0; i < scale; i++) {
            expression += "a*";
        }
        return expression;
    }

    /**
     * Testaa säännöllisiä lausekkeita, joissa on plussia.
     */
    private static void testExpressionWithPlus() {
        int scale = 1;
        while (scale <= 100) {
            String expression = generatePlusExpression(scale);
            testAutomateForming(expression, "Plus(" + scale + ")");
            scale *= 10;
        }
        System.out.println("------------------------");
    }

    /**
     * Muodostaa annetun pituisen säännöllisenlausekkeen, jossa on plussia.
     *
     * @param scale int, joka kertoo muodostettavan säännöllisen lausekkeen
     * pituuden.
     * @return String, joka on muodostettu säännöllinen lauseke.
     */
    private static String generatePlusExpression(int scale) {
        String expression = "";
        for (int i = 0; i < scale; i++) {
            expression += "a+";
        }
        return expression;
    }

    /**
     * Testaa säännöllisiä lausekkeita, joissa on plussia.
     */
    private static void testExpressionWithQuestionmark() {
        int scale = 1;
        while (scale <= 100) {
            String expression = generatePlusExpression(scale);
            testAutomateForming(expression, "Question mark(" + scale + ")");
            scale *= 10;
        }
        System.out.println("------------------------");
    }

    /**
     * Muodostaa annetun pituisen säännöllisenlausekkeen, jossa on plussia.
     *
     * @param scale int, joka kertoo muodostettavan säännöllisen lausekkeen
     * pituuden.
     * @return String, joka on muodostettu säännöllinen lauseke.
     */
    private static String generateQuestionmarkExpression(int scale) {
        String expression = "";
        for (int i = 0; i < scale; i++) {
            expression += "a?";
        }
        return expression;
    }

    /**
     * Testaa säännöllisiä lausekkeita, joissa on |-merkkejä.
     */
    private static void testExpressionWithOr() {
        int scale = 1;
        while (scale <= 100) {
            String expression = generateOrExpression(scale);
            testAutomateForming(expression, "Or(" + scale + ")");
            scale *= 10;
        }
        System.out.println("------------------------");
    }

    /**
     * Muodostaa annetun pituisen säännöllisenlausekkeen, jossa on |-merkkejä.
     *
     * @param scale int, joka kertoo muodostettavan säännöllisen lausekkeen
     * pituuden.
     * @return String, joka on muodostettu säännöllinen lauseke.
     */
    private static String generateOrExpression(int scale) {
        String expression = "";
        for (int i = 0; i < scale; i++) {
            expression += "a|";
        }
        expression += "a";
        return expression;
    }

    /**
     * Testaa ja tulostaa annetun lausekkeen NFA:ksi ja DFA:ksi muuttamiseen
     * kuluvan ajan keskiarvon.
     *
     * @param expression String, joka on säännöllinen lauseke, josta automaatti
     * muodostetaan.
     * @param testName String, joka on testin nimi.
     */
    private static void testAutomateForming(String expression, String testName) {
        long nfaTimesSum = 0;
        long dfaTimesSum = 0;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            List<Node> automate = new NFAfactory().generateNFA(expression);
            long end = System.nanoTime();
            nfaTimesSum += end - start;
            start = System.nanoTime();
            automate = new DFAfactory().generateDFA(automate);
            end = System.nanoTime();
            dfaTimesSum += end - start;
        }
        System.out.println("NFA: " + testName + ": " + (nfaTimesSum / 100) + "ns");
        System.out.println("DFA: " + testName + ": " + (dfaTimesSum / 100) + "ns");
    }
}
