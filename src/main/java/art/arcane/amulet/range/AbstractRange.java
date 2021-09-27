

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

public abstract class AbstractRange<E extends Comparable<E>, ME extends AbstractRange<E, ME>> implements Range<E, ME> {
    final private E _left;
    final private E _right;
    final private boolean _leftClosed;
    final private boolean _rightClosed;
    final private boolean _reversed;

    public AbstractRange(E left, E right) {
        this(left, right, true, true, false);
    }

    public AbstractRange(E left, E right, boolean leftClosed, boolean rightClosed, boolean reverse) {
        checkArgs(left, right);
        _reversed = reverse;
        if (reverse) {
            _left = right;
            _right = left;

            _leftClosed = rightClosed;
            _rightClosed = leftClosed;
        } else {
            _left = left;
            _right = right;

            _leftClosed = leftClosed;
            _rightClosed = rightClosed;
        }

        if (_left.compareTo(_right) > 0) {
            throw new IllegalArgumentException(
                    "The logical left endpoint is greater than the logical right endpoint: [" + left + ", " + right + "]");
        }
    }

    private void checkArgs(E left, E right) {
        if (left == null) {
            throw new IllegalArgumentException("Non-null value expected for left endpoint.");
        }
        if (right == null) {
            throw new IllegalArgumentException("Non-null value expected for right endpoint.");
        }
    }

    @Override
    public E getLeftEndpoint() {
        return _left;
    }

    @Override
    public E getRightEndpoint() {
        return _right;
    }

    @Override
    public boolean isLeftClosed() {
        return _leftClosed;
    }

    @Override
    public boolean isRightClosed() {
        return _rightClosed;
    }

    @Override
    public boolean contains(E e) {
        return (isLeftClosed()
                ? getLeftEndpoint().compareTo(e) <= 0
                : getLeftEndpoint().compareTo(e) < 0) &&
                (isRightClosed()
                        ? getRightEndpoint().compareTo(e) >= 0
                        : getRightEndpoint().compareTo(e) > 0);
    }

    @Override
    public boolean contains(ME range) {
        return (isLeftClosed()
                ? getLeftEndpoint().compareTo(range.getLeftEndpoint()) <= 0
                : range.isLeftClosed()
                ? getLeftEndpoint().compareTo(range.getLeftEndpoint()) < 0
                : getLeftEndpoint().compareTo(range.getLeftEndpoint()) <= 0) &&
                (isRightClosed()
                        ? getRightEndpoint().compareTo(range.getRightEndpoint()) >= 0
                        : range.isRightClosed()
                        ? getRightEndpoint().compareTo(range.getRightEndpoint()) > 0
                        : getRightEndpoint().compareTo(range.getRightEndpoint()) >= 0);
    }

    @Override
    public boolean isReversed() {
        return _reversed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractRange)) {
            return false;
        }

        AbstractRange that = (AbstractRange) o;

        if (isLeftClosed() != that.isLeftClosed()) {
            return false;
        }
        if (isReversed() != that.isReversed()) {
            return false;
        }
        if (isRightClosed() != that.isRightClosed()) {
            return false;
        }
        if (!getLeftEndpoint().equals(that.getLeftEndpoint())) {
            return false;
        }
        return getRightEndpoint().equals(that.getRightEndpoint());

    }

    @Override
    public int hashCode() {
        int result = getLeftEndpoint().hashCode();
        result = 31 * result + getRightEndpoint().hashCode();
        result = 31 * result + (isLeftClosed() ? 1 : 0);
        result = 31 * result + (isRightClosed() ? 1 : 0);
        result = 31 * result + (isReversed() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        if (isReversed()) {
            return getRightEndpoint() + (isRightClosed() ? "" : "|") + ".." + (isLeftClosed() ? "" : "|") + getLeftEndpoint();
        }
        return getLeftEndpoint() + (isLeftClosed() ? "" : "|") + ".." + (isRightClosed() ? "" : "|") + getRightEndpoint();
    }
}