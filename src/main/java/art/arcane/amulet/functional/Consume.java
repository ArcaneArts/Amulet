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

package art.arcane.amulet.functional;

import art.arcane.amulet.metric.Average;
import art.arcane.amulet.metric.PrecisionStopwatch;

import java.io.IOException;
import java.util.function.DoubleConsumer;

public class Consume {
    @FunctionalInterface
    public static interface One<T0> {
        void accept(T0 t0);

        default One<T0> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t) -> {
                p.begin();
                accept(t);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default One<T0> profiledParallel(DoubleConsumer metrics)
        {
            return (t) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface Any<T0> {
        void accept(T0... t0);

        default One<T0> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t) -> {
                p.begin();
                accept(t);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default One<T0> profiledParallel(DoubleConsumer metrics)
        {
            return (t) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface Two<T0, T1> {
        void accept(T0 t0, T1 t1);

        default Two<T0, T1> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t, tt) -> {
                p.begin();
                accept(t, tt);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default Two<T0, T1> profiledParallel(DoubleConsumer metrics)
        {
            return (t, tt) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t, tt);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface TwoIO<T0, T1> {
        void accept(T0 t0, T1 t1) throws IOException;

        default TwoIO<T0, T1> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t, tt) -> {
                p.begin();
                accept(t, tt);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default TwoIO<T0, T1> profiledParallel(DoubleConsumer metrics)
        {
            return (t, tt) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t, tt);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface Three<T0, T1, T2> {
        void accept(T0 t0, T1 t1, T2 t2);

        default Three<T0, T1, T2> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t, tt, ttt) -> {
                p.begin();
                accept(t, tt, ttt);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default Three<T0, T1, T2> profiledParallel(DoubleConsumer metrics)
        {
            return (t, tt, ttt) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t, tt, ttt);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface Four<T0, T1, T2, T3> {
        void accept(T0 t0, T1 t1, T2 t2, T3 t3);

        default Four<T0, T1, T2, T3> profiled(DoubleConsumer metrics)
        {
            PrecisionStopwatch p = new PrecisionStopwatch();

            return (t, tt, ttt, tttt) -> {
                p.begin();
                accept(t, tt, ttt, tttt);
                metrics.accept(p.getMilliseconds());
                p.reset();
            };
        }

        default Four<T0, T1, T2, T3> profiledParallel(DoubleConsumer metrics)
        {
            return (t, tt, ttt, tttt) -> {
                PrecisionStopwatch p = PrecisionStopwatch.start();
                accept(t, tt, ttt, tttt);
                metrics.accept(p.getMilliseconds());
            };
        }
    }

    @FunctionalInterface
    public static interface FourIO<T0, T1, T2, T3> {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3) throws IOException;
    }

    @FunctionalInterface
    public static interface Five<T0, T1, T2, T3, T4> {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4);
    }

    @FunctionalInterface
    public static interface Six<T0, T1, T2, T3, T4, T5> {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    @FunctionalInterface
    public static interface Seven<T0, T1, T2, T3, T4, T5, T6> {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
    }

    @FunctionalInterface
    public static interface Eight<T0, T1, T2, T3, T4, T5, T6, T7> {
        public void accept(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
    }
}
