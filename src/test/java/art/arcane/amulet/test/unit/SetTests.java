package art.arcane.amulet.test.unit;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SetTests
{
    @Test
    public void testOperators() {
        Set<Integer> v = Set.from(1,2,3);

        assertEquals(v + Set.from(9, 10, 11), v.copy().add(9, 10, 11));
        assertEquals(v - Set.from(1,2), v.copy().remove(1,2));
    }

    @Test
    public void testMethods() {
        Set<Integer> v = Set.from(1,2,3);

        assertEquals(v.copy().removeWhere(i -> i <= 2), v.copy().remove(1, 2));
        assertEquals(v.copy().keepWhere(i -> i >= 2), v.copy().remove(1, 1));
        assertEquals(v.toString(","), "1,2,3");
        assertEquals(v.copy().pop(), 1);
    }
}
