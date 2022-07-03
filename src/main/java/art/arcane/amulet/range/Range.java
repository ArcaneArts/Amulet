

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


/**
 * A range of {@link Comparable} elements defined by two endpoints.
 *
 * @param <E>  The type of elements in the range, must implement {@link Comparable}
 * @param <ME> The range type (recursive)
 */
public interface Range<E extends Comparable<E>, ME extends Range<E, ME>> {
    /**
     * @return The left endpoint of this range where the left <= right
     */
    E getLeftEndpoint();

    /**
     * @return The right endpoint of this range where the left <= right
     */
    E getRightEndpoint();

    /**
     * @return True if this range <i>includes</i> the left endpoint.
     */
    boolean isLeftClosed();

    /**
     * @return True if this range <i>includes</i> the right endpoint.
     */
    boolean isRightClosed();

    /**
     * @param elem An element to test
     * @return True if elem is a proper element in the set of elements defining this range.
     */
    boolean contains(E elem);

    /**
     * @param range An range to test for containment
     * @return True if range's endpoints are proper elements in the set of elements defining this range.
     */
    boolean contains(ME range);

    /**
     * @return True if this range iterates from the right by default e.g.,
     * if the range is specified in reverse order: 10..1, a reverse range results
     */
    boolean isReversed();
}
