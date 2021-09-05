package art.arcane.amulet.test;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTests {
    @Test
    public void testUsages()
    {
        assertEquals("hello world".capitalizeWords(), "Hello World");
        assertEquals("X".fromRoman(), 10);
    }
}
