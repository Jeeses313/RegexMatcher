package domain;


import static org.junit.Assert.*;
import org.junit.Test;
import regexmatcher.domain.List;

public class ListTest {

    @Test
    public void newListSizeIsZero() {
        List<Integer> list = new List<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void addGrowsListSizeCorrectlyAndMakesItNonEmpty() {
        List<Integer> list = new List<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertEquals(100, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    public void canGetIndex() {
        List<Integer> list = new List<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));
    }

    @Test
    public void canCheckIfListContains() {
        List<Integer> list = new List<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertFalse(list.contains(4));
        assertTrue(list.contains(2));
    }

    @Test
    public void canGetIndexOfObjectWhenItIsOnList() {
        List<Integer> list = new List<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(1, list.indexOf(2));
        assertEquals(2, list.indexOf(3));
    }

    @Test
    public void indexOfReturnsMinusOneWhenObjectIsNotOnList() {
        List<Integer> list = new List<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(-1, list.indexOf(4));
    }
    
    @Test
    public void getOutsideOfArrayThrowsError() {
        List<Integer> list = new List<>();
        try {
            list.get(0);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void getNegativeThrowsError() {
        List<Integer> list = new List<>();
        list.add(1);
        try {
            list.get(-1);
            assertTrue(false);
        } catch(Exception e) {
            assertTrue(true);
        }
    }
}
