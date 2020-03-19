package regexmatcher;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Luokka, jonka avulla muodostetaan NFA.
 */
public class NFAfactory {

    private Stack<Integer> stack;
    private ArrayList<Node> automate;
    private int previousState;
    private int currentState;
    private int index;

    /**
     * Muodostaa automaatin annetusta säännöllisestä lausekkeesta käyden läpi
     * sen merkkejä. Palauttaa null, jos muodostus ei ole mahdollista.
     *
     * @param expression String, josta automaatti muodostetaan.
     * @return ArrayList, eli muodostettu NFA tai null, jos muodostus ei ole
     * mahdollista.
     */
    public ArrayList<Node> generateNFA(String expression) {
        initData();
        while (true) {
            char character = expression.charAt(index);
            boolean works = handleCharacter(character, expression);
            if (!works) {
                return null;
            }
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        automate.get(currentState).setEnd();
        return automate;
    }

    /**
     * Tutkii yhtä merkkiä ja kutsuu sen käsittelyyn tarvittavaa metodia.
     *
     * @param character char, jota tutkitaan.
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo onnistuiko merkin käsittely.
     */
    private boolean handleCharacter(char character, String expression) {
        boolean works = true;
        switch (character) {
            case '(':
                break;
            case ')':
                break;
            case '[':
                works = handleSquareBrackets(expression);
                break;
            case ']':
                return false;
            case '*':
                works = handleStar(expression);
                break;
            case '+':
                works = handlePlus(expression);
                break;
            case '|':
                break;
            case '-':
                return false;
            default:
                works = handleNormalCharacter(character);
                break;
        }
        return works;
    }

    /**
     * Käsittelee säännöllisen lausekkeen hakasulkeet.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo onnistuiko käsittely.
     */
    private boolean handleSquareBrackets(String expression) {
        index++;
        automate.add(new Node(false));
        while (true) {
            if (index >= expression.length()) {
                return false;
            }
            int startChar = expression.charAt(index);
            if (startChar == ']') {
                break;
            }
            if (index >= expression.length() - 1 || !isStandardChar((char) startChar)) {
                return false;
            }
            if (expression.charAt(index + 1) == '-') {
                if (index >= expression.length() - 2) {
                    return false;
                }
                int endChar = expression.charAt(index + 2);
                if (endChar < startChar || (startChar >= 48 && startChar <= 57 && endChar > 57) || (startChar >= 65 && startChar <= 90 && endChar > 90) || (startChar >= 97 && startChar <= 122 && endChar > 122)) {
                    return false;
                }
                for (int k = startChar; k <= endChar; k++) {
                    automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) k));
                }
                index += 3;
            } else {
                automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) startChar));
                index++;
            }
        }
        previousState = currentState;
        currentState = automate.size() - 1;
        return true;
    }

    /**
     * Alustaa automaatin tekoon käytettävän datan.
     */
    private void initData() {
        automate = new ArrayList<>();
        stack = new Stack<>();
        automate.add(new Node(false));
        stack.push(0);
        previousState = 0;
        currentState = 0;
        index = 0;
    }

    /**
     * Käsittelee säännöllisen lausekkeen muut merkit. Hyväksyy a-z, 0-9, A-Z ja
     * '.'.
     *
     * @param character char, jota käsitellään.
     * @return Totuusarvo, joka kertoo onnistuiko käsittely.
     */
    private boolean handleNormalCharacter(char character) {
        if (!isStandardChar(character) && character != 46) {
            return false;
        }
        automate.add(new Node(false));
        automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, character));
        previousState = currentState;
        currentState = automate.size() - 1;
        return true;
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

    /**
     * Käsittelee säännöllisen lausekkeen tähdet.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo onnistuiko käsittely.
     */
    private boolean handleStar(String expression) {
        if (index == 0) {
            return false;
        }
        char charBefore = expression.charAt(index - 1);
        if (isStandardChar(charBefore) || charBefore == ']') {
            automate.get(currentState).getEdgeList().add(new Edge(previousState, (char) 0));
            if (index == expression.length() - 1) {
                automate.get(currentState).setEnd();
            }
            currentState = previousState;
            return true;
        } else if (charBefore == ')') {
            return true;
        }
        return false;
    }

    /**
     * Käsittelee säännöllisen lausekkeen plussat.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo onnistuiko käsittely.
     */
    private boolean handlePlus(String expression) {
        if (index == 0) {
            return false;
        }
        char charBefore = expression.charAt(index - 1);
        if (isStandardChar(charBefore) || charBefore == ']') {
            automate.get(currentState).getEdgeList().add(new Edge(previousState, (char) 0));
            if (index == expression.length() - 1) {
                automate.get(currentState).setEnd();
            }
            currentState = previousState;
            return true;
        } else if (charBefore == ')') {
            return true;
        }
        return false;
    }
}
