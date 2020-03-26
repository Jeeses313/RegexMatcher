
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.DFAnode;

public class DFAnodeTest {

    @Test
    public void newDFAnodeIsNotEnd() {
        DFAnode node = new DFAnode();
        assertFalse(node.isEnd());
    }

    @Test
    public void canSetEnd() {
        DFAnode node = new DFAnode();
        node.setEnd();
        assertTrue(node.isEnd());
    }

    @Test
    public void canGetEdgeList() {
        DFAnode node = new DFAnode();
        assertTrue(node.getEdgeList() != null);
    }

    @Test
    public void canGetNodeList() {
        DFAnode node = new DFAnode();
        assertTrue(node.getNodeList() != null);
    }

    @Test
    public void equalsReturnsTrueWhenNodeListsHaveSameContent() {
        DFAnode node1 = new DFAnode();
        DFAnode node2 = new DFAnode();
        node1.getNodeList().add(1);
        node1.getNodeList().add(2);
        node2.getNodeList().add(1);
        node2.getNodeList().add(2);
        assertTrue(node1.equals(node2));
    }

    @Test
    public void equalsReturnsFalseWhenNodeListsHaveDifferentContent() {
        DFAnode node1 = new DFAnode();
        DFAnode node2 = new DFAnode();
        node1.getNodeList().add(1);
        node1.getNodeList().add(2);
        node2.getNodeList().add(1);
        node2.getNodeList().add(3);
        assertFalse(node1.equals(node2));
    }
}
