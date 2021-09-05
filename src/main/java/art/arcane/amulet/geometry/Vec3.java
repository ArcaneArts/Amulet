package art.arcane.amulet.geometry;

import java.util.Objects;
import java.util.Random;

public class Vec3 {
    /**
     * Threshold for fuzzy equals().
     */
    private static final double epsilon = 0.000001;
    public static final Vec3 ZERO = new Vec3(0);
    private double x;
    private double y;
    private double z;

    /**
     * Create a new vector
     */
    public Vec3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector
     */
    public Vec3(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector v,v,v
     */
    public Vec3(double v)
    {
        this(v,v,v);
    }

    /**
     * Create a new vector 0,0,0
     */
    public Vec3()
    {
        this(0);
    }

    /**
     * Add to this vector
     * @param x +
     * @param y +
     * @param z +
     * @return this (now added) vector
     */
    public Vec3 add(double x, double y, double z)
    {
        this.x +=x;
        this.y+=y;
        this.z+=z;

        return this;
    }

    public Vec3 multiply(double mult)
    {
        return multiply(mult, mult, mult);
    }

    public Vec3 add(double all)
    {
        return add(all, all, all);
    }

    public Vec3 copy()
    {
        return new Vec3(x, y, z);
    }

    public Vec3 rem(double d)
    {
        return copy().modulus(d);
    }

    public Vec3 plus(double d)
    {
        return copy().add(d);
    }

    public Vec3 minus(double d)
    {
        return copy().subtract(d);
    }

    public Vec3 times(double d)
    {
        return copy().multiply(d);
    }

    public Vec3 div(double d)
    {
        return copy().divide(d);
    }

    public Vec3 rem(Vec3 d)
    {
        return copy().modulus(d);
    }

    public Vec3 plus(Vec3 d)
    {
        return copy().add(d);
    }

    public Vec3 minus(Vec3 d)
    {
        return copy().subtract(d);
    }

    public Vec3 times(Vec3 d)
    {
        return copy().multiply(d);
    }

    public Vec3 div(Vec3 d)
    {
        return copy().divide(d);
    }

    public Vec3 modulus(Vec3 d) {
        return modulus(d.x, d.y, d.z);
    }

    public Vec3 divide(Vec3 d) {
        return divide(d.x, d.y, d.z);
    }

    public Vec3 multiply(Vec3 d)
    {
        return multiply(d.x, d.y, d.z);
    }

    public Vec3 inc()
    {
        return copy().add(1);
    }

    public Vec3 dec()
    {
        return copy().subtract(1);
    }

    public Vec3 unaryMinus()
    {
        return copy().reverse();
    }

    public Vec3 divide(double d)
    {
        return divide(d, d, d);
    }

    public Vec3 modulus(double d)
    {
        return modulus(d, d, d);
    }

    public Vec3 subtract(double d)
    {
        return add(-d);
    }

    public Vec3 multiply(double x, double y, double z)
    {
        this.x *=x;
        this.y *=y;
        this.z *=z;

        return this;
    }

    public Vec3 divide(double x, double y, double z)
    {
        this.x /=x;
        this.y /=y;
        this.z /=z;

        return this;
    }

    public Vec3 modulus(double x, double y, double z)
    {
        this.x %=x;
        this.y %=y;
        this.z %=z;

        return this;
    }

    public Vec3 reverse()
    {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vec3 reverseX()
    {
        x = -x;
        return this;
    }

    public Vec3 reverseY()
    {
        y = -y;
        return this;
    }

    public Vec3 reverseZ()
    {
        z = -z;
        return this;
    }

    public double get(int index)
    {
        if(index == 0)
        {
            return x;
        }
        else if(index == 1)
        {
            return y;
        }
        else if(index == 2)
        {
            return z;
        }

        else
        {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    public double set(int index, double value)
    {
        if(index == 0)
        {
            x = value;
            return value;
        }

        else if(index == 1)
        {
            y = value;
            return value;
        }

        else if(index == 2)
        {
            z = value;
            return value;
        }

        else
        {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    public Vec3 subtract(double x, double y, double z)
    {
        return add(-x, -y, -z);
    }

    public Vec3 add(Vec3 v)
    {
        return add(v.x, v.y, v.z);
    }

    public Vec3 subtract(Vec3 v)
    {
        return subtract(v.x, v.y, v.z);
    }

    public double getX() {
        return x;
    }

    public Vec3 setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Vec3 setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return z;
    }

    public Vec3 setZ(double z) {
        this.z = z;
        return this;
    }


    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    public double length() {
        return Math.sqrt(Math.sq(x) + Math.sq(y) + Math.sq(z));
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    public double lengthSquared() {
        return Math.sq(x) + Math.sq(y) + Math.sq(z);
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
    public double distance(Vec3 o) {
        return Math.sqrt(Math.sq(x - o.x) + Math.sq(y - o.y) + Math.sq(z - o.z));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o The other vector
     * @return the distance
     */
    public double distanceSquared(Vec3 o) {
        return Math.sq(x - o.x) + Math.sq(y - o.y) + Math.sq(z - o.z);
    }

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param other The other vector
     * @return angle in radians
     */
    public float angle(Vec3 other) {
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
    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }


    /**
     * Sets this vector to the midpoint between this vector and another.
     *
     * @param other The other vector
     * @return this same vector (now a midpoint)
     */
    public Vec3 midpoint(Vec3 other) {
        x = (x + other.x) / 2;
        y = (y + other.y) / 2;
        z = (z + other.z) / 2;
        return this;
    }

    /**
     * Gets a new midpoint vector between this vector and another.
     *
     * @param other The other vector
     * @return a new midpoint vector
     */
    public Vec3 getMidpoint(Vec3 other) {
        double x = (this.x + other.x) / 2;
        double y = (this.y + other.y) / 2;
        double z = (this.z + other.z) / 2;
        return new Vec3(x, y, z);
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
    public Vec3 crossProduct(Vec3 o) {
        double newX = y * o.z - o.y * z;
        double newY = z * o.x - o.z * x;
        double newZ = x * o.y - o.x * y;

        x = newX;
        y = newY;
        z = newZ;
        return this;
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
    public Vec3 getCrossProduct(Vec3 o) {
        double x = this.y * o.z - o.y * this.z;
        double y = this.z * o.x - o.z * this.x;
        double z = this.x * o.y - o.x * this.y;
        return new Vec3(x, y, z);
    }

    /**
     * Converts this vector to a unit vector (a vector with length of 1).
     *
     * @return the same vector
     */
    public Vec3 normalize() {
        double length = length();

        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    /**
     * Zero this vector's components.
     *
     * @return the same vector
     */
    public Vec3 zero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }

    /**
     * Converts each component of value <code>-0.0</code> to <code>0.0</code>.
     *
     * @return This vector.
     */
    public Vec3 normalizeZeros() {
        if (x == -0.0D) x = 0.0D;
        if (y == -0.0D) y = 0.0D;
        if (z == -0.0D) z = 0.0D;
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
    public boolean isInAABB(Vec3 min, Vec3 max) {
        return x >= min.x && x <= max.x && y >= min.y && y <= max.y && z >= min.z && z <= max.z;
    }

    /**
     * Returns whether this vector is within a sphere.
     *
     * @param origin Sphere origin.
     * @param radius Sphere radius
     * @return whether this vector is in the sphere
     */
    public boolean isInSphere(Vec3 origin, double radius) {
        return (Math.sq(origin.x - x) + Math.sq(origin.y - y) + Math.sq(origin.z - z)) <= Math.sq(radius);
    }

    /**
     * Returns if a vector is normalized
     *
     * @return whether the vector is normalised
     */
    public boolean isNormalized() {
        return Math.abs(this.lengthSquared() - 1) < getEpsilon();
    }

    /**
     * Get the threshold used for equals().
     *
     * @return The epsilon.
     */
    public static double getEpsilon() {
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
     * in radians
     * @return the same vector
     */
    public Vec3 rotateAroundX(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double y = angleCos * getY() - angleSin * getZ();
        double z = angleSin * getY() + angleCos * getZ();
        return setY(y).setZ(z);
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
     * in radians
     * @return the same vector
     */
    public Vec3 rotateAroundY(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * getX() + angleSin * getZ();
        double z = -angleSin * getX() + angleCos * getZ();
        return setX(x).setZ(z);
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
     * in radians
     * @return the same vector
     */
    public Vec3 rotateAroundZ(double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);

        double x = angleCos * getX() - angleSin * getY();
        double y = angleSin * getX() + angleCos * getY();
        return setX(x).setY(y);
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
     * @param axis the axis to rotate the vector around. If the passed vector is
     * not of length 1, it gets copied and normalized before using it for the
     * passing it to this method
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     * null
     */
    public Vec3 rotateAroundAxis(Vec3 axis, double angle) throws IllegalArgumentException {
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
     * @param axis the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     * null
     */
    public Vec3 rotateAroundNonUnitAxis(Vec3 axis, double angle) throws IllegalArgumentException {
        double x = getX(), y = getY(), z = getZ();
        double x2 = axis.getX(), y2 = axis.getY(), z2 = axis.getZ();

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

        return setX(xPrime).setY(yPrime).setZ(zPrime);
    }

    public Vec3 floor()
    {
        return setX(Math.floor(x)).setY(Math.floor(y)).setZ(Math.floor(z));
    }

    public Vec3 ceil()
    {
        return setX(Math.ceil(x)).setY(Math.ceil(y)).setZ(Math.ceil(z));
    }

    public Vec3 floorDiv(int m)
    {
        return floor().setX(Math.floorDiv((int) x, m))
                .setY(Math.floorDiv((int) y, m))
                .setZ(Math.floorDiv((int) z, m));
    }

    public String toString()
    {
        return "[" + x + "," + y + "," + z + "]";
    }

    public static Vec3 random(Random random) {
        return new Vec3(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public static Vec3 random() {
        return random(Random.r());
    }

    public int hashCode()
    {
        return Objects.hash(x, y, z);
    }

    public boolean equals(Object other)
    {
        if(other instanceof Vec3 v)
        {
            return v.x == x && v.y == y && v.z == z;
        }

        return false;
    }
}
