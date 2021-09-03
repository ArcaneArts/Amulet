package art.arcane.amulet.test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListExtensions {
    public void testCopy()
    {
        List<String> s = new ArrayList<>();
        s.add("a");
        s.add("b");
        s.add("c");
    }
}
