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

package art.arcane.amulet.collections.hunk.view;

import art.arcane.amulet.collections.hunk.Hunk;
import art.arcane.amulet.functional.Consume;

@SuppressWarnings("ClassCanBeRecord")
public class ListeningHunk<T> implements Hunk<T> {
    private final Hunk<T> src;
    private final Consume.Four<Integer, Integer, Integer, T> listener;

    public ListeningHunk(Hunk<T> src, Consume.Four<Integer, Integer, Integer, T> listener) {
        this.src = src;
        this.listener = listener;
    }

    @Override
    public void setRaw(int x, int y, int z, T t) {
        listener.accept(x, y, z, t);
        src.setRaw(x, y, z, t);
    }

    @Override
    public T getRaw(int x, int y, int z) {
        return src.getRaw(x, y, z);
    }

    @Override
    public int getWidth() {
        return src.getWidth();
    }

    @Override
    public int getHeight() {
        return src.getHeight();
    }

    @Override
    public int getDepth() {
        return src.getDepth();
    }

    @Override
    public Hunk<T> getSource() {
        return src;
    }
}
