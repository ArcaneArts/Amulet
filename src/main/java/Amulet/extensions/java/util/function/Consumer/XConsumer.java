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

package Amulet.extensions.java.util.function.Consumer;

import art.arcane.amulet.metric.PrecisionStopwatch;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

@Extension
public class XConsumer {
    public static <T> @Self Consumer<T> profiled(@This Consumer<T> r, DoubleConsumer metrics) {
        PrecisionStopwatch p = new PrecisionStopwatch();

        return (t) -> {
            p.begin();
            r.accept(t);
            metrics.accept(p.getMilliseconds());
            p.reset();
        };
    }

    public static <T> @Self Consumer<T> profiledParallel(@This Consumer<T> r, DoubleConsumer metrics) {
        return (t) -> {
            PrecisionStopwatch p = PrecisionStopwatch.start();
            r.accept(t);
            metrics.accept(p.getMilliseconds());
        };
    }
}