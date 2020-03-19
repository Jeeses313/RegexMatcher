
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.NFAfactory;
import regexmatcher.Node;

public class NFAfactoryTest {

    @Test
    public void generateNFAReturnsNullWhnRegularExpressionIsInvalid() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("[a-");
        assertTrue(automate == null);
        automate = new NFAfactory().generateNFA("]a");
        assertTrue(automate == null);
        automate = new NFAfactory().generateNFA("[a-9]");
        assertTrue(automate == null);
        automate = new NFAfactory().generateNFA("[.a-b]");
        assertTrue(automate == null);
        automate = new NFAfactory().generateNFA("*a");
        assertTrue(automate == null);
        automate = new NFAfactory().generateNFA("(*a)");
        assertTrue(automate == null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValid() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("a");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasStar() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("a*");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasPlus() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("a+");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasSquareBrackets() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("[a1-9]");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasStarAndSquareBrackets() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("[a1-9]*");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasPlusAndSquareBrackets() {
        ArrayList<Node> automate = new NFAfactory().generateNFA("[a1-9]+");
        assertTrue(automate != null);
    }
}
