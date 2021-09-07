package art.arcane.amulet;

import art.arcane.amulet.geometry.Vec;

public class MagicalSugar {
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
}
