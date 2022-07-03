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

package art.arcane.amulet.data;

public class CompressedNumbers {
    public static long i2(int a, int b) {
        return (((long) a) << 32) | (b & 0xffffffffL);
    }

    public static int i2a(long a) {
        return (int) (a >> 32);
    }

    public static int i2b(long b) {
        return (int) b;
    }

    public static int index3Dto1D(int x, int y, int z, int w, int h) {
        return (z * w * h) + (y * w) + x;
    }

    public static int[] index1Dto3D(int idx, int w, int h) {
        final int z = idx / (w * h);
        idx -= (z * w * h);
        final int y = idx / w;
        final int x = idx % w;
        return new int[]{x, y, z};
    }

    public static int index2Dto1D(int x, int y, int w) {
        return index3Dto1D(x, 0, y, w, 1);
    }

    public static int[] index1Dto2D(int idx, int w) {
        int[] b = new int[2];
        int[] c = index1Dto3D(idx, w, 1);
        b[0] = c[0];
        b[1] = c[2];

        return b;
    }
}
