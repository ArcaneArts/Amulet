

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

import java.util.Iterator;

public interface IterableRange<E extends Comparable<E>, S, U, ME extends IterableRange<E, S, U, ME>> extends Iterable<E>, Range<E, ME> {
    /**
     * @return An iterator that visits the elements in this range in order, from left to right.
     * Returns null if this range does not support iteration.
     * @see #iterateFromLeft()
     * @see #iterateFromRight()
     */
    Iterator<E> iterator();

    /**
     * @return An iterator that visits the elements in this range in order, from left to right.
     * Returns null if this range does not support iteration.
     * @see #iterator()
     * @see #iterateFromRight()
     */
    Iterator<E> iterateFromLeft();

    /**
     * @return An iterator that visits the elements in this range in reverse order, from right to left.
     * Returns null if this range does not support iteration.
     * @see #iterator()
     * @see #iterateFromLeft()
     */
    Iterator<E> iterateFromRight();

    /**
     * @return The step (or increment) by which this range visits elements in its set. Returns null
     * if this range cannot iterate its elements.
     * <p>
     * For instance, if the range is a set of decimal values, say [1..10], the step might be a decimal
     * increment, say 0.25. Similarly, if the range is simply a set of integers the step might also be
     * an integer value, typically 1. Considering a date range, say [4/5/10..5/20/10], the step could
     * be expressed in terms of a unit of time e.g., 10 seconds, 1 minute, 2 weeks, etc.
     * <p>
     * Note if non-null, the step is a <i>positive</i> (or absolute) increment. To iterate the range
     * in reverse order use iterateFromRight().
     */
    S getStep();

    ME step(S s);

    U getUnit();

    ME unit(U u);

    /**
     * @param iStepIndex The index of the step from the left endpoint
     * @return The nth step from the left endpoint. Returns null if iStepIndex is out of bounds.
     * @thows IllegalArgumentException if iStepIndex is < 0
     */
    E getFromLeft(int iStepIndex);

    /**
     * @param iStepIndex The index of the step from the right endpoint
     * @return The nth step from the right endpoint. Returns null if iStepIndex is out of bounds.
     * @thows IllegalArgumentException if iStepIndex is < 0
     */
    E getFromRight(int iStepIndex);
}