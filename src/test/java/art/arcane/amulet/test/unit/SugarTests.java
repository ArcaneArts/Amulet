/*
 * Amulet is an extension api for Java
 * Copyright (c) 2021 Arcane Arts
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package art.arcane.amulet.test.unit;

import art.arcane.amulet.Amulet;
import art.arcane.amulet.geometry.Vec;
import art.arcane.amulet.range.IntegerRange;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static art.arcane.amulet.MagicalSugar.*;
import static org.junit.jupiter.api.Assertions.*;

public class SugarTests {
    @Test
    public void testTime() {
        assertEquals(30seconds, TimeUnit.SECONDS.toMillis(30));
        assertEquals(4.5hours, TimeUnit.HOURS.toMillis(1) * 4.5D);
        assertEquals(30seconds + (25minutes), TimeUnit.SECONDS.toMillis(30) + TimeUnit.MINUTES.toMillis(25));
    }

    @Test
    public void testMath() {
        assertEquals(40max 15, 40);
        assertEquals(48min 64, 48);
        assertEquals(50min 60max 70, 70);
        assertEquals(76min 60max 70, 70);
        assertEquals(5 * 5, 5sq);
        assertEquals(50, 5sq + 5sq);
        assertEquals(Math.sqrt(5 * 5), 5sq sqrt);
        assertEquals(Math.sqrt(5 * 5 + (5 * 5)), (5sq + 5sq)sqrt);
    }

    @Test
    public void testRanges() {
        assertEquals(3to 5, new IntegerRange(3, 5));
        assertEquals(-(3to 5), 5to 3);
        assertEquals(reverse 3to 5, 5to 3);
    }

    @Test
    public void testClip() {
        assertEquals(45clip 3to 50, 45);
        assertEquals(42clip 4to 25, 25);
        assertEquals(2clip 7to 50, 7);
    }

    @Test
    public void testDegreesRadians() {
        assertEquals(90 deg, Math.toRadians(90));
        assertEquals(1rad, Math.toDegrees(1));
        assertEquals(1337, 1337 deg rad);
        assertEquals(1337, 1337rad deg);
    }

    @Test
    public void testStrings() {
        assertEquals("hello"uc, "hello".upper());
        assertEquals("aaa"uc lc, "aaa");
        assertEquals("hello"without"o", "hell");
        assertEquals("a,b,c"split",", List.of("a", "b", "c"));
    }

    @Test
    public void testVectors() {
        Vec a = Vec.of(0);
        Vec b = Vec.of(33, 21, 66);
        Vec x = Vec.of(1, 3, 8).normalize();

        assertEquals(a distance b, a.copy().distance(b));
        assertEquals(a distanceSq b, a.copy().distanceSquared(b));
        assertEquals(a dot b, a.copy().dot(b));
        assertEquals(a cross b, a.copy().crossProduct(b));
        assertEquals(a rotateX 42 deg, a.copy().rotateAroundX(42 deg));
        assertEquals(a rotateY(-2342)deg, a.copy().rotateAroundY(-2342 deg));
        assertEquals(a rotateZ 424 deg, a.copy().rotateAroundZ(424 deg));
        assertEquals(a rotate x angle 38 deg, a.copy().rotateAroundAxis(x, 38 deg));
    }
}
