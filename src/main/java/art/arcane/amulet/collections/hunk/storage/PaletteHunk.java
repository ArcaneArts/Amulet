/*
 * Iris is a World Generator for Minecraft Bukkit Servers
 * Copyright (c) 2021 Arcane Arts (Volmit Software)
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

import art.arcane.amulet.collections.container.DataContainer;
import art.arcane.amulet.collections.container.NodeWritable;
import art.arcane.amulet.collections.hunk.Hunk;
import art.arcane.amulet.functional.Consume;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@SuppressWarnings({"DefaultAnnotationParam", "Lombok"})
@Data
@EqualsAndHashCode(callSuper = false)
public class PaletteHunk<T> extends StorageHunk<T> implements Hunk<T> {
    private DataContainer<T> data;

    public PaletteHunk(int w, int h, int d, NodeWritable<T> writer) {
        super(w, h, d);
        data = new DataContainer<>(writer, w * h * d);
    }

    public void setPalette(DataContainer<T> c) {
        data = c;
    }

    public boolean isMapped() {
        return false;
    }

    private int index(int x, int y, int z) {
        return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
    }

    @Override
    public synchronized Hunk<T> iterateSync(Consume.Four<Integer, Integer, Integer, T> c) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getDepth(); k++) {
                    T t = getRaw(i, j, k);
                    if (t != null) {
                        c.accept(i, j, k, t);
                    }
                }
            }
        }
        return this;
    }

    @Override
    public synchronized Hunk<T> iterateSyncIO(Consume.FourIO<Integer, Integer, Integer, T> c) throws IOException {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                for (int k = 0; k < getDepth(); k++) {
                    T t = getRaw(i, j, k);
                    if (t != null) {
                        c.accept(i, j, k, t);
                    }
                }
            }
        }
        return this;
    }

    @Override
    public void setRaw(int x, int y, int z, T t) {
        data.set(index(x, y, z), t);
    }

    @Override
    public T getRaw(int x, int y, int z) {
        return data.get(index(x, y, z));
    }
}
