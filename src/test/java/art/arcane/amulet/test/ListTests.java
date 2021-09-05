package art.arcane.amulet.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ListTests {
    @Test
    public void testCreation()
    {
        assertEquals(List.from("hello", "world").size(), 2);
        assertEquals(List.from("hello", "world").getClass(), ArrayList.class);
        assertEquals(List.from(CopyOnWriteArrayList::new, "hello", "world").getClass(), CopyOnWriteArrayList.class);
    }

    @Test
    public void testCopy()
    {
        List<String> s = List.from("a", "b", "c");
        List<String> c = s.copy().removeLast();
        assertEquals(s.size(), 3);
        assertEquals(c.size(), 2);
    }

    @Test
    public void testNumericOperators()
    {
        assertEquals((List.from("a", "b") + List.from("c", "d")).toString(","), "a,b,c,d");
    }
}
