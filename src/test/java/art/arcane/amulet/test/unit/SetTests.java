package art.arcane.amulet.test.unit;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SetTests
{
    @Test
    public void testOperators() {
        Set<Integer> v = Set.of(1,2,3);

        assertEquals(v + Set.of(9, 10, 11), v.copy().add(9, 10, 11));
        assertEquals(v - Set.of(1,2), v.copy().remove(1,2));
    }

    @Test
    public void testMethods() {
        Set<Integer> v = Set.of(1,2,3);

        assertEquals(v.removeWhere(i -> i <= 2), v.copy().remove(1, 2));
        assertEquals(v.keepWhere(i -> i >= 2), v.copy().remove(3, 2));
        assertEquals(v.toString(","), "1,2,3");
        assertEquals(v.copy().pop(), 1);
    }
}
