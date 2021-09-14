

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

public final class LongRange extends NumberRange<Long, LongRange> {
    @SuppressWarnings({"UnusedDeclaration"})
    public LongRange(Long left, Long right) {
        this(left, right, 1, true, true, false);
    }

    public LongRange(Long left, Long right, long lStep, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, lStep, leftClosed, rightClosed, reverse);

        if (lStep <= 0) {
            throw new IllegalArgumentException("The step must be greater than 0: " + lStep);
        }
    }

    @Override
    public Iterator<Long> iterateFromLeft() {
        return new ForwardIterator();
    }

    @Override
    public Iterator<Long> iterateFromRight() {
        return new ReverseIterator();
    }

    @Override
    public Long getFromLeft(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isLeftClosed()) {
            iStepIndex++;
        }
        long value = getLeftEndpoint() + getStep() * iStepIndex;
        if (isRightClosed() ? value <= getRightEndpoint() : value < getRightEndpoint()) {
            return value;
        }

        return null;
    }

    public LongRange unaryMinus() {
        var left = getLeftEndpoint();
        var right = getRightEndpoint();

        if (isReversed()) {
            return new LongRange(left, right, getStep(), true, true, false);
        }

        return new LongRange(right, left, getStep(), true, true, true);
    }

    @Override
    public Long getFromRight(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isRightClosed()) {
            iStepIndex++;
        }
        long value = getRightEndpoint() - getStep() * iStepIndex;
        if (isLeftClosed() ? value >= getLeftEndpoint() : value > getLeftEndpoint()) {
            return value;
        }

        return null;
    }

    public class ForwardIterator extends AbstractLongIterator {
        private long _csr;

        public ForwardIterator() {
            _csr = getLeftEndpoint();
            if (!isLeftClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return _csr < getRightEndpoint() || (isRightClosed() && _csr == getRightEndpoint());
        }

        @Override
        public Long next() {
            return nextLong();
        }

        public long nextLong() {
            if (_csr > getRightEndpoint() ||
                    (!isRightClosed() && _csr == getRightEndpoint())) {
                throw new NoSuchElementException();
            }
            long ret = _csr;
            _csr = _csr + getStep();
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ReverseIterator extends AbstractLongIterator {
        private long _csr;

        public ReverseIterator() {
            _csr = getRightEndpoint();
            if (!isRightClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return _csr > getLeftEndpoint() || (isLeftClosed() && _csr == getLeftEndpoint());
        }

        @Override
        public Long next() {
            return nextLong();
        }

        public long nextLong() {
            if (_csr < getLeftEndpoint() ||
                    (!isLeftClosed() && _csr == getLeftEndpoint())) {
                throw new NoSuchElementException();
            }
            long ret = _csr;
            _csr = _csr - getStep();
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}