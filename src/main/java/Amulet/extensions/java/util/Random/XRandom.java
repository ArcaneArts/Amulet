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

package Amulet.extensions.java.util.Random;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.List;
import java.util.Random;

@Extension
public class XRandom {
    private static final char[] CHARGEN = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-=!@#$%^&*()_+`~[];',./<>?:\\\"{}|\\\\".toCharArray();
    public static final Random r = new Random();

    public static String s(@This Random self, int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(self.c());
        }

        return sb.toString();
    }

    @Extension
    public static Random r() {
        return r;
    }

    public static char c(@This Random self) {
        return CHARGEN[self.i(CHARGEN.length - 1)];
    }

    /**
     * Pick a random enum
     *
     * @param t the enum class
     * @return the enum
     */
    public static <T> T e(@This Random self, Class<T> t) {
        T[] c = t.getEnumConstants();
        return c[self.i(c.length)];
    }

    public static boolean b(@This Random self) {
        return self.nextBoolean();
    }

    public static boolean b(@This Random self, double percent) {
        return self.d() > percent;
    }

    public static short si(@This Random self, int lowerBound, int upperBound) {
        return (short) (lowerBound + (self.nextFloat() * ((upperBound - lowerBound) + 1)));
    }

    public static short si(@This Random self, int upperBound) {
        return self.si(0, upperBound);
    }

    public static short si(@This Random self) {
        return self.si(1);
    }

    public static float f(@This Random self, float lowerBound, float upperBound) {
        return lowerBound + (self.nextFloat() * ((upperBound - lowerBound)));
    }

    public static float f(@This Random self, float upperBound) {
        return self.f(0, upperBound);
    }

    public static float f(@This Random self) {
        return self.f(1);
    }

    public static double d(@This Random self, double lowerBound, double upperBound) {
        if (lowerBound > upperBound) {
            return Math.lerp(upperBound, lowerBound, self.nextDouble());
        }

        return Math.lerp(lowerBound, upperBound, self.nextDouble());
    }

    public static double d(@This Random self, double upperBound) {
        return self.d(0, upperBound);
    }

    public static double d(@This Random self) {
        return self.d(1);
    }

    public static int i(@This Random self, int lowerBound, int upperBound) {
        return (int) Math.floor(self.d(lowerBound, upperBound));
    }

    public static int i(@This Random self, int upperBound) {
        return self.i(Math.min(upperBound, 0), Math.max(0, upperBound));
    }

    public static long l(@This Random self, long lowerBound, long upperBound) {
        return Math.round(self.d(lowerBound, upperBound));
    }

    public static long l(@This Random self, long upperBound) {
        return self.l(0, upperBound);
    }

    public static int imax(@This Random self) {
        return self.i(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static long lmax(@This Random self) {
        return self.l(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static float fmax(@This Random self) {
        return self.f(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public static double dmax(@This Random self) {
        return self.d(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static short simax(@This Random self) {
        return self.si(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public static boolean chance(@This Random self, double chance) {
        return chance >= self.nextDouble();
    }

    public static <T> T pick(@This Random self, List<T> pieces) {
        if (pieces.isEmpty()) {
            return null;
        }

        if (pieces.size() == 1) {
            return pieces.get(0);
        }

        return pieces.get(self.nextInt(pieces.size()));
    }
}
