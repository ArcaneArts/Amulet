package art.arcane.amulet.test;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTests {
    @Test
    public void testLists()
    {
        Map<String, Integer> map = new HashMap<>();
        map.qput("a", 1).qput("b", 2).qput("c", 3);

        Map<Integer, String> flipped = -map;
        flipped.get(3); // "c"

        List<Integer> ints = flipped.sortK(Comparator.comparing(Object::toString)).reverse();
        ints.toString("|"); // "3|2|1"
        ints += List.from(5, 6, 7);
        ints.forEach((i) -> flipped.put(i, "..."));

        int g = ints[0]; // 3
    }
}
