/*
 * Amulet is an extension api for Java
 * Copyright (c) 2022 Arcane Arts
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

import art.arcane.amulet.MagicalSugar;
import art.arcane.amulet.geometry.Vec;
import art.arcane.amulet.range.IntegerRange;
import manifold.ext.rt.RuntimeMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static art.arcane.amulet.MagicalSugar.*;
import static org.junit.jupiter.api.Assertions.*;

public class SugarTests {
    @Test
    public void testTime() {
        assertEquals(TimeUnit.SECONDS.toMillis(30), 30 seconds);
        assertEquals(TimeUnit.HOURS.toMillis(1) * 4.5D, 4.5hours);
        assertEquals(TimeUnit.SECONDS.toMillis(30) + TimeUnit.MINUTES.toMillis(25), 30seconds + (25minutes));
    }

    @Test
    public void testParsing() {
        assertEquals(5.774, "5.774".toDouble());
        assertEquals(-1, "xxx".toInt(-1));
        assertEquals(7L, "7".toLong());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testNullSafe() {
        String aa = null;
        assertEquals("a", "a" or "b");
        assertEquals("b", aa ifnull "b");
    }

    @Test
    public void testLerping() {
        assertEquals(5, 0 to 10 lerp 0.5);
        assertEquals(7.5D, 0.0 to 10D lerp 0.75);
    }

    @Test
    public void testMath() {
        assertEquals(40, 40 max 15);
        assertEquals(48, 48 min 64);
        assertEquals(70, 50 min 60 max 70);
        assertEquals(70, 76 min 60 max 70);
        assertEquals(5 * 5, 5 sq);
        assertEquals(50, 5 sq + 5 sq);
        assertEquals(Math.sqrt(5 * 5), 5 sq sqrt);
        assertEquals(Math.sqrt(5 * 5 + (5 * 5)), (5 sq + 5 sq) sqrt);
    }

    @Test
    public void testNumbers() {
        assertEquals(40_000, 40K);
        assertEquals(42_000_000, 42M);
        assertEquals(1_500_000_000D, 1.5B);
        assertEquals(1_000 * 1_000_000, 1K M);
    }

    @Test
    public void testBytes() {
        assertEquals(5767168D, 5.5MB);
        assertEquals(21474836480L, 36GB - 16GB);
        assertEquals(1099511627776L * 1.5D, 1.5TB);
    }

    @Test
    public void testRanges() {
        assertEquals(new IntegerRange(3, 5), 3 to 5);
        assertEquals(5 to 3, -(3 to 5));
        assertEquals(5 to 3, reverse 3 to 5);
    }

    @Test
    public void testClip() {
        assertEquals(45, 45 clip 3 to 50);
        assertEquals(25, 42 clip 4 to 25);
        assertEquals(7, 2 clip 7 to 50);
    }

    @Test
    public void testDegreesRadians() {
        assertEquals(90 deg, Math.toRadians(90));
        assertEquals(1 rad, Math.toDegrees(1));
        assertEquals(1337 deg rad, 1337);
        assertEquals(1337 rad deg, 1337);
    }

    @Test
    public void testStrings() {
        assertEquals("hello".upper(), "hello" uc);
        assertEquals("aaa", "aaa" uc lc);
        assertEquals("hell", "hello" without "o");
        assertEquals(List.of("a", "b", "c"), "a,b,c" split ",");
    }

    @Test
    public void testHashing() {
        assertArrayEquals("hello".md5(), "hello" md5);
        assertArrayEquals("hello".sha1(), "hello" sha1);
        assertArrayEquals("hello".sha256(), "hello" sha256);
        assertArrayEquals("hello".sha384(), "hello" sha384);
        assertArrayEquals("hello".sha512(), "hello" sha512);
        assertEquals("hello".md5().hex(), "hello" md5 hex);
        assertEquals("hello".sha1().hex(), "hello" sha1 hex);
        assertEquals("hello".sha256().hex(), "hello" sha256 hex);
        assertEquals("hello".sha384().hex(), "hello" sha384 hex);
        assertEquals("hello".sha512().hex(), "hello" sha512 hex);
    }

    @Test
    public void testBase64() {
        String mass = Random.r().s(512);
        assertEquals(mass.toBase64().fromBase64String(), mass);
    }

    @Test
    public void testAtomics() {
        assertEquals("AAA", (atomic "aaa").get() uc);
        assertEquals(8, (aint 7).incrementAndGet());
        assertEquals(11.32, (adouble 7.32).addAndGet(4));
        assertEquals(2L, (along 3).decrementAndGet());
    }

    @Test
    public void testVectors() {
        Vec a = Vec.of(0);
        Vec b = Vec.of(33, 21, 66);
        Vec x = Vec.of(1, 3, 8).normalize();
        assertEquals(a.copy().distance(b), a distance b);
        assertEquals(a.copy().distanceSquared(b), a distanceSq b);
        assertEquals(a.copy().dot(b), a dot b);
        assertEquals(a.copy().crossProduct(b), a cross b);
        assertEquals(a.copy().rotateAroundX(42 deg), a rotateX 42 deg);
        assertEquals(a.copy().rotateAroundY(-2342 deg), a rotateY (-2342) deg);
        assertEquals(a.copy().rotateAroundZ(424 deg), a rotateZ 424 deg);
        assertEquals(a.copy().rotateAroundAxis(x, 38 deg), a rotate x angle 38 deg);
    }
}
