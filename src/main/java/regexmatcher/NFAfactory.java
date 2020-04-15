package regexmatcher;

/**
 * Luokka, jonka avulla muodostetaan NFA.
 */
public class NFAfactory {

    /**
     * Sulkujen alkukohtien muistissa pitämiseen tarkoitettu pino.
     */
    private Stack<Integer> stack;
    /**
     * Tai, eli |, lohkon viimeisten tilojen muistissa pitämiseen tarkoitettu
     * pino, joka sisältää listoja.
     */
    private Stack<List<Integer>> memory;
    private List<Node> automate;
    private int previousState;
    private int currentState;
    private int index;

    /**
     * Muodostaa automaatin annetusta säännöllisestä lausekkeesta käyden läpi
     * sen merkkejä. Palauttaa null, jos muodostus ei ole mahdollista.
     *
     * @param expression String, josta automaatti muodostetaan.
     * @return List, eli muodostettu NFA tai null, jos muodostus ei ole
     * mahdollista.
     */
    public List<Node> generateNFA(String expression) {
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
        if (!setEndStates()) {
            return null;
        }
        return automate;
    }

    /**
     * Tutkii yhtä merkkiä ja kutsuu sen käsittelyyn tarvittavaa metodia.
     *
     * @param character char, jota tutkitaan.
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko merkin käsittely.
     */
    private boolean handleCharacter(char character, String expression) {
        boolean works;
        switch (character) {
            case '(':
                works = handleBracketStart();
                break;
            case ')':
                works = handleBracketEnd(expression);
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
            case '?':
                works = handleQuestionmark(expression);
                break;
            case '|':
                works = handleOr();
                break;
            case '-':
                return false;
            case '.':
                works = handleDot();
                break;
            default:
                works = handleNormalCharacter(character);
                break;
        }
        return works;
    }

    /**
     * Käsittelee säännöllisen lausekkeen hakasulkeet.
     *
     * Muodostaa uuden tilan ja tekee siirtymän nykyisestä tilasta uuteen
     * kaikilla hakasulkeiden sisällä olevilla ja tavuviivan osoittamilla
     * merkeillä.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleSquareBrackets(String expression) {
        index++;
        automate.add(new Node());
        boolean[] contained = new boolean[123];
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
                boolean works = handleHyphen(expression, startChar, contained);
                if (!works) {
                    return false;
                }
            } else if (!contained[startChar]) {
                automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) startChar));
                contained[startChar] = true;
            }
            index++;
        }
        previousState = currentState;
        currentState = automate.size() - 1;
        return true;
    }

    /**
     * Käsittelee säännöllisen lausekkeen tavuviivan hakasulkeiden sisällä.
     *
     * Muodostaa siirtymän nykyisestä tilastaa uuteen tilaan kaikilla merkeillä,
     * jotka ovat tavuviivan edeltävän ja jäljessä olevan merkin välillä.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @param startChar int, joka on merkkijoukon aloittavan merkin numero.
     * @param contained boolean taulukko, joka kertoo, onko merkki esiintynyt jo
     * hakasulkeiden sisällä.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleHyphen(String expression, int startChar, boolean[] contained) {
        if (index >= expression.length() - 2) {
            return false;
        }
        int endChar = expression.charAt(index + 2);
        if (endChar < startChar || (startChar >= 48 && startChar <= 57 && endChar > 57) || (startChar >= 65 && startChar <= 90 && endChar > 90) || (startChar >= 97 && startChar <= 122 && endChar > 122)) {
            return false;
        }
        for (int k = startChar; k <= endChar; k++) {
            if (!contained[k]) {
                contained[k] = true;
                automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) k));
            }
        }
        index += 2;
        return true;
    }

    /**
     * Alustaa automaatin tekoon käytettävän datan.
     *
     * Muodostaa kaksi tilaa, joista ensimmäinen on aloitustila, josta on tyhjän
     * merkin siirtymä toiseen tilaan. Ensimmäinen tila lisätään myös sulkujen
     * alkukohtia muistissa pitävään pinoon.
     */
    private void initData() {
        automate = new List<>();
        stack = new Stack<>();
        memory = new Stack<>();
        automate.add(new Node());
        stack.push(0);
        memory.push(new List<>());
        automate.add(new Node());
        automate.get(0).getEdgeList().add(new Edge(1, (char) 0));
        previousState = 0;
        currentState = 1;
        index = 0;
    }

    /**
     * Käsittelee säännöllisen lausekkeen muut merkit. Hyväksyy a-z, 0-9 ja A-Z.
     *
     * Muodostaa uuden tilan ja tekee siirtymän nykyisestä tilasta uuteen
     * annetulla merkillä.
     *
     * @param character char, jota käsitellään.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleNormalCharacter(char character) {
        if (!isStandardChar(character)) {
            return false;
        }
        automate.add(new Node());
        automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, character));
        previousState = currentState;
        currentState = automate.size() - 1;
        return true;
    }

    /**
     * Käsittelee säännöllisen lausekkeen pisteen.
     *
     * Muodostaa uuden tilan ja tekee siirtymän nykyisestä tilasta uuteen
     * kaikilla hyväksytyillä merkeillä, eli a-z, 0-9 ja A-Z.
     *
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleDot() {
        automate.add(new Node());
        for (int i = 48; i <= 57; i++) {
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) i));
        }
        for (int i = 65; i <= 90; i++) {
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) i));
        }
        for (int i = 97; i <= 122; i++) {
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) i));
        }
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
     * Muodostaa uuden tilan ja tekee tyhjän merkin siirtymän nykyisestä tilasta
     * aiempaan tilaan ja aiemmasta tilasta uuteen tilaan.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleStar(String expression) {
        if (index == 0) {
            return false;
        }
        char charBefore = expression.charAt(index - 1);
        if (isStandardChar(charBefore) || charBefore == ']' || charBefore == '.') {
            automate.get(currentState).getEdgeList().add(new Edge(previousState, (char) 0));
            automate.add(new Node());
            automate.get(previousState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            currentState = automate.size() - 1;
            return true;
        }
        return false;
    }

    /**
     * Käsittelee säännöllisen lausekkeen plussat.
     *
     * Muodostaa uuden tilan ja tekee tyhjän merkin siirtymän nykyisestä tilasta
     * aiempaan tilaan ja nykyisestä tilasta uuteen tilaan.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handlePlus(String expression) {
        if (index == 0) {
            return false;
        }
        char charBefore = expression.charAt(index - 1);
        if (isStandardChar(charBefore) || charBefore == ']' || charBefore == '.') {
            automate.get(currentState).getEdgeList().add(new Edge(previousState, (char) 0));
            automate.add(new Node());
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            previousState = currentState;
            currentState = automate.size() - 1;
            return true;
        }
        return false;
    }

    /**
     * Käsittelee säännöllisen lausekkeen kysymysmerkit.
     *
     * Muodostaa uuden tilan ja tekee tyhjän merkin siirtymän nykyisestä tilasta
     * uuteen tilaan ja aiemmasta tilasta uuteen tilaan.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleQuestionmark(String expression) {
        if (index == 0) {
            return false;
        }
        char charBefore = expression.charAt(index - 1);
        if (isStandardChar(charBefore) || charBefore == ']' || charBefore == '.') {
            automate.add(new Node());
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            automate.get(previousState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            previousState = currentState;
            currentState = automate.size() - 1;
            return true;
        }
        return false;
    }

    /**
     * Asettaa automaatin hyväksyvät tilat.
     *
     * Asettaa nykyisen tilan ja kaikki tai-lohkon viimeisten tilojen muistissa
     * pitävän pinon päälimmäisen listan tilat hyväksyviksi.
     *
     * @return Totuusarvo, joka kertoo, onko automaatin käsittely loppunut
     * hyväksyttävään tilanteeseen.
     */
    private boolean setEndStates() {
        if (memory.size() > 1 || stack.size() > 1) {
            return false;
        }
        for (int i = 0; i < memory.peek().size(); i++) {
            int state = memory.peek().get(i);
            automate.get(state).setEnd();
        }
        automate.get(currentState).setEnd();
        return true;
    }

    /**
     * Käsittelee säännöllisen lausekkeen kaarisulkujen alun.
     *
     * Lisää nykyisen tilan sulkujen alkukohtia muistissa pitävään pinoon, lisää
     * uuden listan tai-lohkon viimeisten tilojen muistissa pitävään pinoon,
     * muodostaa uuden tilan ja tekee tyhjän merkin siirtymän nykyisestä tilasta
     * uuteen.
     *
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleBracketStart() {
        stack.push(currentState);
        memory.push(new List<>());
        automate.add(new Node());
        automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
        previousState = currentState;
        currentState = automate.size() - 1;
        return true;
    }

    /**
     * Käsittelee säännöllisen lausekkeen kaarisulkujen lopun.
     *
     * Poistaa päälimmäisen tilan sulkujen alkukohtia muistissa pitävästä
     * pinosta ja päälimmäisen listan tai-lohkon viimeisten tilojen muistissa
     * pitävästä pinosta. Käsittelee ensin sulkujen sisällä mahdollisesti olevat
     * tai-lohkot ja sitten sulkujen jälkeen mahdollisesti olevan tähden,
     * plussan tai kysymysmerkin.
     *
     * @param expression String, joka on säännöllinen lauseke, josta
     * muodostetaan automaattia.
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleBracketEnd(String expression) {
        if (stack.size() <= 1) {
            return false;
        }
        int stateBeforeBracket = stack.pop();
        List<Integer> bracketMemory = memory.pop();
        handleBracketEndOr(bracketMemory);
        if (index == expression.length() - 1) {
            return true;
        }
        char nextChar = expression.charAt(index + 1);
        handleBracketEndQuestionmarkStarAndPlus(stateBeforeBracket, nextChar);
        return true;
    }

    /**
     * Käsittelee säännöllisen lausekkeen kaarisulkujen lopun, kun sulkujen
     * sisällä on ollut | merkki.
     *
     * Muodostaa uuden tilan ja tekee tyhjän merkin siirtymän siihen nykyisestä
     * ja kaikista tai-lohkon viimeisten tilojen muistissa pitävästä pinon
     * päälimmäisessä listassa olevista tiloista.
     *
     * @param bracketMemory List, joka sisältää | lohkon viimeiset tilat.
     */
    private void handleBracketEndOr(List<Integer> bracketMemory) {
        if (!bracketMemory.isEmpty()) {
            automate.add(new Node());
            automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            for (int i = 0; i < bracketMemory.size(); i++) {
                int state = bracketMemory.get(i);
                automate.get(state).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
            }
            previousState = currentState;
            currentState = automate.size() - 1;
        }
    }

    /**
     * Käsittelee säännöllisen lausekkeen kaarisulkujen lopun, kun sulkujen
     * jälkeen on kysymysmerkki, plus tai tähti.
     *
     * Käsittelee merkit samoin kuin ilman sulkuja, mutta edellisen tilan sijaan
     * käytetään sulkuja ennen olevaa tilaa.
     *
     * @param stateBeforeBracket int, joka kertoo sulkuja edeltävän tilan.
     * @param nextChar char, joka on seuraavana seuraavana
     */
    private void handleBracketEndQuestionmarkStarAndPlus(int stateBeforeBracket, char nextChar) {
        if (nextChar == '*' || nextChar == '+' || nextChar == '?') {
            index++;
            automate.add(new Node());
            switch (nextChar) {
                case '*':
                    automate.get(currentState).getEdgeList().add(new Edge(stateBeforeBracket, (char) 0));
                    automate.get(stateBeforeBracket).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
                    currentState = automate.size() - 1;
                    previousState = stateBeforeBracket;
                    break;
                case '+':
                    automate.get(currentState).getEdgeList().add(new Edge(stateBeforeBracket, (char) 0));
                    automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
                    previousState = currentState;
                    currentState = automate.size() - 1;
                    break;
                case '?':
                    automate.get(currentState).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
                    automate.get(stateBeforeBracket).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
                    previousState = currentState;
                    currentState = automate.size() - 1;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Käsittelee säännöllisen lausekkeen | merkin.
     *
     * Lisää nykyisen tilan tai-lohkon viimeisten tilojen muistissa pitävän
     * pinon päälimmäiseen listaan, muodostaa uuden tilan ja tekee siihen tyhjän
     * merkin siirtymän sulkujen alkukohtien muistissa pitävän pinon
     * päälimmäisestä tilasta.
     *
     * @return Totuusarvo, joka kertoo, onnistuiko käsittely.
     */
    private boolean handleOr() {
        memory.peek().add(currentState);
        automate.add(new Node());
        automate.get(stack.peek()).getEdgeList().add(new Edge(automate.size() - 1, (char) 0));
        currentState = automate.size() - 1;
        previousState = stack.peek();
        return true;
    }

}
