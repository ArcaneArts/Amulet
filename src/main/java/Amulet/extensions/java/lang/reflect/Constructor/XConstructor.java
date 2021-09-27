package Amulet.extensions.java.lang.reflect.Constructor;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.lang.reflect.Constructor;

@Extension
public class XConstructor {
  public static <T> @Self Constructor<T> access(@This Constructor<T> thiz) {
    thiz.setAccessible(true);
    return thiz;
  }
}