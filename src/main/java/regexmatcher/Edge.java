package regexmatcher;

/**
 * Luokka, joka toimii verkon kaarena.
 */
public class Edge {

    private int goalNode;
    private char character;

    /**
     * Luokan konstruktori, joka saa parametreina maalisolmun viitteen ja kaaren
     * kirjaimen.
     *
     * @param goalNode int, joka viittaa maalisolmuun.
     * @param character char, joka on kaaren kirjain.
     */
    public Edge(int goalNode, char character) {
        this.goalNode = goalNode;
        this.character = character;
    }

    /**
     * Palauttaa kaaren kirjaimen.
     *
     * @return char, joka on kaaren kirjain.
     */
    public char getCaharacter() {
        return character;
    }

    /**
     * Palauttaa kaaren maalisolmun viitteen.
     *
     * @return int, joka viittaa maalisolmuun.
     */
    public int getGoalNode() {
        return goalNode;
    }
}
