package domain;


import static org.junit.Assert.*;
import org.junit.Test;
import regexmatcher.domain.Node;

public class NodeTest {

    @Test
    public void newNodeIsNotEnd() {
        Node node = new Node();
        assertFalse(node.isEnd());
    }

    @Test
    public void canSetEnd() {
        Node node = new Node();
        node.setEnd();
        assertTrue(node.isEnd());
    }

    @Test
    public void canGetEdgeList() {
        Node node = new Node();
        assertTrue(node.getEdgeList() != null);
    }
    
    @Test
    public void canSetEdgeList() {
        Node node = new Node();
        node.setEdgeList(null);
        assertTrue(node.getEdgeList() == null);
    }
}
