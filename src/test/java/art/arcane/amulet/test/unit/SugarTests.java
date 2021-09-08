package art.arcane.amulet.test.unit;

import static art.arcane.amulet.MagicalSugar.*;
import static org.junit.jupiter.api.Assertions.*;

import art.arcane.amulet.geometry.Vec;
import art.arcane.amulet.range.IntegerRange;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SugarTests {
    @Test
    public void testTime()
    {
        assertEquals(30 seconds, TimeUnit.SECONDS.toMillis(30));
        assertEquals(4.5 hours, TimeUnit.HOURS.toMillis(1) * 4.5D);
        assertEquals(30 seconds + 25 minutes, TimeUnit.SECONDS.toMillis(TimeUnit.SECONDS.toMillis(30) + TimeUnit.MINUTES.toMillis(25)));
    }

    @Test
    public void testMath()
    {
        assertEquals(40 max 15, 15);
        assertEquals(48 min 64, 64);
        assertEquals(50 min 60 max 70, 60);
        assertEquals(76 min 60 max 70, 70);
    }

    @Test
    public void testRanges()
    {
        assertEquals(3 to 5, new IntegerRange(3, 5));
    }

    @Test
    public void testClip()
    {
        assertEquals(45 clip 3 to 50, 45);
        assertEquals(42 clip 4 to 25, 25);
        assertEquals(2 clip 7 to 50, 7);
    }

    @Test
    public void testDegreesRadians()
    {
        assertEquals(90 deg, Math.toRadians(90));
        assertEquals(1 rad, Math.toDegrees(1));
        assertEquals(1337, 1337 deg rad);
        assertEquals(1337, 1337 rad deg);
    }

    @Test
    public void testStrings()
    {
        assertEquals("hello"uc, "hello".upper());
        assertEquals("aaa"uc lc, "aaa");
        assertEquals("hello" without "o", "hell");
        assertEquals("a,b,c" split ",", List.of("a", "b", "c"));
    }

    @Test
    public void testVectors()
    {
        Vec a = Vec.of(0);
        Vec b = Vec.of(33, 21, 66);
        Vec x = Vec.of(1, 3, 8).normalize();

        assertEquals(a distance b, a.copy().distance(b));
        assertEquals(a distanceSq b, a.copy().distanceSquared(b));
        assertEquals(a dot b, a.copy().dot(b));
        assertEquals(a cross b, a.copy().crossProduct(b));
        assertEquals(a rotateX 42 deg, a.copy().rotateAroundX(42 deg));
        assertEquals(a rotateY (-2342) deg, a.copy().rotateAroundY(-2342 deg));
        assertEquals(a rotateZ 424 deg, a.copy().rotateAroundZ(424 deg));
        assertEquals(a rotate x angle 38 deg, a.copy().rotateAroundAxis(x, 38 deg));
    }
}
