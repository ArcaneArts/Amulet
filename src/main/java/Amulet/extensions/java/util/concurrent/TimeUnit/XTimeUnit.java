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

package Amulet.extensions.java.util.concurrent.TimeUnit;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Extension
public class XTimeUnit {
    public static long postfixBind(@This TimeUnit thiz, Double d) {
        return switch (thiz) {
            case NANOSECONDS, MICROSECONDS -> 0L;
            case MILLISECONDS -> d.longValue();
            case SECONDS -> (long) (d * 1000D);
            case MINUTES -> (long) (d * 60_000D);
            case HOURS -> (long) (d * (3_600_000D));
            case DAYS -> (long) (d * (3_600_000D * 24D));
        };
    }

    public static long postfixBind(@This TimeUnit thiz, Integer d) {
        return switch (thiz) {
            case NANOSECONDS, MICROSECONDS -> 0L;
            case MILLISECONDS -> d.longValue();
            case SECONDS -> (long) (d * 1000);
            case MINUTES -> (long) (d * 60_000);
            case HOURS -> (long) (d * (3_600_000));
            case DAYS -> (long) (d * (3_600_000 * 24));
        };
    }

    public static long postfixBind(@This TimeUnit thiz, Long d) {
        return switch (thiz) {
            case NANOSECONDS, MICROSECONDS -> 0L;
            case MILLISECONDS -> d;
            case SECONDS -> d * 1000;
            case MINUTES -> d * 60_000;
            case HOURS -> d * (3_600_000);
            case DAYS -> d * (3_600_000 * 24);
        };
    }

    public void f() {
        long a = 15.36SECONDS;
        long b = 20MINUTES + 15SECONDS;
        long c = (6HOURS + 30MINUTES) - 15SECONDS;
    }
}