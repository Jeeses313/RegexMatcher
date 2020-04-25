package regexmatcher.util;

/**
 * Luokka, joka muodostaa säännöllisen lausekkeen kieleen kuulumiseen
 * käytettävän automaatin.
 *
 * @author JV
 */
public class MatcherFactory {

    /**
     * Luokan konstruktori, jonka parametreina ovat merkkijono, joka on
     * säännöllinen lauseke, josta muodostetaan automaatti, ja totuusarvoja,
     * jotka kertovat, käytetäänkö DFA:ta NFA:n sijaan ja tulostetaanko
     * automaatit.
     *
     * @param expression String, joka on säännöllinen lauseke, josta automaatti
     * muodostetaan.
     * @param useDFA boolean, joka kertoo, käytetäänkö DFA:ta NFA:n sijaan.
     * @param printNFA boolean, joka kertoo tulostetaanko onnistuneesti tehty
     * NFA.
     * @param printDFA boolean, joka kertoo tulostetaanko onnistuneesti tehty
     * DFA.
     * @return Matcher, jota käytetään säännöllisen lausekkeen kieleen
     * kuulumisen tarkistamiseen.
     */
    public Matcher createMatcher(String expression, boolean useDFA, boolean printNFA, boolean printDFA) {
        if (expression.isEmpty()) {
            return new Matcher(null, false);
        }
        Matcher matcher = new Matcher(new NFAfactory().generateNFA(expression), false);
        if (printNFA && matcher.getWorks()) {
            System.out.println("NFA");
            matcher.printAutomate();
        }
        if (!matcher.getWorks()) {
            return new Matcher(null, false);
        }
        if (useDFA) {
            matcher = new Matcher(new DFAfactory().generateDFA(matcher.getAutomate()), true);
            if (printDFA && matcher.getWorks()) {
                System.out.println("DFA");
                matcher.printAutomate();
            }
        }
        return matcher;
    }
}
