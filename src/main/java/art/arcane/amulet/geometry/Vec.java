package art.arcane.amulet.geometry;

import java.util.Random;

public interface Vec {
    /**
     * Threshold for fuzzy equals().
     */
    static final double epsilon = 0.000001;
    static final Vec ZERO = new VecImpl3(0);

    static Vec of(double x, double y, double z)
    {
        return new VecImpl3(x, y, z);
    }

    static Vec of(int x, int y, int z)
    {
        return new VecImpl3(x, y, z);
    }

    static Vec of(double x)
    {
        return new VecImpl3(x);
    }

    /**
     * Add to this vector
     * @param x +
     * @param y +
     * @param z +
     * @return this (now added) vector
     */
    default Vec add(double x, double y, double z)
    {
        return setX(getX() + x).setY(getY() + y).setZ(getZ() + z);
    }

    default Vec multiply(double mult)
    {
        return multiply(mult, mult, mult);
    }

    default Vec add(double all)
    {
        return add(all, all, all);
    }

    default Vec copy()
    {
        return new VecImpl3(getX(), getY(), getZ());
    }

    default Vec rem(double d)
    {
        return copy().modulus(d);
    }

    default Vec plus(double d)
    {
        return copy().add(d);
    }

    default Vec minus(double d)
    {
        return copy().subtract(d);
    }

    default Vec times(double d)
    {
        return copy().multiply(d);
    }

    default Vec div(double d)
    {
        return copy().divide(d);
    }

    default Vec rem(Vec d)
    {
        return copy().modulus(d);
    }

    default Vec plus(Vec d)
    {
        return copy().add(d);
    }

    default Vec minus(Vec d)
    {
        return copy().subtract(d);
    }

    default Vec times(Vec d)
    {
        return copy().multiply(d);
    }

    default Vec div(Vec d)
    {
        return copy().divide(d);
    }

    default Vec modulus(Vec d) {
        return modulus(d.getX(), d.getY(), d.getZ());
    }

    default Vec divide(Vec d) {
        return divide(d.getX(), d.getY(), d.getZ());
    }

    default Vec multiply(Vec d)
    {
        return multiply(d.getX(), d.getY(), d.getZ());
    }

    default Vec inc()
    {
        return copy().add(1);
    }

    default Vec dec()
    {
        return copy().subtract(1);
    }

    default Vec unaryMinus()
    {
        return copy().reverse();
    }

    default Vec divide(double d)
    {
        return divide(d, d, d);
    }

    default Vec modulus(double d)
    {
        return modulus(d, d, d);
    }

    default Vec subtract(double d)
    {
        return add(-d);
    }

    default Vec multiply(double x, double y, double z)
    {
        return setX(getX() * x).setY(getY() * y).setZ(getZ() * z);
    }

    default Vec divide(double x, double y, double z)
    {
        return setX(getX() / x).setY(getY() / y).setZ(getZ() / z);
    }

    default Vec modulus(double x, double y, double z)
    {
        return setX(getX() % x).setY(getY() % y).setZ(getZ() % z);
    }

    default Vec reverse()
    {
        return setX(-getX()).setY(-getY()).setZ(-getZ());
    }

    default Vec reverseX()
    {
        return setX(-getX());
    }

    default Vec reverseY()
    {
        return setY(-getY());
    }

    default Vec reverseZ()
    {
        return setZ(-getZ());
    }

    default double get(int index)
    {
        if(index == 0)
        {
            return getX();
        }
        else if(index == 1)
        {
            return getY();
        }
        else if(index == 2)
        {
            return getZ();
        }

        else
        {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    default double set(int index, double value)
    {
        if(index == 0)
        {
            setX(value);
            return value;
        }

        else if(index == 1)
        {
            setY(value);
            return value;
        }

        else if(index == 2)
        {
            setZ(value);
            return value;
        }

        else
        {
            throw new IndexOutOfBoundsException("0, 1, or 2 (provided: " + index + ")");
        }
    }

    default Vec subtract(double x, double y, double z)
    {
        return add(-x, -y, -z);
    }

    default Vec add(Vec v)
    {
        return add(v.getX(), v.getY(), v.getZ());
    }

    default Vec subtract(Vec v)
    {
        return subtract(v.getX(), v.getY(), v.getZ());
    }

    double getX();

    Vec setX(double x);

    double getY();

    Vec setY(double y);

    double getZ();

     Vec setZ(double z);


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
        return Math.sqrt(Math.sq(getX()) + Math.sq(getY()) + Math.sq(getZ()));
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    default double lengthSquared() {
        return Math.sq(getX()) + Math.sq(getY()) + Math.sq(getZ());
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
        return Math.sqrt(Math.sq(getX() - o.getX()) + Math.sq(getY() - o.getY()) + Math.sq(getZ() - o.getZ()));
    }

    /**
     * Get the squared distance between this vector and another.
     *
     * @param o The other vector
     * @return the distance
     */
    default double distanceSquared(Vec o) {
        return Math.sq(getX() - o.getX()) + Math.sq(getY() - o.getY()) + Math.sq(getZ() - o.getZ());
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
        return getX() * other.getX() + getY() * other.getY() + getZ() * other.getZ();
    }


    /**
     * Sets this vector to the midpoint between this vector and another.
     *
     * @param other The other vector
     * @return this same vector (now a midpoint)
     */
    default Vec midpoint(Vec other) {
        setX((getX() + other.getX()) / 2);
        setY((getY() + other.getY()) / 2);
        setZ((getZ() + other.getZ()) / 2);
        return this;
    }

    /**
     * Gets a new midpoint vector between this vector and another.
     *
     * @param other The other vector
     * @return a new midpoint vector
     */
    default Vec getMidpoint(Vec other) {
        double x = (this.getX() + other.getX()) / 2;
        double y = (this.getY() + other.getY()) / 2;
        double z = (this.getZ() + other.getZ()) / 2;
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
        double newX = getY() * o.getZ() - o.getY() * getZ();
        double newY = getZ() * o.getX() - o.getZ() * getX();
        double newZ = getX() * o.getY() - o.getX() * getY();

        return setX(newX).setY(newY).setZ(newZ);
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
        double x = this.getY() * o.getZ() - o.getY() * this.getZ();
        double y = this.getZ() * o.getX() - o.getZ() * this.getX();
        double z = this.getX() * o.getY() - o.getX() * this.getY();
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
        return setX(0).setY(0).setZ(0);
    }

    /**
     * Converts each component of value <code>-0.0</code> to <code>0.0</code>.
     *
     * @return This vector.
     */
    default Vec normalizeZeros() {
        if (getX() == -0.0D) {
            setX(0);
        }
        if (getY() == -0.0D){
            setY(0);
        }
        if (getZ() == -0.0D){
            setZ(0);
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
        return getX() >= min.getX() && getX() <= max.getX() && getY() >= min.getY() && getY() <= max.getY() && getZ() >= min.getZ() && getZ() <= max.getZ();
    }

    /**
     * Returns whether this vector is within a sphere.
     *
     * @param origin Sphere origin.
     * @param radius Sphere radius
     * @return whether this vector is in the sphere
     */
    default boolean isInSphere(Vec origin, double radius) {
        return (Math.sq(origin.getX() - getX()) + Math.sq(origin.getY() - getY()) + Math.sq(origin.getZ() - getZ())) <= Math.sq(radius);
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
     * in radians
     * @return the same vector
     */
    default Vec rotateAroundX(double angle) {
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
    default Vec rotateAroundY(double angle) {
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
    default Vec rotateAroundZ(double angle) {
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
     * @param axis the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return the same vector
     * @throws IllegalArgumentException if the provided axis vector instance is
     * null
     */
    default Vec rotateAroundNonUnitAxis(Vec axis, double angle) throws IllegalArgumentException {
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

    default Vec floor()
    {
        return setX(Math.floor(getX())).setY(Math.floor(getY())).setZ(Math.floor(getZ()));
    }

    default Vec ceil()
    {
        return setX(Math.ceil(getX())).setY(Math.ceil(getY())).setZ(Math.ceil(getZ()));
    }

    default Vec floorDiv(int m)
    {
        return floor().setX(Math.floorDiv((int) getX(), m))
                .setY(Math.floorDiv((int) getY(), m))
                .setZ(Math.floorDiv((int) getZ(), m));
    }

    static Vec random(Random random) {
        return new VecImpl3(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    static Vec random() {
        return random(Random.r());
    }
}
