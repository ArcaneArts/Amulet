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

package art.arcane.amulet.profiling;

import art.arcane.amulet.math.RollingSequence;
import art.arcane.amulet.metric.PrecisionStopwatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Profiler {
    public static int METRIC_SIZE = 32;
    public static final Map<String, PrecisionStopwatch> stopwatches = new ConcurrentHashMap<>();
    public static final Map<String, RollingSequence> sequences = new ConcurrentHashMap<>();

    public static void begin(String key) {
        stopwatches.computeIfAbsent(key, (k) -> new PrecisionStopwatch()).resetAndBegin();
    }

    public static void end(String key) {
        PrecisionStopwatch p = stopwatches.get(key);

        if (p != null) {
            put(key, p.getMilliseconds());
        }
    }

    public static void put(String key, double value) {
        sequences.computeIfAbsent(key, (k) -> new RollingSequence(METRIC_SIZE)).put(value);
    }

    public static double get(String key) {
        RollingSequence r = sequences.get(key);

        if (r != null) {
            return r.getAverage();
        }

        return 0;
    }
}
