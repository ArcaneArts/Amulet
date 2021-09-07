package Amulet.extensions.java.lang.Math;

import manifold.ext.rt.api.Extension;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;

@Extension
public class XMath {
    private static final int precision = 128;
    private static final int modulus = 360 * precision;
    private static final float[] sin = new float[modulus];
    public static int tick = 0;

    /**
     * Scales B by an external range change so that <br/>
     * <br/>
     * BMIN < B < BMAX <br/>
     * AMIN < RESULT < AMAX <br/>
     * <br/>
     * So Given rangeScale(0, 20, 0, 10, 5) -> 10 <br/>
     * 0 < 5 < 10 <br/>
     * 0 < ? < 20 <br/>
     * <br/>
     * would return 10
     *
     * @param amin the resulting minimum
     * @param amax the resulting maximum
     * @param bmin the initial minimum
     * @param bmax the initial maximum
     * @param b    the initial value
     * @return the resulting value
     */
    @Extension
    public static double rangeScale(double amin, double amax, double bmin, double bmax, double b) {
        return amin + ((amax - amin) * ((b - bmin) / (bmax - bmin)));
    }

    /**
     * Get the percent (inverse lerp) from "from" to "to" where "at".
     * <p>
     * If from = 0 and to = 100 and at = 25 then it would return 0.25
     *
     * @param from the from
     * @param to   the to
     * @param at   the at
     * @return the percent
     */
    @Extension
    public static double lerpInverse(double from, double to, double at) {
        return Math.rangeScale(0, 1, from, to, at);
    }

    /**
     * Linear interpolation from a to b where f is the percent across
     *
     * @param a the first pos (0)
     * @param b the second pos (1)
     * @param f the percent
     * @return the value
     */
    @Extension
    public static double lerp(double a, double b, double f) {
        return a + (f * (b - a));
    }

    /**
     * Bilinear interpolation
     *
     * @param a the first point (0, 0)
     * @param b the second point (1, 0)
     * @param c the third point (0, 1)
     * @param d the fourth point (1, 1)
     * @return the bilerped value
     */
    @Extension
    public static double bilerp(double a, double b, double c, double d, double x, double y) {
        return lerp(lerp(a, b, x), lerp(c, d, x), y);
    }

    /**
     * Trilinear interpolation
     *
     * @param a the first point (0, 0, 0)
     * @param b the second point (1, 0, 0)
     * @param c the third point (0, 0, 1)
     * @param d the fourth point (1, 0, 1)
     * @param e the fifth point (0, 1, 0)
     * @param f the sixth point (1, 1, 0)
     * @param g the seventh point (0, 1, 1)
     * @param h the eighth point (1, 1, 1)
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the trilerped value
     */
    @Extension
    public static double trilerp(double a, double b, double c, double d, double e, double f, double g, double h, double x, double y, double z) {
        return lerp(bilerp(a, b, c, d, x, y), bilerp(e, f, g, h, x, y), z);
    }

    /**
     * Clip a value
     *
     * @param value the value
     * @param min   the min
     * @param max   the max
     * @return the clipped value
     */
    @Extension
    public static double dclip(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * Get true or false based on random percent
     *
     * @param d between 0 and 1
     * @return true if true
     */
    @Extension
    public static boolean r(Double d) {
        //noinspection ReplaceNullCheck
        if (d == null) {
            return Math.random() < 0.5;
        }

        return Math.random() < d;
    }

    /**
     * Get the ticks per second from a time in nanoseconds, the rad can be used for
     * multiple ticks
     *
     * @param ns  the time in nanoseconds
     * @param rad the radius of the time
     * @return the ticks per second in double form
     */
    @Extension
    public static double tps(long ns, int rad) {
        return (20.0 * (ns / 50000000.0)) / rad;
    }

    /**
     * Get the number of ticks from a time in nanoseconds
     *
     * @param ns the nanoseconds
     * @return the amount of ticks
     */
    @Extension
    public static double ticksFromNS(long ns) {
        return (ns / 50000000.0);
    }


    /**
     * Get system Nanoseconds
     *
     * @return nanoseconds (current)
     */
    @Extension
    public static long ns() {
        return System.nanoTime();
    }

    /**
     * Get the current millisecond time
     *
     * @return milliseconds
     */
    @Extension
    public static long ms() {
        return System.currentTimeMillis();
    }

    /**
     * Evaluates an expression using javascript engine and returns the double
     * result. This can take variable parameters, so you need to define them.
     * Parameters are defined as $[0-9]. For example evaluate("4$0/$1", 1, 2); This
     * makes the expression (4x1)/2 == 2. Keep note that you must use 0-9, you
     * cannot skip, or start at a number other than 0.
     *
     * @param expression the expression with variables
     * @param args       the arguments/variables
     * @return the resulting double value
     * @throws ScriptException           ... gg
     * @throws IndexOutOfBoundsException learn to count
     */
    @Extension
    public static double evaluate(String expression, Double... args) throws ScriptException, IndexOutOfBoundsException {
        for (int i = 0; i < args.length; i++) {
            String current = "$" + i;

            if (expression.contains(current)) {
                expression = expression.replaceAll(Matcher.quoteReplacement(current), args[i] + "");
            }
        }

        return evaluate(expression);
    }

    /**
     * Evaluates an expression using javascript engine and returns the double
     *
     * @param expression the mathimatical expression
     * @return the double result
     * @throws ScriptException ... gg
     */
    @Extension
    public static double evaluate(String expression) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");

        return Double.parseDouble(scriptEngine.eval(expression).toString());
    }

    /**
     * is the number "is" within from-to
     *
     * @param from the lower end
     * @param to   the upper end
     * @param is   the check
     * @return true if its within
     */
    @Extension
    public static boolean within(int from, int to, int is) {
        return is >= from && is <= to;
    }

    /**
     * Get the amount of days past since the epoch time (1970 jan 1 utc)
     *
     * @return the epoch days
     */
    @Extension
    public static long epochDays() {
        return epochDays(ms());
    }

    /**
     * Get the amount of days past since the epoch time (1970 jan 1 utc)
     *
     * @param ms the time in milliseconds
     * @return the epoch days
     */
    @Extension
    public static long epochDays(long ms) {
        return ms / 1000 / 60 / 60 / 24;
    }

    static {
        for (int i = 0; i < sin.length; i++) {
            sin[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
        }
    }

    @Extension
    public static double constrainToRange(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    @Extension
    public static double sq(double a) {
        return a * a;
    }

    @Extension
    public static int sq(int a) {
        return a * a;
    }

    @Extension
    public static float sq(float a) {
        return a * a;
    }

    @Extension
    public static float sinLookup(int a) {
        return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
    }

    @Extension
    public static boolean interval(int tickInterval) {
        return tick % (tickInterval <= 0 ? 1 : tickInterval) == 0;
    }
}
