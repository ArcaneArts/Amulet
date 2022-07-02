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

package art.arcane.amulet.io.nbt.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public interface StringDeserializer<T> extends Deserializer<T> {

	T fromReader(Reader reader) throws IOException;

	default T fromString(String s) throws IOException {
		return fromReader(new StringReader(s));
	}

	@Override
	default T fromStream(InputStream stream) throws IOException {
		try (Reader reader = new InputStreamReader(stream)) {
			return fromReader(reader);
		}
	}

	@Override
	default T fromFile(File file) throws IOException {
		try (Reader reader = new FileReader(file)) {
			return fromReader(reader);
		}
	}

	@Override
	default T fromBytes(byte[] data) throws IOException {
		return fromReader(new StringReader(new String(data)));
	}
}
