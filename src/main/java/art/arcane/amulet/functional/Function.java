package art.arcane.amulet.functional;

class Function
{
    @FunctionalInterface
    interface One<T0, R>
    {
        R apply(T0 t0);
    }

    @FunctionalInterface
    interface Any<T0, R>
    {
        R apply(T0... t0);
    }

    @FunctionalInterface
    interface Two<T0, T1, R>
    {
        R apply(T0 t0, T1 t1);
    }

    @FunctionalInterface
    interface Three<T0, T1, T2, R>
    {
        R apply(T0 t0, T1 t1, T2 t2);
    }

    @FunctionalInterface
    interface Four<T0, T1, T2, T3, R>
    {
        R apply(T0 t0, T1 t1, T2 t2, T3 t3);
    }

    @FunctionalInterface
    interface Five<T0, T1, T2, T3, T4, R>
    {
        R apply(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4);
    }
}
