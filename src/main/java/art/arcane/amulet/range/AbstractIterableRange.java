

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

public abstract class AbstractIterableRange<E extends Comparable<E>, S, U, ME extends AbstractIterableRange<E, S, U, ME>> extends AbstractRange<E, ME> implements IterableRange<E, S, U, ME> {
    private S _step;
    private U _unit;

    public AbstractIterableRange(E left, E right, S step) {
        this(left, right, step, null, true, true, false);
    }

    public AbstractIterableRange(E left, E right, S step, U unit, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, reverse ? rightClosed : leftClosed, reverse ? leftClosed : rightClosed, reverse);

        _step = step;
        _unit = unit;
    }

    @Override
    public Iterator<E> iterator() {
        if (isReversed()) {
            return iterateFromRight();
        } else {
            return iterateFromLeft();
        }
    }

    @Override
    public S getStep() {
        return _step;
    }

    @Override
    public ME step(S s) {
        _step = s;
        //noinspection unchecked
        return (ME) this;
    }

    @Override
    public U getUnit() {
        return _unit;
    }

    @Override
    public ME unit(U u) {
        _unit = u;
        //noinspection unchecked
        return (ME) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractIterableRange)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        AbstractIterableRange that = (AbstractIterableRange) o;

        if (_step != null ? !_step.equals(that._step) : that._step != null) {
            return false;
        }
        return !(_unit != null ? !_unit.equals(that._unit) : that._unit != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_step != null ? _step.hashCode() : 0);
        result = 31 * result + (_unit != null ? _unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " step " + getStep();
    }
}