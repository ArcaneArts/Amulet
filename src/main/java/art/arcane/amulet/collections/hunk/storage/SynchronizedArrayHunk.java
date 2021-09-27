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

package art.arcane.amulet.collections.hunk.storage;

import art.arcane.amulet.collections.hunk.Hunk;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

@SuppressWarnings({"DefaultAnnotationParam", "Lombok"})
@Data
@EqualsAndHashCode(callSuper = false)
public class SynchronizedArrayHunk<T> extends StorageHunk<T> implements Hunk<T> {
    private final T[] data;

    @SuppressWarnings("unchecked")
    public SynchronizedArrayHunk(int w, int h, int d) {
        super(w, h, d);
        data = (T[]) new Object[w * h * d];
    }

    @Override
    public void setRaw(int x, int y, int z, T t) {
        synchronized (data) {
            data[index(x, y, z)] = t;
        }
    }

    @Override
    public T getRaw(int x, int y, int z) {
        synchronized (data) {
            return data[index(x, y, z)];
        }
    }

    private int index(int x, int y, int z) {
        return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
    }

    @Override
    public void fill(T t) {
        synchronized (data) {
            Arrays.fill(data, t);
        }
    }
}
