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

package Amulet.extensions.java.util.function.Supplier;

import art.arcane.amulet.metric.PrecisionStopwatch;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

@Extension
public class XSupplier {

    public static <T> @Self Supplier<T> profiled(@This Supplier<T> r, DoubleConsumer metrics) {
        PrecisionStopwatch p = new PrecisionStopwatch();

        return () -> {
            p.begin();
            T v = r.get();
            metrics.accept(p.getMilliseconds());
            p.reset();
            return v;
        };
    }

    public static <T> @Self Supplier<T> profiledParallel(@This Supplier<T> r, DoubleConsumer metrics) {
        return () -> {
            PrecisionStopwatch p = PrecisionStopwatch.start();
            T v = r.get();
            metrics.accept(p.getMilliseconds());
            return v;
        };
    }
}