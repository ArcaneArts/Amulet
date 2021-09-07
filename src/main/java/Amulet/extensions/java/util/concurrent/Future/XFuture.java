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
  public static <V> V force(@This Future<V> self)
  {
    return J.attempt(self::get);
  }

  @SafeVarargs
  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<List<V>> and(@This Future<V> self, Future<V>... vs)
  {
    return J.get(() -> Arrays.stream(vs).and(self).map((i) -> i.force()).toList());
  }

  @SafeVarargs
  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<List<V>> and(@This Future<V> self, Callable<V>... vs)
  {
    return J.get(() -> Arrays.stream(vs).map(J::get).and(self).map((i) -> i.force()).toList());
  }

  @SuppressWarnings("Convert2MethodRef")
  public static <V> Future<V> and(@This Future<V> self, Runnable... vs)
  {
    return J.get(() -> {
      AtomicReference<V> bin = new AtomicReference<>();
      J.run(() -> Arrays.stream(vs).and(() -> bin.set(self.force())).map((i) -> J.run(i)).forEach((i) -> i.force())).force();
      return bin.get();
    });
  }

  public static <V> Future<?> then(@This Future<V> self, Consumer<V> then)
  {
    return J.run(() -> then.accept(self.force()));
  }
}