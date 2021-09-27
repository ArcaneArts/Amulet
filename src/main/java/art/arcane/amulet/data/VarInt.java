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

package art.arcane.amulet.data;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VarInt {
    public static void writeVLong(OutputStream out, long value) throws IOException {
        if (value < 0) out.write((byte) 0x81);
        if (value > 0xFFFFFFFFFFFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 56) & 0x7FL)));
        if (value > 0x1FFFFFFFFFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 49) & 0x7FL)));
        if (value > 0x3FFFFFFFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 42) & 0x7FL)));
        if (value > 0x7FFFFFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 35) & 0x7FL)));
        if (value > 0xFFFFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 28) & 0x7FL)));
        if (value > 0x1FFFFFL || value < 0) out.write((byte) (0x80 | ((value >>> 21) & 0x7FL)));
        if (value > 0x3FFFL || value < 0) out.write((byte) (0x80 | ((value >>> 14) & 0x7FL)));
        if (value > 0x7FL || value < 0) out.write((byte) (0x80 | ((value >>> 7) & 0x7FL)));

        out.write((byte) (value & 0x7FL));
    }

    public static void writeVInt(OutputStream out, int value) throws IOException {
        if (value > 0x0FFFFFFF || value < 0) out.write((byte) (0x80 | ((value >>> 28))));
        if (value > 0x1FFFFF || value < 0) out.write((byte) (0x80 | ((value >>> 21) & 0x7F)));
        if (value > 0x3FFF || value < 0) out.write((byte) (0x80 | ((value >>> 14) & 0x7F)));
        if (value > 0x7F || value < 0) out.write((byte) (0x80 | ((value >>> 7) & 0x7F)));

        out.write((byte) (value & 0x7F));
    }

    public static int writeVInt(byte[] data, int pos, int value) {
        if (value > 0x0FFFFFFF || value < 0) data[pos++] = ((byte) (0x80 | ((value >>> 28))));
        if (value > 0x1FFFFF || value < 0) data[pos++] = ((byte) (0x80 | ((value >>> 21) & 0x7F)));
        if (value > 0x3FFF || value < 0) data[pos++] = ((byte) (0x80 | ((value >>> 14) & 0x7F)));
        if (value > 0x7F || value < 0) data[pos++] = ((byte) (0x80 | ((value >>> 7) & 0x7F)));

        data[pos++] = (byte) (value & 0x7F);

        return pos;
    }

    public static int readVInt(InputStream in) throws IOException {
        byte b = readByteSafely(in);

        if (b == (byte) 0x80)
            throw new RuntimeException("Attempting to read null value as int");

        int value = b & 0x7F;
        while ((b & 0x80) != 0) {
            b = readByteSafely(in);
            value <<= 7;
            value |= (b & 0x7F);
        }

        return value;
    }

    public static long readVLong(InputStream in) throws IOException {
        byte b = readByteSafely(in);

        if (b == (byte) 0x80)
            throw new RuntimeException("Attempting to read null value as long");

        long value = b & 0x7F;
        while ((b & 0x80) != 0) {
            b = readByteSafely(in);
            value <<= 7;
            value |= (b & 0x7F);
        }

        return value;
    }

    public static int sizeOfVInt(int value) {
        if (value < 0)
            return 5;
        if (value < 0x80)
            return 1;
        if (value < 0x4000)
            return 2;
        if (value < 0x200000)
            return 3;
        if (value < 0x10000000)
            return 4;
        return 5;
    }

    public static int sizeOfVLong(long value) {
        if (value < 0L)
            return 10;
        if (value < 0x80L)
            return 1;
        if (value < 0x4000L)
            return 2;
        if (value < 0x200000L)
            return 3;
        if (value < 0x10000000L)
            return 4;
        if (value < 0x800000000L)
            return 5;
        if (value < 0x40000000000L)
            return 6;
        if (value < 0x2000000000000L)
            return 7;
        if (value < 0x100000000000000L)
            return 8;
        return 9;
    }

    public static byte readByteSafely(InputStream is) throws IOException {
        int i = is.read();
        if (i == -1) {
            throw new EOFException("Unexpected end of VarInt record");
        }
        return (byte) i;
    }
}