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

package Amulet.extensions.java.util.concurrent.Callable;

import art.arcane.chrono.PrecisionStopwatch;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.concurrent.Callable;
import java.util.function.DoubleConsumer;

@Extension
public class XCallable {
    public static <V> @Self Callable<V> profiled(@This Callable<V> r, DoubleConsumer metrics) {
        PrecisionStopwatch p = new PrecisionStopwatch();

        return () -> {
            p.begin();
            V v = r.call();
            metrics.accept(p.getMilliseconds());
            p.reset();
            return v;
        };
    }

    public static <V> @Self Callable<V> profiledParallel(@This Callable<V> r, DoubleConsumer metrics) {
        return () -> {
            PrecisionStopwatch p = PrecisionStopwatch.start();
            V v = r.call();
            metrics.accept(p.getMilliseconds());
            return v;
        };
    }
}