

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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequentialRange<E extends Sequential<E, S, U>, S, U> extends AbstractIterableRange<E, S, U, SequentialRange<E, S, U>> {
    public SequentialRange(E left, E right, S step, U unit, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, step, unit, reverse ? rightClosed : leftClosed, reverse ? leftClosed : rightClosed, reverse);
    }

    @Override
    public Iterator<E> iterateFromLeft() {
        return new SequentialIterator();
    }

    public SequentialRange<E, S, U> unaryMinus() {
        var left = getLeftEndpoint();
        var right = getRightEndpoint();

        if (isReversed()) {
            return new SequentialRange<>(left, right, getStep(), getUnit(), true, true, false);
        }

        return new SequentialRange<>(right, left, getStep(), getUnit(), true, true, true);
    }

    @Override
    public Iterator<E> iterateFromRight() {
        return new ReverseSequentialIterator();
    }

    @Override
    public E getFromLeft(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isLeftClosed()) {
            iStepIndex++;
        }
        E value = getLeftEndpoint().nextNthInSequence(getStep(), getUnit(), iStepIndex);
        int iComp = value.compareTo(getRightEndpoint());
        if (isRightClosed() ? iComp <= 0 : iComp < 0) {
            return value;
        }

        return null;
    }

    @Override
    public E getFromRight(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isRightClosed()) {
            iStepIndex++;
        }
        E value = getRightEndpoint().previousNthInSequence(getStep(), getUnit(), iStepIndex);
        int iComp = value.compareTo(getLeftEndpoint());
        if (isLeftClosed() ? iComp >= 0 : iComp > 0) {
            return value;
        }

        return null;
    }

    private class SequentialIterator implements Iterator<E> {
        private E _csr;

        public SequentialIterator() {
            _csr = getLeftEndpoint();
            if (!isLeftClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            if (_csr == null) {
                return false;
            }
            int iComp = _csr.compareTo(getRightEndpoint());
            return iComp < 0 || (isRightClosed() && iComp == 0);
        }

        @Override
        public E next() {
            int iComp = _csr.compareTo(getRightEndpoint());
            if (iComp > 0 ||
                    (!isRightClosed() && iComp == 0)) {
                throw new NoSuchElementException();
            }
            E ret = _csr;
            _csr = _csr.nextInSequence(getStep(), getUnit());
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ReverseSequentialIterator implements Iterator<E> {
        private E _csr;

        public ReverseSequentialIterator() {
            _csr = getRightEndpoint();
            if (!isRightClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            if (_csr == null) {
                return false;
            }

            int iComp = _csr.compareTo(getLeftEndpoint());
            return iComp > 0 || (isLeftClosed() && iComp == 0);
        }

        @Override
        public E next() {
            int iComp = _csr.compareTo(getLeftEndpoint());
            if (iComp < 0 ||
                    (!isLeftClosed() && iComp == 0)) {
                throw new NoSuchElementException();
            }
            E ret = _csr;
            _csr = _csr.previousInSequence(getStep(), getUnit());
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}