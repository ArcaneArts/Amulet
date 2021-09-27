package Amulet.extensions.java.util.concurrent.locks.ReentrantLock;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Extension
public class XReentrantLock {
  public static void run(@This ReentrantLock lock, Runnable r) {
    lock.lock();
    r.run();
    lock.unlock();
  }

  public static <T> void run(@This ReentrantLock lock, Supplier<T> v)
  {
    lock.lock();
    T vv = v.get();
    lock.unlock();
  }
}
