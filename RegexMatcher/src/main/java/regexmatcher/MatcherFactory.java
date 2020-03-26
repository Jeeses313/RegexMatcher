package regexmatcher;

/**
 * Luokka, joka muodostaa säännöllisen lausekkeen kieleen kuulumiseen
 * käytettävän automaatin.
 *
 * @author JV
 */
public class MatcherFactory {

    /**
     * Luokan konstruktori, jonka parametrina on merkkijono, joka on
     * säännöllinen lauseke, josta muodostetaan automaatti.
     *
     * @param expression String, joka on säännöllinen lauseke, josta automaatti
     * muodostetaan.
     * @param printNFA boolean, joka kertoo tulostetaanko onnistuneesti tehty
     * NFA.
     * @param printDFA boolean, joka kertoo tulostetaanko onnistuneesti tehty
     * DFA.
     * @return Matcher, jota käyetään säännöllisen lausekkeen kieleen kuulumisen
     * tarkistamiseen.
     */
    public Matcher createMatcher(String expression, boolean printNFA, boolean printDFA) {
        if (expression.isEmpty()) {
            return new Matcher(null);
        }
        Matcher matcher = new Matcher(new NFAfactory().generateNFA(expression));
        if (printNFA && matcher.getWorks()) {
            System.out.println("NFA");
            matcher.printAutomate();
        }
        if (matcher.getWorks()) {
            matcher = new Matcher(new DFAfactory().generateDFA(matcher.getAutomate()));
            if (printDFA && matcher.getWorks()) {
                System.out.println("DFA");
                matcher.printAutomate();
            }
            if (!matcher.getWorks()) {
                return new Matcher(null);
            }
        } else {
            return new Matcher(null);
        }
        return matcher;
    }
}
