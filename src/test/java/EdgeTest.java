
import static org.junit.Assert.*;
import org.junit.Test;
import regexmatcher.Edge;

public class EdgeTest {
    
    @Test
    public void canConstruct() {
        Edge edge = new Edge(0, 'a');
        assertTrue(edge.getCaharacter() == 'a');
        assertTrue(edge.getGoalNode() == 0);
    }
}
