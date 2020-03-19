
import static org.junit.Assert.*;
import org.junit.Test;
import regexmatcher.Node;

public class NodeTest {

    @Test
    public void canConstructAsEnd() {
        Node node = new Node(true);
        assertTrue(node.isEnd());
    }

    @Test
    public void canConstructAsNonEnd() {
        Node node = new Node(false);
        assertFalse(node.isEnd());
    }

    @Test
    public void canGetEdgeList() {
        Node node = new Node(true);
        assertTrue(node.getEdgeList() != null);
    }
}
