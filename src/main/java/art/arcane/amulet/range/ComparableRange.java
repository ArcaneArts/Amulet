

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

package art.arcane.amulet.range;

public final class ComparableRange<E extends Comparable<E>> extends AbstractRange<E, ComparableRange<E>> {
    public ComparableRange(E left, E right) {
        this(left, right, true, true, false);
    }

    public ComparableRange(E left, E right, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, leftClosed, rightClosed, reverse);
    }

    public ComparableRange<E> unaryMinus() {
        E left = getLeftEndpoint();
        E right = getRightEndpoint();

        if (isReversed()) {
            return new ComparableRange<E>(left, right, true, true, false);
        }

        return new ComparableRange<E>(right, left, true, true, true);
    }
}