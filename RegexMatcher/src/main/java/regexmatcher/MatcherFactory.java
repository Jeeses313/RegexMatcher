package regexmatcher;

import java.util.ArrayList;

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
     * @return Matcher, jota käyetään säännöllisen lausekkeen kieleen kuulumisen
     * tarkistamiseen.
     */
    public Matcher createMatcher(String expression) {
        if (expression.isEmpty()) {
            return new Matcher(null);
        }
        ArrayList<Node> automate = new NFAfactory().generateNFA(expression);
        if (automate == null) {
            return new Matcher(null);
        }
        //generateDFA?
        return new Matcher(automate);
    }
}
