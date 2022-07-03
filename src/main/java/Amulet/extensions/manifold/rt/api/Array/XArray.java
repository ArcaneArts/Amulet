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

package Amulet.extensions.manifold.rt.api.Array;

import art.arcane.amulet.io.IO;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Extension
public class XArray {


    public static byte[] fromBase64(@This Object self) {
        if (self instanceof byte[] b) {
            return Base64.getUrlDecoder().decode(b);
        }

        throw new UnsupportedOperationException("This method only supports byte arrays!");
    }

    public static String fromBase64String(@This Object self) {
        if (self instanceof byte[] b) {
            return new String(Base64.getUrlDecoder().decode(b), StandardCharsets.UTF_8);
        }

        throw new UnsupportedOperationException("This method only supports byte arrays!");
    }

    /**
     * Byte Arrays only. Converts these bytes into a hex string
     *
     * @return the hex string representing these bytes
     */
    public static String hex(@This Object self) {
        if (self instanceof byte[] b) {
            return IO.bytesToHex(b);
        }

        throw new UnsupportedOperationException("This method only supports byte arrays!");
    }

    /**
     * Byte Arrays Only. Converts this into a bytearrayinputstream
     *
     * @return the input stream
     */
    public static InputStream read(@This Object self) {
        if (self instanceof byte[] b) {
            return new ByteArrayInputStream(b);
        }

        throw new UnsupportedOperationException("This method only supports byte arrays!");
    }
}