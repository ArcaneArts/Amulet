package art.arcane.amulet.functional;

public class Consume
{
    @FunctionalInterface
    public static interface One<T0>
    {
        public void accept(T0 t0);
    }

    @FunctionalInterface
    public static interface Any<T0>
    {
        public void accept(T0... t0);
    }

    @FunctionalInterface
    public static interface Two<T0, T1>
    {
        public void accept(T0 t0, T1 t1);
    }

    @FunctionalInterface
    public static interface Three<T0, T1, T2>
    {
        public void accept(T0 t0, T1 t1, T2 t2);
    }

    @FunctionalInterface
    public static interface Four<T0, T1, T2, T3>
    {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3);
    }

    @FunctionalInterface
    public static interface Five<T0, T1, T2, T3, T4>
    {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4);
    }
}
