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

import art.arcane.amulet.io.nbt.io.StringSerializer;
import art.arcane.amulet.io.nbt.nbt.tag.Tag;

import java.io.IOException;
import java.io.Writer;

public class SNBTSerializer implements StringSerializer<Tag<?>> {

    @Override
    public void toWriter(Tag<?> tag, Writer writer) throws IOException {
        SNBTWriter.write(tag, writer);
    }

    public void toWriter(Tag<?> tag, Writer writer, int maxDepth) throws IOException {
        SNBTWriter.write(tag, writer, maxDepth);
    }
}
