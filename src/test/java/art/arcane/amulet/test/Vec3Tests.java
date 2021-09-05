package art.arcane.amulet.test;

import art.arcane.amulet.geometry.Vec3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec3Tests {
    @Test
    public void testVectors()
    {
        Vec3 v = new Vec3();
        assertEquals(v, new Vec3(0,0,0));
        v[0] = 1;
        assertEquals(v[0], 1);
    }
}
