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

package Amulet.extensions.java.lang.Runnable;

import art.arcane.amulet.metric.PrecisionStopwatch;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.function.DoubleConsumer;

@Extension
public class XRunnable {
    public static @Self Runnable profiled(@This Runnable r, DoubleConsumer metrics) {
        PrecisionStopwatch p = new PrecisionStopwatch();

        return () -> {
            p.begin();
            r.run();
            metrics.accept(p.getMilliseconds());
            p.reset();
        };
    }

    public static @Self Runnable profiledParallel(@This Runnable r, DoubleConsumer metrics) {
        return () -> {
            PrecisionStopwatch p = PrecisionStopwatch.start();
            r.run();
            metrics.accept(p.getMilliseconds());
        };
    }
}