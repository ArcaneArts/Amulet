package Amulet.extensions.java.util.concurrent.Future;

import art.arcane.amulet.concurrent.J;
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
   * @return the value or null
   */
  public static <V> V force(@This Future<V> self)
  {
    return J.attempt(self::get);
  }

  /**
   * Wait on multiple futures of the same type while this future runs (concurrent)
   * @param vs the futures to wait on with this future
   * @return a future list of all the results including this future
   */
  @SafeVarargs
  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<List<V>> and(@This Future<V> self, Future<V>... vs)
  {
    return J.get(() -> Arrays.stream(vs).and(self).map((i) -> i.force()).toList());
  }

  /**
   * Wait on this future and multiple callables to finish in parallel,
   * once all (including this future) have finished a list of all the values is constructed.
   * @param self
   * @param vs the list of callables to run concurrently
   * @param <V>
   * @return the future list of all the results including this future's result
   */
  @SafeVarargs
  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<List<V>> and(@This Future<V> self, Callable<V>... vs)
  {
    return J.get(() -> Arrays.stream(vs).map(J::get).and(self).map((i) -> i.force()).toList());
  }

  /**
   * Wait on this future and multiple runnables to complete concurrently.
   * @param self
   * @param vs the runnables to run in parallel
   * @param <V>
   * @return this future's result (after all the runnables have finished with this future concurrently)
   */
  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<V> and(@This Future<V> self, Runnable... vs)
  {
    return J.get(() -> {
      AtomicReference<V> bin = new AtomicReference<>();
      J.run(() -> Arrays.stream(vs).and(() -> bin.set(self.force())).map((i) -> J.run(i)).forEach((i) -> i.force())).force();
      return bin.get();
    });
  }

  /**
   * When this future completes, run a consumer with it's result
   * @param self
   * @param then the consumer to call when this future completes
   * @param <V>
   * @return the future of the THEN being called. This future should be
   * chained on the then consumer, not after this method if you need types.
   */
  public static <V> Future<?> then(@This Future<V> self, Consumer<V> then)
  {
    return J.run(() -> then.accept(self.force()));
  }
}