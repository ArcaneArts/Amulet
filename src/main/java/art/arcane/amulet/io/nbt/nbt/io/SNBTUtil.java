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

import art.arcane.amulet.io.nbt.nbt.tag.Tag;

import java.io.IOException;

public class SNBTUtil {

    public static String toSNBT(Tag<?> tag) throws IOException {
        return new SNBTSerializer().toString(tag);
    }

    public static Tag<?> fromSNBT(String string) throws IOException {
        return new SNBTDeserializer().fromString(string);
    }

    public static Tag<?> fromSNBT(String string, boolean lenient) throws IOException {
        return new SNBTParser(string).parse(Tag.DEFAULT_MAX_DEPTH, lenient);
    }
}
