package art.arcane.amulet.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ListTests
{
    @Test
    public void testOperators() {
        List<Integer> v = List.from(1,2,3);

        assertEquals(-v, v.copy().reverse());
        assertEquals(v + List.from(9, 10, 11), v.copy().add(9, 10, 11));
        assertEquals(v - List.from(1,2), v.copy().remove(1,2));
        assertEquals(v[1], 2);
    }

    @Test
    public void testMethods() {
        List<Integer> v = List.from(1,2,3);

        assertEquals(List.from(3), v.copy().remove(1, 2));
        assertEquals(v.copy().removeWhere(i -> i <= 2), v.copy().remove(1, 2));
        assertEquals(v.copy().keepWhere(i -> i > 2), v.copy().remove(1, 2));
        assertEquals(v.toString(","), "1,2,3");
        assertEquals(v.copy().pop(), 1);
        assertEquals(v.copy().popLast(), 3);
        assertEquals(v.last(), 2);
    }
}
