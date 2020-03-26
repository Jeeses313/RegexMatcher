
import static org.junit.Assert.*;
import org.junit.Test;
import regexmatcher.Stack;

public class StackTest {

    @Test
    public void newStacksSizeIsZero() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void pushGrowsStackSizeCorrectlyAndMakesItNonEmpty() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        assertEquals(100, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void peekGetsTopOfStackAndDoesNotChangeSize() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, (int) stack.peek());
        assertEquals(3, stack.size());
    }

    @Test
    public void popGetsTopOfStackAndChangesSize() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, (int) stack.pop());
        assertEquals(2, stack.size());
    }
}
