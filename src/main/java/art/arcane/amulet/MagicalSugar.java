package art.arcane.amulet;

import art.arcane.amulet.geometry.Vec;
import art.arcane.amulet.range.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class MagicalSugar {
    /**
     * Create a vector & angle amount
     */
    public static final _MAGIC_Vec_Angle angle = _MAGIC_Vec_Angle.instance();
    public static class _MAGIC_Vec_Angle
    {
        private static final _MAGIC_Vec_Angle INSTANCE = new _MAGIC_Vec_Angle();
        private static _MAGIC_Vec_Angle instance() {return INSTANCE;}
        public __FROM postfixBind(Vec angle) {return new __FROM(angle);}
        public record __FROM(Vec angle) {
            public __FROM_DUAL prefixBind(double a) {
                return new __FROM_DUAL(angle, a);
            }
        }
        public record __FROM_DUAL(Vec start, double a) { }
    }

    /**
     * Rotate a vector around a unit vector axis/amount
     */
    public static final _MAGIC_Vec_RT rotate = _MAGIC_Vec_RT.instance();
    public static class _MAGIC_Vec_RT
    {
        private static final _MAGIC_Vec_RT INSTANCE = new _MAGIC_Vec_RT();
        private static _MAGIC_Vec_RT instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_RTX
    {
        private static final _MAGIC_Vec_RTX INSTANCE = new _MAGIC_Vec_RTX();
        private static _MAGIC_Vec_RTX instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_RTY
    {
        private static final _MAGIC_Vec_RTY INSTANCE = new _MAGIC_Vec_RTY();
        private static _MAGIC_Vec_RTY instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_RTZ
    {
        private static final _MAGIC_Vec_RTZ INSTANCE = new _MAGIC_Vec_RTZ();
        private static _MAGIC_Vec_RTZ instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_Dot
    {
        private static final _MAGIC_Vec_Dot INSTANCE = new _MAGIC_Vec_Dot();
        private static _MAGIC_Vec_Dot instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
        public record __FROM(Vec start) {
            public double prefixBind(Vec into) {
                return start.dot(into);
            }
        }
    }

    /**
     * String split
     */
    public static final _MAGIC_String_Split split = _MAGIC_String_Split.instance();
    public static class _MAGIC_String_Split
    {
        private static final _MAGIC_String_Split INSTANCE = new _MAGIC_String_Split();
        private static _MAGIC_String_Split instance() {return INSTANCE;}
        public __FROM postfixBind(String from) {return new __FROM(from);}
        public record __FROM(String start) {
            public List<String> prefixBind(String into) {
                return List.of(start.splitAbs(into));
            }
        }
    }

    /**
     * String without
     */
    public static final _MAGIC_String_Without without = _MAGIC_String_Without.instance();
    public static class _MAGIC_String_Without
    {
        private static final _MAGIC_String_Without INSTANCE = new _MAGIC_String_Without();
        private static _MAGIC_String_Without instance() {return INSTANCE;}
        public __FROM postfixBind(String from) {return new __FROM(from);}
        public record __FROM(String start) {
            public List<String> prefixBind(String into) {
                return List.of(start.replaceAbs(into, ""));
            }
        }
    }

    /**
     * Clip a number to a min and max bound
     */
    public static final _MAGIC_Number_Clip clip = _MAGIC_Number_Clip.instance();
    public static class _MAGIC_Number_Clip
    {
        private static final _MAGIC_Number_Clip INSTANCE = new _MAGIC_Number_Clip();
        private static _MAGIC_Number_Clip instance() {return INSTANCE;}
        public __FROMD postfixBind(double from) {return new __FROMD(from);}
        public record __FROMD(double start) {
            public double prefixBind(DoubleRange into) {
                double min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                double max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return Math.min(Math.max(start, min), max);
            }
        }
        public __FROMI postfixBind(int from) {return new __FROMI(from);}
        public record __FROMI(double start) {
            public int prefixBind(IntegerRange into) {
                int min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                int max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return (int) Math.min(Math.max(start, min), max);
            }
        }

        public __FROML postfixBind(long from) {return new __FROML(from);}
        public record __FROML(long start) {
            public long prefixBind(LongRange into) {
                long min = Math.min(into.getLeftEndpoint(), into.getRightEndpoint());
                long max = Math.max(into.getLeftEndpoint(), into.getRightEndpoint());
                return (long) Math.min(Math.max(start, min), max);
            }
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
    public static class _MAGIC_Convert_Degrees
    {
        private static final _MAGIC_Convert_Degrees INSTANCE = new _MAGIC_Convert_Degrees();
        private static _MAGIC_Convert_Degrees instance() {return INSTANCE;}
        public double postfixBind(double rad) {return Math.toRadians(rad);}
    }

    /**
     * Convert to lower case
     */
    public static final _MAGIC_Convert_Lowercase lc = _MAGIC_Convert_Lowercase.instance();
    /**
     * Convert to lower case
     */
    public static final _MAGIC_Convert_Lowercase lower = _MAGIC_Convert_Lowercase.instance();
    public static class _MAGIC_Convert_Lowercase
    {
        private static final _MAGIC_Convert_Lowercase INSTANCE = new _MAGIC_Convert_Lowercase();
        private static _MAGIC_Convert_Lowercase instance() {return INSTANCE;}
        public String postfixBind(String in) {return in.lower();}
    }

    /**
     * Convert to uppper case
     */
    public static final _MAGIC_Convert_Uppercase uc = _MAGIC_Convert_Uppercase.instance();
    /**
     * Convert to uppper case
     */
    public static final _MAGIC_Convert_Uppercase upper = _MAGIC_Convert_Uppercase.instance();
    public static class _MAGIC_Convert_Uppercase
    {
        private static final _MAGIC_Convert_Uppercase INSTANCE = new _MAGIC_Convert_Uppercase();
        private static _MAGIC_Convert_Uppercase instance() {return INSTANCE;}
        public String postfixBind(String in) {return in.upper();}
    }

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Vector vec = _MAGIC_Convert_Vector.instance();

    /**
     * Convert degrees to radians
     */
    public static final _MAGIC_Convert_Vector vector = _MAGIC_Convert_Vector.instance();
    public static class _MAGIC_Convert_Vector
    {
        private static final _MAGIC_Convert_Vector INSTANCE = new _MAGIC_Convert_Vector();
        private static _MAGIC_Convert_Vector instance() {return INSTANCE;}
        public Vec prefixBind(double[] pos) {return Vec.of(pos[0], pos[1], pos[2]);}
        public Vec prefixBind(List<Double> pos) {return Vec.of(pos[0], pos[1], pos[2]);}
    }

    /**
     * Convert radians to degrees
     */
    public static final _MAGIC_Convert_Radians rad = _MAGIC_Convert_Radians.instance();

    /**
     * Convert radians to degrees
     */
    public static final _MAGIC_Convert_Radians radians = _MAGIC_Convert_Radians.instance();
    public static class _MAGIC_Convert_Radians
    {
        private static final _MAGIC_Convert_Radians INSTANCE = new _MAGIC_Convert_Radians();
        private static _MAGIC_Convert_Radians instance() {return INSTANCE;}
        public double postfixBind(double deg) {return Math.toDegrees(deg);}
    }

    /**
     * Cross product (same as v1.crossProduct(v2))
     */
    public static final _MAGIC_Vec_CrossProduct cross = _MAGIC_Vec_CrossProduct.instance();
    public static class _MAGIC_Vec_CrossProduct
    {
        private static final _MAGIC_Vec_CrossProduct INSTANCE = new _MAGIC_Vec_CrossProduct();
        private static _MAGIC_Vec_CrossProduct instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_Direction
    {
        private static final _MAGIC_Vec_Direction INSTANCE = new _MAGIC_Vec_Direction();
        private static _MAGIC_Vec_Direction instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_Ray
    {
        private static final _MAGIC_Vec_Ray INSTANCE = new _MAGIC_Vec_Ray();
        private static _MAGIC_Vec_Ray instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_Distance
    {
        private static final _MAGIC_Vec_Distance INSTANCE = new _MAGIC_Vec_Distance();
        private static _MAGIC_Vec_Distance instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
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
    public static class _MAGIC_Vec_DistanceSquared
    {
        private static final _MAGIC_Vec_DistanceSquared INSTANCE = new _MAGIC_Vec_DistanceSquared();
        private static _MAGIC_Vec_DistanceSquared instance() {return INSTANCE;}
        public __FROM postfixBind(Vec from) {return new __FROM(from);}
        public record __FROM(Vec start) {
            public double prefixBind(Vec into) {
                return start.distanceSquared(into);
            }
        }
    }
    
    public static final MagicalSugar.Closed to   = MagicalSugar.Closed.instance();    // both endpoints included
    public static final MagicalSugar.LeftOpen _to   = MagicalSugar.LeftOpen.instance();  // left endpoint excluded
    public static final MagicalSugar.RightOpen to_  = MagicalSugar.RightOpen.instance(); // right endpoint excluded
    public static final MagicalSugar.Open _to_  = MagicalSugar.Open.instance();      // both endpoints excluded

    public static final MagicalSugar.Step step  = MagicalSugar.Step.instance();      // provides a `step` clause
    public static final MagicalSugar.Unit unit  = MagicalSugar.Unit.instance();      // provides a `unit` clause

    public static final MagicalSugar.Inside inside = MagicalSugar.Inside.instance();      // test for range membership
    public static final MagicalSugar.Outside outside = MagicalSugar.Outside.instance();   // negative test for range membership

    /**
     * For internal use.
     */
    public static class Closed
    {
        private static final MagicalSugar.Closed INSTANCE = new MagicalSugar.Closed();

        public static MagicalSugar.Closed instance()
        {
            return INSTANCE;
        }

        boolean _leftClosed;
        boolean _rightClosed;

        private Closed()
        {
            _leftClosed = true;
            _rightClosed = true;
        }

        /** Comparable */

        public <E extends Comparable<E>> MagicalSugar.Closed.From_Comp<E> postfixBind(E comparable )
        {
            return new MagicalSugar.Closed.From_Comp<>( comparable );
        }

        public class From_Comp<E extends Comparable<E>>
        {
            private E _start;

            From_Comp( E sequential )
            {
                _start = sequential;
            }

            public ComparableRange<E> prefixBind(E end )
            {
                return new ComparableRange<>( _start, end, _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** Sequential */

        public <E extends Sequential<E, S, U>, S, U> MagicalSugar.Closed.From_Seq<E, S, U> postfixBind(E sequential )
        {
            return new MagicalSugar.Closed.From_Seq<>( sequential );
        }

        public class From_Seq<E extends Sequential<E, S, U>, S, U>
        {
            private E _start;

            From_Seq( E sequential )
            {
                _start = sequential;
            }

            public SequentialRange<E, S, U> prefixBind(E end )
            {
                return new SequentialRange<>( _start, end, null, null, _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** BigDecimal */

        public MagicalSugar.Closed.From_BigDecimal postfixBind(BigDecimal bd )
        {
            return new MagicalSugar.Closed.From_BigDecimal( bd );
        }
        public class From_BigDecimal
        {
            private BigDecimal _start;

            From_BigDecimal( BigDecimal sequential )
            {
                _start = sequential;
            }

            public BigDecimalRange prefixBind( BigDecimal end )
            {
                return new BigDecimalRange( _start, end, BigDecimal.ONE,
                        _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** BigInteger */

        public MagicalSugar.Closed.From_BigInteger postfixBind(BigInteger bd )
        {
            return new MagicalSugar.Closed.From_BigInteger( bd );
        }
        public class From_BigInteger
        {
            private BigInteger _start;

            From_BigInteger( BigInteger sequential )
            {
                _start = sequential;
            }

            public BigIntegerRange prefixBind( BigInteger end )
            {
                return new BigIntegerRange( _start, end, BigInteger.ONE,
                        _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** Double */

        public MagicalSugar.Closed.From_Double postfixBind(Double bd )
        {
            return new MagicalSugar.Closed.From_Double( bd );
        }
        public class From_Double
        {
            private Double _start;

            From_Double( Double sequential )
            {
                _start = sequential;
            }

            public DoubleRange prefixBind( Double end )
            {
                return new DoubleRange( _start, end, 1,
                        _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** Long */

        public MagicalSugar.Closed.From_Long postfixBind(Long bd )
        {
            return new MagicalSugar.Closed.From_Long( bd );
        }
        public class From_Long
        {
            private Long _start;

            From_Long( Long sequential )
            {
                _start = sequential;
            }

            public LongRange prefixBind( Long end )
            {
                return new LongRange( _start, end, 1,
                        _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }

        /** Integer */

        public MagicalSugar.Closed.From_Integer postfixBind(Integer bd )
        {
            return new MagicalSugar.Closed.From_Integer( bd );
        }
        public class From_Integer
        {
            private Integer _start;

            From_Integer( Integer sequential )
            {
                _start = sequential;
            }

            public IntegerRange prefixBind( Integer end )
            {
                return new IntegerRange( _start, end, 1,
                        _leftClosed, _rightClosed, _start.compareTo( end ) > 0 );
            }
        }
    }

    /**
     * For internal use.
     */
    public static class LeftOpen extends MagicalSugar.Closed
    {
        private static final MagicalSugar.LeftOpen INSTANCE = new MagicalSugar.LeftOpen();

        public static MagicalSugar.LeftOpen instance()
        {
            return INSTANCE;
        }

        private LeftOpen()
        {
            _leftClosed = false;
        }
    }

    /**
     * For internal use.
     */
    public static class RightOpen extends MagicalSugar.Closed
    {
        private static final MagicalSugar.RightOpen INSTANCE = new MagicalSugar.RightOpen();

        public static MagicalSugar.RightOpen instance()
        {
            return INSTANCE;
        }

        private RightOpen()
        {
            _rightClosed = false;
        }
    }

    /**
     * For internal use.
     */
    public static class Open extends MagicalSugar.Closed
    {
        private static final MagicalSugar.Open INSTANCE = new MagicalSugar.Open();

        public static MagicalSugar.Open instance()
        {
            return INSTANCE;
        }

        private Open()
        {
            _leftClosed = false;
            _rightClosed = false;
        }
    }

    public static class Step
    {
        private static final MagicalSugar.Step INSTANCE = new MagicalSugar.Step();

        public static MagicalSugar.Step instance()
        {
            return INSTANCE;
        }

        public <E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> MagicalSugar.Step.StepRange<E, S, U, RANGE> postfixBind(RANGE range )
        {
            return new MagicalSugar.Step.StepRange<>( range );
        }

        public static class StepRange<E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>>
        {
            private final RANGE _range;

            StepRange( RANGE range )
            {
                _range = range;
            }

            public RANGE prefixBind( S step )
            {
                return _range.step( step );
            }
        }
    }

    public static class Unit
    {
        private static final MagicalSugar.Unit INSTANCE = new MagicalSugar.Unit();

        public static MagicalSugar.Unit instance()
        {
            return INSTANCE;
        }

        public <E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>> MagicalSugar.Unit.UnitRange<E, S, U, RANGE> postfixBind(RANGE range )
        {
            return new MagicalSugar.Unit.UnitRange<>( range );
        }

        public static class UnitRange<E extends Comparable<E>, S, U, RANGE extends AbstractIterableRange<E, S, U, RANGE>>
        {
            private final RANGE _range;

            UnitRange( RANGE range )
            {
                _range = range;
            }

            public RANGE prefixBind( U unit )
            {
                return _range.unit( unit );
            }
        }
    }

    public static class Inside
    {
        private static final MagicalSugar.Inside INSTANCE = new MagicalSugar.Inside();

        public static MagicalSugar.Inside instance()
        {
            return INSTANCE;
        }

        public <E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> MagicalSugar.Inside.InsideRange<E, RANGE> prefixBind(RANGE range )
        {
            return new MagicalSugar.Inside.InsideRange<>( range );
        }

        public static class InsideRange<E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>>
        {
            private final RANGE _range;

            InsideRange( RANGE range )
            {
                _range = range;
            }

            public boolean postfixBind( E element )
            {
                return _range.contains( element );
            }
        }
    }

    public static class Outside
    {
        private static final MagicalSugar.Outside INSTANCE = new MagicalSugar.Outside();

        public static MagicalSugar.Outside instance()
        {
            return INSTANCE;
        }

        public <E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>> MagicalSugar.Outside.OutsideRange<E, RANGE> prefixBind(RANGE range )
        {
            return new MagicalSugar.Outside.OutsideRange<>( range );
        }

        public static class OutsideRange<E extends Comparable<E>, RANGE extends AbstractRange<E, RANGE>>
        {
            private final RANGE _range;

            OutsideRange( RANGE range )
            {
                _range = range;
            }

            public boolean postfixBind( E element )
            {
                return !_range.contains( element );
            }
        }
    }
}
