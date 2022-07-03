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

package art.arcane.amulet.io.nbt.nbt.io;

import art.arcane.amulet.io.nbt.io.Serializer;
import art.arcane.amulet.io.nbt.nbt.tag.Tag;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class NBTSerializer implements Serializer<NamedTag> {

    private boolean compressed, littleEndian;

    public NBTSerializer() {
        this(true);
    }

    public NBTSerializer(boolean compressed) {
        this.compressed = compressed;
    }

    public NBTSerializer(boolean compressed, boolean littleEndian) {
        this.compressed = compressed;
        this.littleEndian = littleEndian;
    }

    @Override
    public void toStream(NamedTag object, OutputStream out) throws IOException {
        NBTOutput nbtOut;
        OutputStream output;
        if (compressed) {
            output = new GZIPOutputStream(out, true);
        } else {
            output = out;
        }

        if (littleEndian) {
            nbtOut = new LittleEndianNBTOutputStream(output);
        } else {
            nbtOut = new NBTOutputStream(output);
        }
        nbtOut.writeTag(object, Tag.DEFAULT_MAX_DEPTH);
        nbtOut.flush();
    }
}
