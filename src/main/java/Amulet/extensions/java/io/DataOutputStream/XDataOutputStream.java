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

package Amulet.extensions.java.io.DataOutputStream;

import art.arcane.amulet.data.Varint;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.DataOutputStream;
import java.io.IOException;

@Extension
public class XDataOutputStream {
    public static void writeVarInt(@This DataOutputStream self, int i) throws IOException {
        Varint.writeSignedVarInt(i, self);
    }

    public static void writeVarLong(@This DataOutputStream self, long i) throws IOException {
        Varint.writeSignedVarLong(i, self);
    }

    public static void writeUnsignedVarInt(@This DataOutputStream self, int i) throws IOException {
        Varint.writeUnsignedVarInt(i, self);
    }

    public static void writeUnsignedVarLong(@This DataOutputStream self, long i) throws IOException {
        Varint.writeUnsignedVarLong(i, self);
    }
}