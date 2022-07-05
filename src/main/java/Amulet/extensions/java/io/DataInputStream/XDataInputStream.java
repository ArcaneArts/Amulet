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

package Amulet.extensions.java.io.DataInputStream;

import art.arcane.amulet.data.Varint;
import art.arcane.amulet.io.nbt.nbt.io.NBTDeserializer;
import art.arcane.amulet.io.nbt.nbt.io.NBTSerializer;
import art.arcane.amulet.io.nbt.nbt.io.NamedTag;
import art.arcane.amulet.io.nbt.nbt.tag.CompoundTag;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Extension
public class XDataInputStream {
    public static CompoundTag readNBT(@This DataInputStream self) throws IOException {
        return (CompoundTag) new NBTDeserializer(true, false).fromStream(self).getTag();
    }

    public static int readVarInt(@This DataInputStream self) throws IOException {
        return Varint.readSignedVarInt(self);
    }

    public static long readVarLong(@This DataInputStream self) throws IOException {
        return Varint.readSignedVarLong(self);
    }

    public static int readUnsignedVarInt(@This DataInputStream self) throws IOException {
        return Varint.readUnsignedVarInt(self);
    }

    public static long readUnsignedVarLong(@This DataInputStream self) throws IOException {
        return Varint.readUnsignedVarLong(self);
    }
}