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

package Amulet.extensions.java.util.concurrent.Future;

import art.arcane.amulet.concurrent.J;
import art.arcane.amulet.functional.Function;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Extension
public class XFuture {
    /**
     * Force call get() and return null if anything fails without throwing exceptions
     *
     * @return the value or null
     */
    public static <V> V force(@This Future<V> self) {
        return J.attempt(self::get);
    }

    /**
     * Wait on multiple futures of the same type while this future runs (concurrent)
     *
     * @param vs the futures to wait on with this future
     * @return a future list of all the results including this future
     */
    @SafeVarargs
    @SuppressWarnings("Convert2MethodRef")
    public static <V> Future<List<V>> and(@This Future<V> self, Future<V>... vs) {
        return J.get(() -> Arrays.stream(vs).and(self).map((i) -> i.force()).toList());
    }

    /**
     * Wait on this future and multiple callables to finish in parallel,
     * once all (including this future) have finished a list of all the values is constructed.
     *
     * @param self
     * @param vs   the list of callables to run concurrently
     * @param <V>
     * @return the future list of all the results including this future's result
     */
    @SafeVarargs
    @SuppressWarnings("Convert2MethodRef")
    public static <V> Future<List<V>> and(@This Future<V> self, Callable<V>... vs) {
        return J.get(() -> Arrays.stream(vs).map(J::get).and(self).map((i) -> i.force()).toList());
    }

    /**
     * Wait on this future and multiple runnables to complete concurrently.
     *
     * @param self
     * @param vs   the runnables to run in parallel
     * @param <V>
     * @return this future's result (after all the runnables have finished with this future concurrently)
     */
    @SuppressWarnings("Convert2MethodRef")
    public static <V> Future<V> and(@This Future<V> self, Runnable... vs) {
        return J.get(() -> {
            AtomicReference<V> bin = new AtomicReference<>();
            J.run(() -> Arrays.stream(vs).and(() -> bin.set(self.force())).map((i) -> J.run(i)).forEach((i) -> i.force())).force();
            return bin.get();
        });
    }

    /**
     * When this future completes, run a consumer with it's result
     *
     * @param then the function to call when this future completes,
     *             this will be converted into another future switching to the return type
     * @param <V> the incoming future type
     * @param <R> the resulting (then) future type
     * @return the future of the THEN being called.
     */
    public static <V, R> Future<R> then(@This Future<V> self, FutureThenFunction<V, R> then) {
        return J.get(() -> then.apply(self.force()));
    }

    /**
     * Join two futures with the THEN tag, one after another
     *
     * @param then the function to call when this future completes,
     *             this will be converted into another future switching to the return type
     * @param <V> the incoming future type
     * @param <R> the resulting (then) future type
     * @return the future of the THEN being called.
     */
    public static <V, R> Future<R> then(@This Future<V> self, Future<R> then) {
        return self.then((r) -> J.get(then::get).force());
    }

    @FunctionalInterface
    public interface FutureThenFunction<V, R> {
        R apply(V v);
    }
}