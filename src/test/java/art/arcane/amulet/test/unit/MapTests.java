package art.arcane.amulet.test.unit;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapTests
{
    @Test
    public void testOperators() {
        Map<String, Integer> v = Map.of("a", 1, "b", 2, "c", 3);

        assertEquals(-v, Map.of(1, "a", 2, "b", 3, "c"));
        assertEquals(v + Map.of("d", 4), Map.of("a", 1, "b", 2, "c", 3, "d", 4));
        assertEquals(v - Map.of("b", 2), Map.of("a", 1, "c", 3));
        assertEquals(v - List.of("b"), Map.of("a", 1, "c", 3));
        assertEquals(v - "b", Map.of("a", 1, "c", 3));
    }
}
