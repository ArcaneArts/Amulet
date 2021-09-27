

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

public final class DoubleRange extends NumberRange<Double, DoubleRange> {
    public DoubleRange(Double left, Double right) {
        this(left, right, 1);
    }

    public DoubleRange unaryMinus() {
        var left = getLeftEndpoint();
        var right = getRightEndpoint();

        if (isReversed()) {
            return new DoubleRange(left, right, getStep(), true, true, false);
        }

        return new DoubleRange(right, left, getStep(), true, true, true);
    }

    public DoubleRange(Double left, Double right, double step) {
        this(left, right, step, true, true, false);
    }

    public DoubleRange(Double left, Double right, double step, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, step, leftClosed, rightClosed, reverse);

        if (step <= 0) {
            throw new IllegalArgumentException("The step must be greater than 0: " + step);
        }
    }

    @Override
    public Iterator<Double> iterateFromLeft() {
        return new ForwardIterator();
    }

    @Override
    public Iterator<Double> iterateFromRight() {
        return new ReverseIterator();
    }

    @Override
    public Double getFromLeft(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isLeftClosed()) {
            iStepIndex++;
        }
        double value = getLeftEndpoint() + getStep() * iStepIndex;
        if (isRightClosed() ? value <= getRightEndpoint() : value < getRightEndpoint()) {
            return value;
        }

        return null;
    }

    @Override
    public Double getFromRight(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isRightClosed()) {
            iStepIndex++;
        }
        double value = getRightEndpoint() - getStep() * iStepIndex;
        if (isLeftClosed() ? value >= getLeftEndpoint() : value > getLeftEndpoint()) {
            return value;
        }

        return null;
    }

    public class ForwardIterator implements Iterator<Double> {
        private double _csr;

        ForwardIterator() {
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
        public Double next() {
            if (_csr > getRightEndpoint() ||
                    (!isRightClosed() && _csr == getRightEndpoint())) {
                throw new NoSuchElementException();
            }
            double ret = _csr;
            _csr = _csr + getStep();
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ReverseIterator implements Iterator<Double> {
        private double _csr;

        ReverseIterator() {
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
        public Double next() {
            if (_csr < getLeftEndpoint() ||
                    (!isLeftClosed() && _csr == getLeftEndpoint())) {
                throw new NoSuchElementException();
            }
            double ret = _csr;
            _csr = _csr - getStep();
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
