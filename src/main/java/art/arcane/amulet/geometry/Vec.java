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

package art.arcane.amulet.geometry;

import manifold.ext.rt.api.Structural;

import java.util.Random;

@Structural
public interface Vec {
    /**
     * Threshold for fuzzy equals().
     */
    static final double epsilon = 0.000001;

    static Vec of(Vec other) {
        return other.copy();
    }

    static Vec of(double x, double y, double z) {
        return new VecImpl3(x, y, z);
    }

    static Vec of(int x, int y, int z) {
        return new VecImpl3(x, y, z);
    }

    static Vec of(double x) {
        return new VecImpl3(x);
    }

    /**
     * Add to this vector
     *
     * @param x +
     * @param y +
     * @param z +
     * @return this (now added) vector
     */
    default Vec add(double x, double y, double z) {
        return x(x() + x).y(y() + y).z(z() + z);
    }

    default Vec multiply(double mult) {
        return multiply(mult, mult, mult);
    }

    default Vec add(double all) {
        return add(all, all, all);
    }

    default Vec copy() {
        return new VecImpl3(x(), y(), z());
    }

    default Vec rem(double d) {
        return copy().modulus(d);
    }

    default Vec plus(double d) {
        return copy().add(d);
    }

    default Vec minus(double d) {
        return copy().subtract(d);
    }

    default Vec times(double d) {
        return copy().multiply(d);
    }

    default Vec div(double d) {
        return copy().divide(d);
    }

    default Vec rem(Vec d) {
        return copy().modulus(d);
    }

    default Vec plus(Vec d) {
        return copy().add(d);
    }

    default Vec minus(Vec d) {
        return copy().subtract(d);
    }

    default Vec times(Vec d) {
        return copy().multiply(d);
    }

    default Vec div(Vec d) {
        return copy().divide(d);
    }

    default Vec modulus(Vec d) {
        return modulus(d.x(), d.y(), d.z());
    }

    default Vec divide(Vec d) {
        return divide(d.x(), d.y(), d.z());
    }

    default Vec multiply(Vec d) {
        return multiply(d.x(), d.y(), d.z());
    }

    default Vec inc() {
        return copy().add(1);
    }

    default Vec dec() {
        return copy().subtract(1);
    }

    default Vec unaryMinus() {
        return copy().reverse();
    }

    default Vec divide(double d) {
        return divide(d, d, d);
    }

    default Vec modulus(double d) {
        return modulus(d, d, d);
    }

    default Vec subtract(double d) {
        return add(-d);
    }

    default Vec direction(Vec to) {
        return directionNoNormal(to).normalize();
    }
    default Vec directionNoNormal(Vec to) {
        return to - this;
    }

    default Vec multiply(double x, double y, double z) {
        return x(x() * x).y(y() * y).z(z() * z);
    }

    default Vec divide(double x, double y, double z) {
        return x(x() / x).y(y() / y).z(z() / z);
    }

    default Vec modulus(double x, double y, double z) {
        return x(x() % x).y(y() % y).z(z() % z);
    }

    default Vec reverse() {
        return x(-x()).y(-y()).z(-z());
    }

    default Vec reverseX() {
        return x(-x());
    }

    default Vec reverseY() {
        return y(-y());
    }

    default Vec reverseZ() {
        return z(-z());
    }

    default double get(int index) {
        if (index == 0) {
            return x();
        } else if (index == 1) {
            return y();
        } else if (index == 2) {
            return z();
        } else {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    default double set(int index, double value) {
        if (index == 0) {
            x(value);
            return value;
        } else if (index == 1) {
            y(value);
            return value;
        } else if (index == 2) {
            z(value);
            return value;
        } else {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    default Vec subtract(double x, double y, double z) {
        return add(-x, -y, -z);
    }

    default Vec add(Vec v) {
        return add(v.x(), v.y(), v.z());
    }

    default Vec subtract(Vec v) {
        return subtract(v.x(), v.y(), v.z());
    }

    double x();

    Vec x(double x);

    double y();

    Vec y(double y);

    double z();

    Vec z(double z);


    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    default double length() {
        return Math.sqrt(Math.sq(x()) + Math.sq(y()) + Math.sq(z()));
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    default double lengthSquared() {
        return Math.sq(x()) + Math.sq(y()) + Math.sq(z());
    }

    /**
     * Get the distance between this vector and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the vector's magnitude. NaN will be
     * returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @param o The other vector
     * @return the distance
     */
    default double distance(Vec o) {
        return Math.sqrt(Math.sq(x() - o.x()) + Math.sq(y() - o.y()) + Math.sq(z() - o.z()));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o The other vector
     * @return the distance
     */
    default double distanceSquared(Vec o) {
        return Math.sq(x() - o.x()) + Math.sq(y() - o.y()) + Math.sq(z() - o.z());
    }

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param other The other vector
     * @return angle in radians
     */
    default float angle(Vec other) {
        double dot = Math.constrainToRange(dot(other) / (length() * other.length()), -1.0, 1.0);

        return (float) Math.acos(dot);
    }

    /**
     * Calculates the dot product of this vector with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param other The other vector
     * @return dot product
     */
    default double dot(Vec other) {
        return x() * other.x() + y() * other.y() + z() * other.z();
    }


    /**
     * Sets this vector to the midpoint between this vector and another.
     *
     * @param other The other vector
     * @return this same vector (now a midpoint)
     */
    default Vec midpoint(Vec other) {
        x((x() + other.x()) / 2);
        y((y() + other.y()) / 2);
        z((z() + other.z()) / 2);
        return this;
    }

    /**
     * Gets a new midpoint vector between this vector and another.
     *
     * @param other The other vector
     * @return a new midpoint vector
     */
    default Vec getMidpoint(Vec other) {
        double x = (this.x() + other.x()) / 2;
        double y = (this.y() + other.y()) / 2;
        double z = (this.z() + other.z()) / 2;
        return new VecImpl3(x, y, z);
    }

    /**
     * Calculates the cross product of this vector with another. The cross
     * product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param o The other vector
     * @return the same vector
     */
    default Vec crossProduct(Vec o) {
        double newX = y() * o.z() - o.y() * z();
        double newY = z() * o.x() - o.z() * x();
        double newZ = x() * o.y() - o.x() * y();

        return x(newX).y(newY).z(newZ);
    }

    /**
     * Calculates the cross product of this vector with another without mutating
     * the original. The cross product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param o The other vector
     * @return a new vector
     */
    default Vec getCrossProduct(Vec o) {
        double x = this.y() * o.z() - o.y() * this.z();
        double y = this.z() * o.x() - o.z() * this.x();
        double z = this.x() * o.y() - o.x() * this.y();
        return new VecImpl3(x, y, z);
    }

    /**
     * Converts this vector to a unit vector (a vector with length of 1).
     *
     * @return the same vector
     */
    default Vec normalize() {
        return divide(length());
    }

    /**
     * Zero this vector's components.
     *
     * @return the same vector
     */
    default Vec zero() {
        return x(0).y(0).z(0);
    }

    /**
     * Converts each component of value <code>-0.0</code> to <code>0.0</code>.
     *
     * @return This vector.
     */
    default Vec normalizeZeros() {
        if (x() == -0.0D) {
            x(0);
        }
        if (y() == -0.0D) {
            y(0);
        }
        if (z() == -0.0D) {
            z(0);
        }
        return this;
    }


    /**
     * Returns whether this vector is in an axis-aligned bounding box.
     * <p>
     * The minimum and maximum vectors given must be truly the minimum and
     * maximum X, Y and Z components.
     *
     * @param min Minimum vector
     * @param max Maximum vector
     * @return whether this vector is in the AABB
     */
    default boolean isInAABB(Vec min, Vec max) {
        return x() >= min.x() && x() <= max.x() && y() >= min.y() && y() <= max.y() && z() >= min.z() && z() <= max.z();
    }

    /**
     * Returns whether this vector is within a sphere.
     *
     * @param origin Sphere origin.
     * @param radius Sphere radius
     * @return whether this vector is in the sphere
     */
    default boolean isInSphere(Vec origin, double radius) {
        return (Math.sq(origin.x() - x()) + Math.sq(origin.y() - y()) + Math.sq(origin.z() - z())) <= Math.sq(radius);
    }

    /**
     * Returns if a vector is normalized
     *
     * @return whether the vector is normalised
     */
    default boolean isNormalized() {
        return Math.abs(this.lengthSquared() - 1) < getEpsilon();
    }

    /**
     * Get the threshold used for equals().
     *
     * @return The epsilon.
     */
    static double getEpsilon() {
        return epsilon;
    }

    /**
     * Rotates the vector around the x axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return the same vector
     */
    default Vec rotateAroundX(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double y = angleCos * y() - angleSin * z();
        double z = angleSin * y() + angleCos * z();
        return y(y).z(z);
    }

    /**
     * Rotates the vector around the y axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return the same vector
     */
    default Vec rotateAroundY(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() + angleSin * z();
        double z = -angleSin * x() + angleCos * z();
        return x(x).z(z);
    }

    /**
     * Rotates the vector around the z axis
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return the same vector
     */
    default Vec rotateAroundZ(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * x() - angleSin * y();
        double y = angleSin * x() + angleCos * y();
        return x(x).y(y);
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * This method will always make sure the provided axis is a unit vector, to
     * not modify the length of the vector when rotating. If you are experienced
     * with the scaling of a non-unit axis vector, you can use
     *
     * @param axis  the axis to rotate the vector around. If the passed vector is
     *              not of length 1, it gets copied and normalized before using it for the
     *              passing it to this method
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     *                                  null
     */
    default Vec rotateAroundAxis(Vec axis, double angle) throws IllegalArgumentException {
        return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.copy().normalize(), angle);
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * Note that the vector length will change accordingly to the axis vector
     * length. If the provided axis is not a unit vector, the rotated vector
     * will not have its previous length. The scaled length of the resulting
     * vector will be related to the axis vector. If you are not perfectly sure
     * about the scaling of the vector, use
     *
     * @param axis  the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     *                                  null
     */
    default Vec rotateAroundNonUnitAxis(Vec axis, double angle) throws IllegalArgumentException {
        double x = x(), y = y(), z = z();
        double x2 = axis.x(), y2 = axis.y(), z2 = axis.z();

        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double dotProduct = this.dot(axis);

        double xPrime = x2 * dotProduct * (1d - cosTheta)
                + x * cosTheta
                + (-z2 * y + y2 * z) * sinTheta;
        double yPrime = y2 * dotProduct * (1d - cosTheta)
                + y * cosTheta
                + (z2 * x - x2 * z) * sinTheta;
        double zPrime = z2 * dotProduct * (1d - cosTheta)
                + z * cosTheta
                + (-y2 * x + x2 * y) * sinTheta;

        return x(xPrime).y(yPrime).z(zPrime);
    }

    default Vec floor() {
        return x(Math.floor(x())).y(Math.floor(y())).z(Math.floor(z()));
    }

    default Vec ceil() {
        return x(Math.ceil(x())).y(Math.ceil(y())).z(Math.ceil(z()));
    }

    default Vec floorDiv(int m) {
        return floor().x(Math.floorDiv((int) x(), m))
                .y(Math.floorDiv((int) y(), m))
                .z(Math.floorDiv((int) z(), m));
    }

    static Vec random(Random random) {
        return new VecImpl3(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    static Vec random() {
        return random(Random.r());
    }
}
