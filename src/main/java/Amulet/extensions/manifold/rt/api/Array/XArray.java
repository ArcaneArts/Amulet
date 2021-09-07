package Amulet.extensions.manifold.rt.api.Array;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.lang.reflect.Array;
import java.util.List;

@Extension
public class XArray {
  public static <T> List<T> list(@This Object array) {
    return List.from((T[]) array);
  }
}