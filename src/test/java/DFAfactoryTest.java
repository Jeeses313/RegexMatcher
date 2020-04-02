
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.DFAfactory;
import regexmatcher.List;
import regexmatcher.NFAfactory;
import regexmatcher.Node;

public class DFAfactoryTest {

    @Test
    public void canGenerateDFAFromNFA() {
        List<Node> automate = new DFAfactory().generateDFA(new NFAfactory().generateNFA("aa"));
        assertTrue(automate != null);
    }

    @Test
    public void returnsNullWhenGivenNFAIsInvalid() {
        List<Node> automate = new DFAfactory().generateDFA(new NFAfactory().generateNFA("[a-"));
        assertTrue(automate == null);
    }
}
