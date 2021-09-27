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

package art.arcane.amulet.range;

/**
 * Implement {@code Sequential} if the set of possible instances of your type are a <i>sequence</i>, whereby given an
 * arbitrary instance of the type and some <i>step</i> or increment value, the <i>next</i> instance can be computed.
 *
 * @param <E> The implementing type e.g., Rational or Length (recursive type)
 * @param <S> The step type e.g., Rational
 * @param <U> The unit type of the step e.g., Void or LengthUnit
 */
public interface Sequential<E extends Sequential<E, S, U>, S, U> extends Comparable<E> {
    /**
     * Given a {@code step} and {@code unit} produces the next instance in the sequence. For instance, given a Length
     * value of {@code 1 meter}, a step value of {@code 5}, and a unit of {@code Centimeter}, the next value in the
     * sequence is {@code 1.05 meters}.
     *
     * @param step A value indicating the number of units separating this instance from the next one in the sequence.
     * @param unit Specifies the unit of the {@code step} for instance {@code inch} or {@code month}. Note if the unit
     *             type is {@link Void}, the unit value is insignificant and will have a null value.
     * @return The next instance separated by {@code step} {@code unit}s from this instance.
     */
    E nextInSequence(S step, U unit);

    /**
     * Given a {@code step}, {@code unit}, and {@code index} produce the next instance in the sequence. For instance,
     * given a Length value of {@code 1 meter}, a step value of {@code 5}, a unit of {@code Centimeter}, and an index of
     * {@code 2}, the next value in the sequence is {@code 1.10 meters}.
     *
     * @param step  A value indicating the number of units separating this instance from the next one in the sequence.
     * @param unit  Specifies the unit of the {@code step} for instance {@code inch} or {@code month}. Note if the unit
     *              type is {@link Void}, the unit value is insignificant and will have a null value.
     * @param index A offset in terms of {@code step} and {@code unit}, typically {@code 1}.
     * @return The next instance separated by {@code index * step * unit} from this instance.
     */
    E nextNthInSequence(S step, U unit, int index);

    /**
     * Given a {@code step} and {@code unit} produces the previous instance in the sequence. For instance, given a Length
     * value of {@code 1 meter}, a step value of {@code 5}, and a unit of {@code Centimeter}, the previous value in the
     * sequence is {@code 0.95 meters}.
     *
     * @param step A value indicating the number of units separating this instance from the previous one in the sequence.
     * @param unit Specifies the unit of the {@code step} for instance {@code inch} or {@code month}. Note if the unit
     *             type is {@link Void}, the unit value is insignificant and will have a null value.
     * @return The previous instance separated by {@code step} {@code unit}s from this instance.
     */
    E previousInSequence(S step, U unit);

    /**
     * Given a {@code step}, {@code unit}, and {@code index} produce the previous instance in the sequence. For instance,
     * given a Length value of {@code 1 meter}, a step value of {@code 5}, a unit of {@code Centimeter}, and an index of
     * {@code 2}, the previous value in the sequence is {@code 0.90 meters}.
     *
     * @param step  A value indicating the number of units separating this instance from the previous one in the sequence.
     * @param unit  Specifies the unit of the {@code step} for instance {@code inch} or {@code month}. Note if the unit
     *              type is {@link Void}, the unit value is insignificant and will have a null value.
     * @param index A offset in terms of {@code step} and {@code unit}, typically {@code 1}.
     * @return The previous instance separated by {@code index * step * unit} from this instance.
     */
    E previousNthInSequence(S step, U unit, int index);
}
