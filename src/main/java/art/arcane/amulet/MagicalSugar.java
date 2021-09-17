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

package art.arcane.amulet;

import art.arcane.amulet.geometry.Vec;
import art.arcane.amulet.io.IO;
import art.arcane.amulet.range.AbstractIterableRange;
import art.arcane.amulet.range.AbstractRange;
import art.arcane.amulet.range.BigDecimalRange;
import art.arcane.amulet.range.BigIntegerRange;
import art.arcane.amulet.range.ComparableRange;
import art.arcane.amulet.range.DoubleRange;
import art.arcane.amulet.range.IntegerRange;
import art.arcane.amulet.range.LongRange;
import art.arcane.amulet.range.Sequential;
import art.arcane.amulet.range.SequentialRange;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("unused")
public class MagicalSugar {
    public static final Gson gson = new Gson();

    /**
     * Create a vector & angle amount
     */
    public static final _MAGIC_Vec_Angle angle = _MAGIC_Vec_Angle.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_Angle {
        private static final _MAGIC_Vec_Angle INSTANCE = new _MAGIC_Vec_Angle();

        private static _MAGIC_Vec_Angle instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec angle) {
            return new __FROM(angle);
        }

        public record __FROM(Vec angle) {
            public __FROM_DUAL prefixBind(double a) {
                return new __FROM_DUAL(angle, a);
            }
        }

        public record __FROM_DUAL(Vec start, double a) {
        }
    }

    /**
     * Rotate a vector around a unit vector axis/amount
     */
    public static final _MAGIC_Vec_RT rotate = _MAGIC_Vec_RT.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_RT {
        private static final _MAGIC_Vec_RT INSTANCE = new _MAGIC_Vec_RT();

        private static _MAGIC_Vec_RT instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(_MAGIC_Vec_Angle.__FROM_DUAL r) {
                return start.copy().rotateAroundAxis(r.start, r.a);
            }
        }
    }

    /**
     * Rotate a vector around the x axis
     */
    public static final _MAGIC_Vec_RTX rotateX = _MAGIC_Vec_RTX.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_RTX {
        private static final _MAGIC_Vec_RTX INSTANCE = new _MAGIC_Vec_RTX();

        private static _MAGIC_Vec_RTX instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(double r) {
                return start.copy().rotateAroundX(r);
            }
        }
    }

    /**
     * Rotate a vector around the y axis
     */
    public static final _MAGIC_Vec_RTY rotateY = _MAGIC_Vec_RTY.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_RTY {
        private static final _MAGIC_Vec_RTY INSTANCE = new _MAGIC_Vec_RTY();

        private static _MAGIC_Vec_RTY instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(double r) {
                return start.copy().rotateAroundY(r);
            }
        }
    }

    /**
     * Rotate a vector around the z axis
     */
    public static final _MAGIC_Vec_RTZ rotateZ = _MAGIC_Vec_RTZ.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_RTZ {
        private static final _MAGIC_Vec_RTZ INSTANCE = new _MAGIC_Vec_RTZ();

        private static _MAGIC_Vec_RTZ instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(double r) {
                return start.copy().rotateAroundZ(r);
            }
        }
    }

    /**
     * Dot product (same as v1.dot(v2))
     */
    public static final _MAGIC_Vec_Dot dot = _MAGIC_Vec_Dot.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_Dot {
        private static final _MAGIC_Vec_Dot INSTANCE = new _MAGIC_Vec_Dot();

        private static _MAGIC_Vec_Dot instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public double prefixBind(Vec into) {
                return start.dot(into);
            }
        }
    }

    /**
     * a min b == Math.min(a, b)
     */
    public static final _MAGIC_Math_Min min = _MAGIC_Math_Min.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Min {
        private static final _MAGIC_Math_Min INSTANCE = new _MAGIC_Math_Min();

        private static _MAGIC_Math_Min instance() {
            return INSTANCE;
        }

        public __FROM_Integer postfixBind(int from) {
            return new __FROM_Integer(from);
        }

        public record __FROM_Integer(int start) {
            public int prefixBind(int into) {
                return Math.min(start, into);
            }
        }

        public __FROM_Long postfixBind(long from) {
            return new __FROM_Long(from);
        }

        public record __FROM_Long(long start) {
            public long prefixBind(long into) {
                return Math.min(start, into);
            }
        }

        public __FROM_Double postfixBind(double from) {
            return new __FROM_Double(from);
        }

        public record __FROM_Double(double start) {
            public double prefixBind(double into) {
                return Math.min(start, into);
            }
        }
    }

    /**
     * a min b == Math.min(a, b)
     */
    public static final _MAGIC_Math_Min_Float min_ = _MAGIC_Math_Min_Float.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Min_Float {
        private static final _MAGIC_Math_Min_Float INSTANCE = new _MAGIC_Math_Min_Float();

        private static _MAGIC_Math_Min_Float instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(float from) {
            return new __FROM(from);
        }

        public record __FROM(float start) {
            public float prefixBind(float into) {
                return Math.min(start, into);
            }
        }
    }

    /**
     * a min b == Math.min(a, b)
     */
    public static final _MAGIC_Math_Min_Int min__ = _MAGIC_Math_Min_Int.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Min_Int {
        private static final _MAGIC_Math_Min_Int INSTANCE = new _MAGIC_Math_Min_Int();

        private static _MAGIC_Math_Min_Int instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(int from) {
            return new __FROM(from);
        }

        public record __FROM(int start) {
            public int prefixBind(int into) {
                return Math.min(start, into);
            }
        }
    }

    /**
     * a min b == Math.min(a, b)
     */
    public static final _MAGIC_Math_Min_Long min___ = _MAGIC_Math_Min_Long.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Min_Long {
        private static final _MAGIC_Math_Min_Long INSTANCE = new _MAGIC_Math_Min_Long();

        private static _MAGIC_Math_Min_Long instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(long from) {
            return new __FROM(from);
        }

        public record __FROM(long start) {
            public long prefixBind(long into) {
                return Math.min(start, into);
            }
        }
    }

    /**
     * a max b == Math.max(a, b)
     */
    public static final _MAGIC_Math_Max_Double max = _MAGIC_Math_Max_Double.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Max_Double {
        private static final _MAGIC_Math_Max_Double INSTANCE = new _MAGIC_Math_Max_Double();

        private static _MAGIC_Math_Max_Double instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(double from) {
            return new __FROM(from);
        }

        public record __FROM(double start) {
            public double prefixBind(double into) {
                return Math.max(start, into);
            }
        }
    }

    /**
     * range lerp double percent
     */
    public static final _MAGIC_Math_Lerp lerp = _MAGIC_Math_Lerp.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Lerp {
        private static final _MAGIC_Math_Lerp INSTANCE = new _MAGIC_Math_Lerp();

        private static _MAGIC_Math_Lerp instance() {
            return INSTANCE;
        }

        public __FROM_Double postfixBind(DoubleRange from) {
            return new __FROM_Double(from);
        }

        public record __FROM_Double(DoubleRange start) {
            public double prefixBind(double into) {
                return Math.lerp(start.getLeftEndpoint(), start.getRightEndpoint(), into);
            }
        }

        public __FROM_Integer postfixBind(IntegerRange from) {
            return new __FROM_Integer(from);
        }

        public record __FROM_Integer(IntegerRange start) {
            public double prefixBind(double into) {
                return Math.lerp(start.getLeftEndpoint(), start.getRightEndpoint(), into);
            }
        }
    }

    /**
     * a max b == Math.max(a, b)
     */
    public static final _MAGIC_Math_Max_Float max_ = _MAGIC_Math_Max_Float.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Max_Float {
        private static final _MAGIC_Math_Max_Float INSTANCE = new _MAGIC_Math_Max_Float();

        private static _MAGIC_Math_Max_Float instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(float from) {
            return new __FROM(from);
        }

        public record __FROM(float start) {
            public float prefixBind(float into) {
                return Math.max(start, into);
            }
        }
    }

    /**
     * a max b == Math.max(a, b)
     */
    public static final _MAGIC_Math_Max_Int max__ = _MAGIC_Math_Max_Int.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Max_Int {
        private static final _MAGIC_Math_Max_Int INSTANCE = new _MAGIC_Math_Max_Int();

        private static _MAGIC_Math_Max_Int instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(int from) {
            return new __FROM(from);
        }

        public record __FROM(int start) {
            public int prefixBind(int into) {
                return Math.max(start, into);
            }
        }
    }

    /**
     * a max b == Math.max(a, b)
     */
    public static final _MAGIC_Math_Max_Long max___ = _MAGIC_Math_Max_Long.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Max_Long {
        private static final _MAGIC_Math_Max_Long INSTANCE = new _MAGIC_Math_Max_Long();

        private static _MAGIC_Math_Max_Long instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(long from) {
            return new __FROM(from);
        }

        public record __FROM(long start) {
            public long prefixBind(long into) {
                return Math.max(start, into);
            }
        }
    }

    /**
     * String split
     */
    public static final _MAGIC_String_Split split = _MAGIC_String_Split.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_String_Split {
        private static final _MAGIC_String_Split INSTANCE = new _MAGIC_String_Split();

        private static _MAGIC_String_Split instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(String from) {
            return new __FROM(from);
        }

        public record __FROM(String start) {
            public List<String> prefixBind(String into) {
                return List.of(start.splitAbs(into));
            }
        }
    }

    /**
     * String without (replaceall)
     */
    public static final _MAGIC_String_Without without = _MAGIC_String_Without.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_String_Without {
        private static final _MAGIC_String_Without INSTANCE = new _MAGIC_String_Without();

        private static _MAGIC_String_Without instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(String from) {
            return new __FROM(from);
        }

        public record __FROM(String start) {
            public String prefixBind(String into) {
                return start.remove(into);
            }
        }
    }

    /**
     * NullableObject ifnull Object, simply return the object, or if it's null, the second object
     */
    public static final _MAGIC_NullCheck_IfNull ifnull = _MAGIC_NullCheck_IfNull.instance();

    /**
     * NullableObject or Object, simply return the object, or if it's null, the second object
     */
    public static final _MAGIC_NullCheck_IfNull or = _MAGIC_NullCheck_IfNull.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_NullCheck_IfNull {
        private static final _MAGIC_NullCheck_IfNull INSTANCE = new _MAGIC_NullCheck_IfNull();

        private static _MAGIC_NullCheck_IfNull instance() {
            return INSTANCE;
        }

        public <T> __FROM<T> postfixBind(T from) {
            return new __FROM<>(from);
        }

        public record __FROM<T>(T start) {
            public T prefixBind(T into) {
                if(start != null)
                {
                    return start;
                }

                return into;
            }
        }
    }


    /**
     * Clip a number to a min and max bound
     */
    public static final _MAGIC_Number_Clip clip = _MAGIC_Number_Clip.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Number_Clip {
        private static final _MAGIC_Number_Clip INSTANCE = new _MAGIC_Number_Clip();

        private static _MAGIC_Number_Clip instance() {
            return INSTANCE;
        }

        public __FROMD postfixBind(double from) {
            return new __FROMD(from);
        }

        public record __FROMD(double start) {
            public double prefixBind(DoubleRange into) {
                double min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                double max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return Math.min(Math.max(start, min), max);
            }
        }

        public __FROMI postfixBind(int from) {
            return new __FROMI(from);
        }

        public record __FROMI(double start) {
            public int prefixBind(IntegerRange into) {
                int min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                int max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return (int) Math.min(Math.max(start, min), max);
            }
        }

        public __FROML postfixBind(long from) {
            return new __FROML(from);
        }

        public record __FROML(long start) {
            public long prefixBind(LongRange into) {
                long min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                long max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return Math.min(Math.max(start, min), max);
            }
        }
    }

    /**
     * Write to files. "text" write file OR "text" write "file.txt" OR outputStream write file
     */
    public static final _MAGIC_IO_WriteAll write = _MAGIC_IO_WriteAll.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_IO_WriteAll {
        private static final _MAGIC_IO_WriteAll INSTANCE = new _MAGIC_IO_WriteAll();

        private static _MAGIC_IO_WriteAll instance() {
            return INSTANCE;
        }

        public __FROM_STRING postfixBind(String start) {
            return new __FROM_STRING(start);
        }

        public __FROM_OUT postfixBind(OutputStream start) {
            return new __FROM_OUT(start);
        }

        public record __FROM_STRING(String start) {
            public Void prefixBind(File into) throws IOException {
                IO.writeAll(into, start);
                return null;
            }
            public Void prefixBind(String into) throws IOException {
                IO.writeAll(new File(into), start);
                return null;
            }
        }

        public record __FROM_OUT(OutputStream start) {
            public Void prefixBind(File into) throws IOException {
                IO.writeAll(into, start);
                return null;
            }
            public Void prefixBind(String into) throws IOException {
                IO.writeAll(new File(into), start);
                return null;
            }
        }
    }

    /**
     * Convert file to FileInputStream
     */
    public static final _MAGIC_IO_Open open = _MAGIC_IO_Open.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_IO_Open {
        private static final _MAGIC_IO_Open INSTANCE = new _MAGIC_IO_Open();

        private static _MAGIC_IO_Open instance() {
            return INSTANCE;
        }

        public FileInputStream prefixBind(File in) throws FileNotFoundException {
            return new FileInputStream(in);
        }

        public FileInputStream prefixBind(String in) throws FileNotFoundException {
            return new FileInputStream(in);
        }
    }

    /**
     * Convert Object to Json using Gson
     */
    public static final _MAGIC_IO_TOJson toJson = _MAGIC_IO_TOJson.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_IO_TOJson {
        private static final _MAGIC_IO_TOJson INSTANCE = new _MAGIC_IO_TOJson();

        private static _MAGIC_IO_TOJson instance() {
            return INSTANCE;
        }

        public String postfixBind(Object in) throws FileNotFoundException {
            return gson.toJson(in);
        }
    }

    /**
     * Convert file to String
     */
    public static final _MAGIC_IO_ReadAll read = _MAGIC_IO_ReadAll.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_IO_ReadAll {
        private static final _MAGIC_IO_ReadAll INSTANCE = new _MAGIC_IO_ReadAll();

        private static _MAGIC_IO_ReadAll instance() {
            return INSTANCE;
        }

        public String prefixBind(File in) throws IOException {
            return IO.readAll(in);
        }

        public String prefixBind(String in) throws IOException {
            return IO.readAll(new File(in));
        }
    }

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Degrees deg = _MAGIC_Convert_Degrees.instance();

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Degrees degrees = _MAGIC_Convert_Degrees.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Convert_Degrees {
        private static final _MAGIC_Convert_Degrees INSTANCE = new _MAGIC_Convert_Degrees();

        private static _MAGIC_Convert_Degrees instance() {
            return INSTANCE;
        }

        public double postfixBind(double rad) {
            return Math.toRadians(rad);
        }
    }

    /**
     * Convert to lower case
     */
    public static final _MAGIC_Convert_Lowercase lc = _MAGIC_Convert_Lowercase.instance();
    /**
     * Convert to lower case
     */
    public static final _MAGIC_Convert_Lowercase lower = _MAGIC_Convert_Lowercase.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Convert_Lowercase {
        private static final _MAGIC_Convert_Lowercase INSTANCE = new _MAGIC_Convert_Lowercase();

        private static _MAGIC_Convert_Lowercase instance() {
            return INSTANCE;
        }

        public String postfixBind(String in) {
            return in.lower();
        }
    }

    /**
     * Convert to uppper case
     */
    public static final _MAGIC_Convert_Uppercase uc = _MAGIC_Convert_Uppercase.instance();
    /**
     * Convert to uppper case
     */
    public static final _MAGIC_Convert_Uppercase upper = _MAGIC_Convert_Uppercase.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Convert_Uppercase {
        private static final _MAGIC_Convert_Uppercase INSTANCE = new _MAGIC_Convert_Uppercase();

        private static _MAGIC_Convert_Uppercase instance() {
            return INSTANCE;
        }

        public String postfixBind(String in) {
            return in.upper();
        }
    }

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Vector vec = _MAGIC_Convert_Vector.instance();

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Vector vector = _MAGIC_Convert_Vector.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Convert_Vector {
        private static final _MAGIC_Convert_Vector INSTANCE = new _MAGIC_Convert_Vector();

        private static _MAGIC_Convert_Vector instance() {
            return INSTANCE;
        }

        public Vec prefixBind(double[] pos) {
            return Vec.of(pos[0], pos[1], pos[2]);
        }

        public Vec prefixBind(List<Double> pos) {
            return Vec.of(pos[0], pos[1], pos[2]);
        }
    }

    /**
     * Convert radians to degrees
     */
    public static final _MAGIC_Convert_Radians rad = _MAGIC_Convert_Radians.instance();

    /**
     * Convert radians to degrees
     */
    public static final _MAGIC_Convert_Radians radians = _MAGIC_Convert_Radians.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Convert_Radians {
        private static final _MAGIC_Convert_Radians INSTANCE = new _MAGIC_Convert_Radians();

        private static _MAGIC_Convert_Radians instance() {
            return INSTANCE;
        }

        public double postfixBind(double deg) {
            return Math.toDegrees(deg);
        }
    }

    /**
     * Cross product (same as v1.crossProduct(v2))
     */
    public static final _MAGIC_Vec_CrossProduct cross = _MAGIC_Vec_CrossProduct.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_CrossProduct {
        private static final _MAGIC_Vec_CrossProduct INSTANCE = new _MAGIC_Vec_CrossProduct();

        private static _MAGIC_Vec_CrossProduct instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(Vec into) {
                return start.crossProduct(into);
            }
        }
    }

    /**
     * Get a unit direction if a direction b, then it is really b.subtract(a).normalize() or (b - a).normalize()
     */
    public static final _MAGIC_Vec_Direction direction = _MAGIC_Vec_Direction.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_Direction {
        private static final _MAGIC_Vec_Direction INSTANCE = new _MAGIC_Vec_Direction();

        private static _MAGIC_Vec_Direction instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(Vec into) {
                return (into - start).normalize();
            }
        }
    }

    /**
     * Get a ray (direction & distance), essentially if a ray b, it is (b - a). Not normalized.
     */
    public static final _MAGIC_Vec_Ray ray = _MAGIC_Vec_Ray.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_Ray {
        private static final _MAGIC_Vec_Ray INSTANCE = new _MAGIC_Vec_Ray();

        private static _MAGIC_Vec_Ray instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public Vec prefixBind(Vec into) {
                return (into - start);
            }
        }
    }


    /**
     * Get the distance between two vectors (a distance b)
     */
    public static final _MAGIC_Vec_Distance distance = _MAGIC_Vec_Distance.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_Distance {
        private static final _MAGIC_Vec_Distance INSTANCE = new _MAGIC_Vec_Distance();

        private static _MAGIC_Vec_Distance instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public double prefixBind(Vec into) {
                return start.distance(into);
            }
        }
    }


    /**
     * Get the squared distance between two vectors (a distanceSq b)
     */
    public static final _MAGIC_Vec_DistanceSquared distanceSq = _MAGIC_Vec_DistanceSquared.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Vec_DistanceSquared {
        private static final _MAGIC_Vec_DistanceSquared INSTANCE = new _MAGIC_Vec_DistanceSquared();

        private static _MAGIC_Vec_DistanceSquared instance() {
            return INSTANCE;
        }

        public __FROM postfixBind(Vec from) {
            return new __FROM(from);
        }

        public record __FROM(Vec start) {
            public double prefixBind(Vec into) {
                return start.distanceSquared(into);
            }
        }
    }

    public static final _MAGIC_RANGE_Closed to = _MAGIC_RANGE_Closed.instance();
    public static final _MAGIC_RANGE_LeftOpen _to = _MAGIC_RANGE_LeftOpen.instance();
    public static final _MAGIC_RANGE_RightOpen to_ = _MAGIC_RANGE_RightOpen.instance();
    public static final _MAGIC_RANGE_Open _to_ = _MAGIC_RANGE_Open.instance();
    public static final _MAGIC_RANGE_Step step = _MAGIC_RANGE_Step.instance();
    public static final _MAGIC_RANGE_Unit unit = _MAGIC_RANGE_Unit.instance();
    public static final _MAGIC_RANGE_Inside inside = _MAGIC_RANGE_Inside.instance();
    public static final _MAGIC_RANGE_Outside outside = _MAGIC_RANGE_Outside.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Closed {
        private static final _MAGIC_RANGE_Closed INSTANCE = new _MAGIC_RANGE_Closed();

        public static _MAGIC_RANGE_Closed instance() {
            return INSTANCE;
        }

        boolean _leftClosed;
        boolean _rightClosed;

        private _MAGIC_RANGE_Closed() {
            _leftClosed = true;
            _rightClosed = true;
        }

        public <E extends Comparable<E>> _MAGIC_RANGE_From_Comp<E> postfixBind(E comparable) {
            return new _MAGIC_RANGE_From_Comp<>(comparable);
        }

        public class _MAGIC_RANGE_From_Comp<E extends Comparable<E>> {
            private final E _start;

            _MAGIC_RANGE_From_Comp(E sequential) {
                _start = sequential;
            }

            public ComparableRange<E> prefixBind(E end) {
                return new ComparableRange<>(_start, end, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public <E extends Sequential<E, S, U>, S, U> _MAGIC_RANGE_From_Seq<E, S, U> postfixBind(E sequential) {
            return new _MAGIC_RANGE_From_Seq<>(sequential);
        }

        public class _MAGIC_RANGE_From_Seq<E extends Sequential<E, S, U>, S, U> {
            private final E _start;

            _MAGIC_RANGE_From_Seq(E sequential) {
                _start = sequential;
            }

            public SequentialRange<E, S, U> prefixBind(E end) {
                return new SequentialRange<>(_start, end, null, null, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public _MAGIC_RANGE_From_BigDecimal postfixBind(BigDecimal bd) {
            return new _MAGIC_RANGE_From_BigDecimal(bd);
        }

        public class _MAGIC_RANGE_From_BigDecimal {
            private final BigDecimal _start;

            _MAGIC_RANGE_From_BigDecimal(BigDecimal sequential) {
                _start = sequential;
            }

            public BigDecimalRange prefixBind(BigDecimal end) {
                return new BigDecimalRange(_start, end, BigDecimal.ONE, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public _MAGIC_RANGE_From_BigInteger postfixBind(BigInteger bd) {
            return new _MAGIC_RANGE_From_BigInteger(bd);
        }

        public class _MAGIC_RANGE_From_BigInteger {
            private final BigInteger _start;

            _MAGIC_RANGE_From_BigInteger(BigInteger sequential) {
                _start = sequential;
            }

            public BigIntegerRange prefixBind(BigInteger end) {
                return new BigIntegerRange(_start, end, BigInteger.ONE, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public _MAGIC_RANGE_From_Double postfixBind(Double bd) {
            return new _MAGIC_RANGE_From_Double(bd);
        }

        public class _MAGIC_RANGE_From_Double {
            private final Double _start;

            _MAGIC_RANGE_From_Double(Double sequential) {
                _start = sequential;
            }

            public DoubleRange prefixBind(Double end) {
                return new DoubleRange(_start, end, 1, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public _MAGIC_RANGE_From_Long postfixBind(Long bd) {
            return new _MAGIC_RANGE_From_Long(bd);
        }

        public class _MAGIC_RANGE_From_Long {
            private final Long _start;

            _MAGIC_RANGE_From_Long(Long sequential) {
                _start = sequential;
            }

            public LongRange prefixBind(Long end) {
                return new LongRange(_start, end, 1, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }

        public _MAGIC_RANGE_From_Integer postfixBind(Integer bd) {
            return new _MAGIC_RANGE_From_Integer(bd);
        }

        public class _MAGIC_RANGE_From_Integer {
            private final Integer _start;

            _MAGIC_RANGE_From_Integer(Integer sequential) {
                _start = sequential;
            }

            public IntegerRange prefixBind(Integer end) {
                return new IntegerRange(_start, end, 1, _leftClosed, _rightClosed, _start.compareTo(end) > 0);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_LeftOpen extends _MAGIC_RANGE_Closed {
        private static final _MAGIC_RANGE_LeftOpen INSTANCE = new _MAGIC_RANGE_LeftOpen();

        public static _MAGIC_RANGE_LeftOpen instance() {
            return INSTANCE;
        }

        private _MAGIC_RANGE_LeftOpen() {
            _leftClosed = false;
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_RightOpen extends _MAGIC_RANGE_Closed {
        private static final _MAGIC_RANGE_RightOpen INSTANCE = new _MAGIC_RANGE_RightOpen();

        public static _MAGIC_RANGE_RightOpen instance() {
            return INSTANCE;
        }

        private _MAGIC_RANGE_RightOpen() {
            _rightClosed = false;
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Open extends _MAGIC_RANGE_Closed {
        private static final _MAGIC_RANGE_Open INSTANCE = new _MAGIC_RANGE_Open();

        public static _MAGIC_RANGE_Open instance() {
            return INSTANCE;
        }

        private _MAGIC_RANGE_Open() {
            _leftClosed = false;
            _rightClosed = false;
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Step {
        private static final _MAGIC_RANGE_Step INSTANCE = new _MAGIC_RANGE_Step();

        public static _MAGIC_RANGE_Step instance() {
            return INSTANCE;
        }

        public <E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> _MAGIC_RANGE_StepRange<E, S, U, RANGE> postfixBind(RANGE range) {
            return new _MAGIC_RANGE_StepRange<>(range);
        }

        @SuppressWarnings("unused")
        public static class _MAGIC_RANGE_StepRange<E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> {
            private final RANGE _range;

            _MAGIC_RANGE_StepRange(RANGE range) {
                _range = range;
            }

            public RANGE prefixBind(S step) {
                return _range.step(step);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Unit {
        private static final _MAGIC_RANGE_Unit INSTANCE = new _MAGIC_RANGE_Unit();

        public static _MAGIC_RANGE_Unit instance() {
            return INSTANCE;
        }

        public <E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> _MAGIC_RANGE_Unit.UnitRange<E, S, U, RANGE> postfixBind(RANGE range) {
            return new _MAGIC_RANGE_Unit.UnitRange<>(range);
        }

        @SuppressWarnings("unused")
        public static class UnitRange<E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> {
            private final RANGE _range;

            UnitRange(RANGE range) {
                _range = range;
            }

            public RANGE prefixBind(U unit) {
                return _range.unit(unit);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Inside {
        private static final _MAGIC_RANGE_Inside INSTANCE = new _MAGIC_RANGE_Inside();

        public static _MAGIC_RANGE_Inside instance() {
            return INSTANCE;
        }

        public <E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> _MAGIC_RANGE_Inside.InsideRange<E, RANGE> prefixBind(RANGE range) {
            return new _MAGIC_RANGE_Inside.InsideRange<>(range);
        }

        @SuppressWarnings("unused")
        public static class InsideRange<E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> {
            private final RANGE _range;

            InsideRange(RANGE range) {
                _range = range;
            }

            public boolean postfixBind(E element) {
                return _range.contains(element);
            }
        }
    }

    @SuppressWarnings("unused")
    public static class _MAGIC_RANGE_Outside {
        private static final _MAGIC_RANGE_Outside INSTANCE = new _MAGIC_RANGE_Outside();

        public static _MAGIC_RANGE_Outside instance() {
            return INSTANCE;
        }

        public <E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> _MAGIC_RANGE_Outside.OutsideRange<E, RANGE> prefixBind(RANGE range) {
            return new _MAGIC_RANGE_Outside.OutsideRange<>(range);
        }

        @SuppressWarnings("unused")
        public static class OutsideRange<E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> {
            private final RANGE _range;

            OutsideRange(RANGE range) {
                _range = range;
            }

            public boolean postfixBind(E element) {
                return !_range.contains(element);
            }
        }
    }

    /**
     * Square the value 45sq == 45 * 45
     */
    public static final _MAGIC_Math_Squared_Double sq = _MAGIC_Math_Squared_Double.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Squared_Double {
        private static final _MAGIC_Math_Squared_Double INSTANCE = new _MAGIC_Math_Squared_Double();

        private static _MAGIC_Math_Squared_Double instance() {
            return INSTANCE;
        }

        public double postfixBind(double rad) {
            return (rad * rad);
        }
    }

    /**
     * Square the value 45sq == 45 * 45
     */
    public static final _MAGIC_Math_Squared_Float sq_ = _MAGIC_Math_Squared_Float.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Squared_Float {
        private static final _MAGIC_Math_Squared_Float INSTANCE = new _MAGIC_Math_Squared_Float();

        private static _MAGIC_Math_Squared_Float instance() {
            return INSTANCE;
        }

        public float postfixBind(float rad) {
            return (rad * rad);
        }
    }

    /**
     * Square the value 45sq == 45 * 45
     */
    public static final _MAGIC_Math_Squared_Long sq__ = _MAGIC_Math_Squared_Long.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Squared_Long {
        private static final _MAGIC_Math_Squared_Long INSTANCE = new _MAGIC_Math_Squared_Long();

        private static _MAGIC_Math_Squared_Long instance() {
            return INSTANCE;
        }

        public long postfixBind(long rad) {
            return (rad * rad);
        }
    }

    /**
     * Square the value 45sq == 45 * 45
     */
    public static final _MAGIC_Math_Squared_Integer sq___ = _MAGIC_Math_Squared_Integer.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Squared_Integer {
        private static final _MAGIC_Math_Squared_Integer INSTANCE = new _MAGIC_Math_Squared_Integer();

        private static _MAGIC_Math_Squared_Integer instance() {
            return INSTANCE;
        }

        public int postfixBind(int rad) {
            return (rad * rad);
        }
    }

    /**
     * Square Root of the value. sqrt 45 == Math.sqrt(45) == Math.pow(45, 1/2)
     */
    public static final _MAGIC_Math_Square_Root sqrt = _MAGIC_Math_Square_Root.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Math_Square_Root {
        private static final _MAGIC_Math_Square_Root INSTANCE = new _MAGIC_Math_Square_Root();

        private static _MAGIC_Math_Square_Root instance() {
            return INSTANCE;
        }

        public double postfixBind(int rad) {
            return Math.sqrt(rad);
        }

        public double postfixBind(long rad) {
            return Math.sqrt(rad);
        }

        public double postfixBind(float rad) {
            return Math.sqrt(rad);
        }

        public double postfixBind(double rad) {
            return Math.sqrt(rad);
        }
    }

    /**
     * Get a range from the indexes of a list. index List == 0 to List.size() - 1
     */
    public static final _MAGIC_Range_Index_List index = _MAGIC_Range_Index_List.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Range_Index_List {
        private static final _MAGIC_Range_Index_List INSTANCE = new _MAGIC_Range_Index_List();

        private static _MAGIC_Range_Index_List instance() {
            return INSTANCE;
        }

        public IntegerRange prefixBind(List<?> rad) {
            return 0to(rad.last());
        }
    }

    /**
     * Reverse a range (reverse 0 to 10)
     */
    public static final _MAGIC_Range_Reverse_Int reverse = _MAGIC_Range_Reverse_Int.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Range_Reverse_Int {
        private static final _MAGIC_Range_Reverse_Int INSTANCE = new _MAGIC_Range_Reverse_Int();

        private static _MAGIC_Range_Reverse_Int instance() {
            return INSTANCE;
        }

        public IntegerRange prefixBind(IntegerRange rad) {
            return -rad;
        }
    }

    /**
     * Time ticks to ms
     */
    public static final _MAGIC_Time_Ticks ticks = _MAGIC_Time_Ticks.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Ticks {
        private static final _MAGIC_Time_Ticks INSTANCE = new _MAGIC_Time_Ticks();

        private static _MAGIC_Time_Ticks instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TT);
        }
    }

    /**
     * Times 1,000
     */
    public static final _MAGIC_Metric_Thousand thousand = _MAGIC_Metric_Thousand.instance();
    /**
     * Times 1,000
     */
    public static final _MAGIC_Metric_Thousand K = _MAGIC_Metric_Thousand.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Metric_Thousand {
        private static final _MAGIC_Metric_Thousand INSTANCE = new _MAGIC_Metric_Thousand();

        private static _MAGIC_Metric_Thousand instance() {
            return INSTANCE;
        }

        public double postfixBind(double rad) {
            return (rad * 1_000);
        }

        public long postfixBind(long rad) {
            return (rad * 1_000);
        }

        public int postfixBind(int rad) {
            return (rad * 1_000);
        }
    }

    /**
     * Times 1,000,000
     */
    public static final _MAGIC_Metric_Million million = _MAGIC_Metric_Million.instance();
    /**
     * Times 1,000,000
     */
    public static final _MAGIC_Metric_Million M = _MAGIC_Metric_Million.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Metric_Million {
        private static final _MAGIC_Metric_Million INSTANCE = new _MAGIC_Metric_Million();

        private static _MAGIC_Metric_Million instance() {
            return INSTANCE;
        }

        public double postfixBind(double rad) {
            return (rad * 1_000_000);
        }

        public long postfixBind(long rad) {
            return (rad * 1_000_000);
        }

        public int postfixBind(int rad) {
            return (rad * 1_000_000);
        }
    }

    /**
     * Times 1,000,000,000
     */
    public static final _MAGIC_Metric_Billion billion = _MAGIC_Metric_Billion.instance();
    /**
     * Times 1,000,000,000
     */
    public static final _MAGIC_Metric_Billion B = _MAGIC_Metric_Billion.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Metric_Billion {
        private static final _MAGIC_Metric_Billion INSTANCE = new _MAGIC_Metric_Billion();

        private static _MAGIC_Metric_Billion instance() {
            return INSTANCE;
        }

        public double postfixBind(double rad) {
            return (rad * 1_000_000_000);
        }

        public long postfixBind(long rad) {
            return (rad * 1_000_000_000);
        }

        public int postfixBind(int rad) {
            return (rad * 1_000_000_000);
        }
    }

    /**
     * Times 1,024
     */
    public static final _MAGIC_Bytes_KB kilobytes = _MAGIC_Bytes_KB.instance();
    /**
     * Times 1,024
     */
    public static final _MAGIC_Bytes_KB KB = _MAGIC_Bytes_KB.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Bytes_KB {
        private static final _MAGIC_Bytes_KB INSTANCE = new _MAGIC_Bytes_KB();

        private static _MAGIC_Bytes_KB instance() {
            return INSTANCE;
        }

        public long postfixBind(long rad) {
            return rad * 1024L;
        }

        public double postfixBind(double rad) {
            return rad * 1024L;
        }
    }

    /**
     * Times 1,048,576
     */
    public static final _MAGIC_Bytes_MB megabytes = _MAGIC_Bytes_MB.instance();
    /**
     * Times 1,048,576
     */
    public static final _MAGIC_Bytes_MB MB = _MAGIC_Bytes_MB.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Bytes_MB {
        private static final _MAGIC_Bytes_MB INSTANCE = new _MAGIC_Bytes_MB();

        private static _MAGIC_Bytes_MB instance() {
            return INSTANCE;
        }

        public long postfixBind(long rad) {
            return rad * 1048576L;
        }

        public double postfixBind(double rad) {
            return rad * 1048576L;
        }
    }

    /**
     * Times 1,073,741,824
     */
    public static final _MAGIC_Bytes_GB gigabytes = _MAGIC_Bytes_GB.instance();
    /**
     * Times 1,073,741,824
     */
    public static final _MAGIC_Bytes_GB GB = _MAGIC_Bytes_GB.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Bytes_GB {
        private static final _MAGIC_Bytes_GB INSTANCE = new _MAGIC_Bytes_GB();

        private static _MAGIC_Bytes_GB instance() {
            return INSTANCE;
        }

        public long postfixBind(long rad) {
            return rad * 1073741824L;
        }

        public double postfixBind(double rad) {
            return rad * 1073741824L;
        }
    }

    /**
     * Times 1,099,511,627,776
     */
    public static final _MAGIC_Bytes_TB terabytes = _MAGIC_Bytes_TB.instance();
    /**
     * Times 1,099,511,627,776
     */
    public static final _MAGIC_Bytes_TB TB = _MAGIC_Bytes_TB.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Bytes_TB {
        private static final _MAGIC_Bytes_TB INSTANCE = new _MAGIC_Bytes_TB();

        private static _MAGIC_Bytes_TB instance() {
            return INSTANCE;
        }

        public long postfixBind(long rad) {
            return rad * (1099511627776L);
        }

        public double postfixBind(double rad) {
            return rad * (1099511627776L);
        }
    }

    /**
     * Time seconds to ms
     */
    public static final _MAGIC_Time_Seconds seconds = _MAGIC_Time_Seconds.instance();
    /**
     * Time seconds to ms
     */
    public static final _MAGIC_Time_Seconds second = _MAGIC_Time_Seconds.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Seconds {
        private static final _MAGIC_Time_Seconds INSTANCE = new _MAGIC_Time_Seconds();

        private static _MAGIC_Time_Seconds instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TS);
        }
    }

    /**
     * Time minutes to ms
     */
    public static final _MAGIC_Time_Minutes minutes = _MAGIC_Time_Minutes.instance();

    /**
     * Time minutes to ms
     */
    public static final _MAGIC_Time_Minutes minute = _MAGIC_Time_Minutes.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Minutes {
        private static final _MAGIC_Time_Minutes INSTANCE = new _MAGIC_Time_Minutes();

        private static _MAGIC_Time_Minutes instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TM);
        }
    }

    /**
     * Time hours to ms
     */
    public static final _MAGIC_Time_Hours hours = _MAGIC_Time_Hours.instance();

    /**
     * Time hours to ms
     */
    public static final _MAGIC_Time_Hours hour = _MAGIC_Time_Hours.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Hours {
        private static final _MAGIC_Time_Hours INSTANCE = new _MAGIC_Time_Hours();

        private static _MAGIC_Time_Hours instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TH);
        }
    }

    /**
     * Time days to ms
     */
    public static final _MAGIC_Time_Days days = _MAGIC_Time_Days.instance();

    /**
     * Time days to ms
     */
    public static final _MAGIC_Time_Days day = _MAGIC_Time_Days.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Days {
        private static final _MAGIC_Time_Days INSTANCE = new _MAGIC_Time_Days();

        private static _MAGIC_Time_Days instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TD);
        }
    }

    /**
     * Time weeks to ms
     */
    public static final _MAGIC_Time_Weeks weeks = _MAGIC_Time_Weeks.instance();

    /**
     * Time weeks to ms
     */
    public static final _MAGIC_Time_Weeks week = _MAGIC_Time_Weeks.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Weeks {
        private static final _MAGIC_Time_Weeks INSTANCE = new _MAGIC_Time_Weeks();

        private static _MAGIC_Time_Weeks instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TW);
        }
    }

    /**
     * Time months to ms
     */
    public static final _MAGIC_Time_Months months = _MAGIC_Time_Months.instance();

    /**
     * Time months to ms
     */
    public static final _MAGIC_Time_Months month = _MAGIC_Time_Months.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Months {
        private static final _MAGIC_Time_Months INSTANCE = new _MAGIC_Time_Months();

        private static _MAGIC_Time_Months instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TMO);
        }
    }

    /**
     * Time years to ms
     */
    public static final _MAGIC_Time_Years years = _MAGIC_Time_Years.instance();

    /**
     * Time years to ms
     */
    public static final _MAGIC_Time_Years year = _MAGIC_Time_Years.instance();

    @SuppressWarnings("unused")
    public static class _MAGIC_Time_Years {
        private static final _MAGIC_Time_Years INSTANCE = new _MAGIC_Time_Years();

        private static _MAGIC_Time_Years instance() {
            return INSTANCE;
        }

        public long postfixBind(double rad) {
            return (long) (rad * __MAGIC_UNIT_TIMES.TY);
        }
    }

    private static class __MAGIC_UNIT_TIMES {
        private static final long TT = 50;
        private static final long TS = 1_000;
        private static final long TM = TS * 60;
        private static final long TH = TM * 60;
        private static final long TD = TH * 24;
        private static final long TW = TD * 7;
        private static final long TMO = 2_628_000_000L;
        private static final long TY = TMO * 12;
    }
}
