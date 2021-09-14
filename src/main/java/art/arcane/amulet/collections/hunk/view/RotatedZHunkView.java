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

public class RotatedZHunkView<T> implements Hunk<T> {
    private final Hunk<T> src;
    private final double sin;
    private final double cos;

    public RotatedZHunkView(Hunk<T> src, double deg) {
        this.src = src;
        this.sin = Math.sin(Math.toRadians(deg));
        this.cos = Math.cos(Math.toRadians(deg));
    }

    @Override
    public void setRaw(int x, int y, int z, T t) {
        int xc = (int) Math.round(cos * (getWidth() / 2f) - sin * (getHeight() / 2f));
        int yc = (int) Math.round(sin * (getWidth() / 2f) + cos * (getHeight() / 2f));
        src.setIfExists((int) Math.round(cos * (x - xc) - sin * (y - yc)) - xc, (int) Math.round(sin * (x - xc) + cos * (y - yc)) - yc, z, t);
    }

    @Override
    public T getRaw(int x, int y, int z) {
        int xc = (int) Math.round(cos * (getWidth() / 2f) - sin * (getHeight() / 2f));
        int yc = (int) Math.round(sin * (getWidth() / 2f) + cos * (getHeight() / 2f));
        return src.getIfExists((int) Math.round(cos * (x - xc) - sin * (y - yc)) - xc,
                (int) Math.round(sin * (x - xc) + cos * (y - yc)) - yc
                , z);
    }

    @Override
    public int getWidth() {
        return src.getWidth();
    }

    @Override
    public int getDepth() {
        return src.getDepth();
    }

    @Override
    public int getHeight() {
        return src.getHeight();
    }

    @Override
    public Hunk<T> getSource() {
        return src;
    }
}
