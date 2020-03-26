
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.List;
import regexmatcher.NFAfactory;
import regexmatcher.Node;

public class NFAfactoryTest {

    @Test
    public void generateNFAReturnsNullWhenRegularExpressionIsInvalid() {
        List<Node> automate = new NFAfactory().generateNFA("[a-");
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
        List<Node> automate = new NFAfactory().generateNFA("a");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasStar() {
        List<Node> automate = new NFAfactory().generateNFA("a*");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasPlus() {
        List<Node> automate = new NFAfactory().generateNFA("a+");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasSquareBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("[a1-9]");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasStarAndSquareBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("[a1-9]*");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasPlusAndSquareBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("[a1-9]+");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasStarAndBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("(abc)*");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasPlusAndBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("(abc)+");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasOr() {
        List<Node> automate = new NFAfactory().generateNFA("a|b");
        assertTrue(automate != null);
    }

    @Test
    public void generateNFAReturnsAutomateWhenRegularExpressionIsValidAndHasOrAndBrackets() {
        List<Node> automate = new NFAfactory().generateNFA("(abc|123)");
        assertTrue(automate != null);
    }
}
