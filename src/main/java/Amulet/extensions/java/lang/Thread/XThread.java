package Amulet.extensions.java.lang.Thread;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.lang.Thread;

@Extension
public class XThread {
  public static void die(@This Thread self) {
    self.interrupt();
    self.stop();
  }

  @Extension
  public static Class<?> callingClass() {
    try {
      return Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return null;
  }
}