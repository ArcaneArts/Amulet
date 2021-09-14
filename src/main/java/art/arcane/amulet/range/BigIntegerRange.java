

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

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BigIntegerRange extends NumberRange<BigInteger, BigIntegerRange> {
    @SuppressWarnings({"UnusedDeclaration"})
    public BigIntegerRange(BigInteger left, BigInteger right) {
        this(left, right, BigInteger.ONE, true, true, false);
    }

    public BigIntegerRange unaryMinus() {
        var left = getLeftEndpoint();
        var right = getRightEndpoint();

        if (isReversed()) {
            return new BigIntegerRange(left, right, getStep(), true, true, false);
        }

        return new BigIntegerRange(right, left, getStep(), true, true, true);
    }

    public BigIntegerRange(BigInteger left, BigInteger right, BigInteger step, boolean leftClosed, boolean rightClosed, boolean reverse) {
        super(left, right, step, leftClosed, rightClosed, reverse);

        if (step.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("The step must be greater than 0: " + step);
        }
    }

    @Override
    public Iterator<BigInteger> iterateFromLeft() {
        return new ForwardIterator();
    }

    @Override
    public Iterator<BigInteger> iterateFromRight() {
        return new ReverseIterator();
    }

    @Override
    public BigInteger getFromLeft(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isLeftClosed()) {
            iStepIndex++;
        }
        BigInteger value = getLeftEndpoint().add(getStep().multiply(BigInteger.valueOf(iStepIndex)));
        int iComp = value.compareTo(getRightEndpoint());
        if (isRightClosed() ? iComp <= 0 : iComp < 0) {
            return value;
        }

        return null;
    }

    @Override
    public BigInteger getFromRight(int iStepIndex) {
        if (iStepIndex < 0) {
            throw new IllegalArgumentException("Step index must be >= 0: " + iStepIndex);
        }

        if (!isRightClosed()) {
            iStepIndex++;
        }
        BigInteger value = getRightEndpoint().subtract(getStep().multiply(BigInteger.valueOf(iStepIndex)));
        int iComp = value.compareTo(getLeftEndpoint());
        if (isLeftClosed() ? iComp >= 0 : iComp > 0) {
            return value;
        }

        return null;
    }

    private class ForwardIterator implements Iterator<BigInteger> {
        private BigInteger _csr;

        public ForwardIterator() {
            _csr = getLeftEndpoint();
            if (!isLeftClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            int iComp = _csr.compareTo(getRightEndpoint());
            return iComp < 0 || (isRightClosed() && iComp == 0);
        }

        @Override
        public BigInteger next() {
            int iComp = _csr.compareTo(getRightEndpoint());
            if (iComp > 0 ||
                    (!isRightClosed() && iComp == 0)) {
                throw new NoSuchElementException();
            }
            BigInteger ret = _csr;
            _csr = _csr.add(getStep());
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ReverseIterator implements Iterator<BigInteger> {
        private BigInteger _csr;

        public ReverseIterator() {
            _csr = getRightEndpoint();
            if (!isRightClosed() && hasNext()) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            int iComp = _csr.compareTo(getLeftEndpoint());
            return iComp > 0 || (isLeftClosed() && iComp == 0);
        }

        @Override
        public BigInteger next() {
            int iComp = _csr.compareTo(getLeftEndpoint());
            if (iComp < 0 ||
                    (!isLeftClosed() && iComp == 0)) {
                throw new NoSuchElementException();
            }
            BigInteger ret = _csr;
            _csr = _csr.subtract(getStep());
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}