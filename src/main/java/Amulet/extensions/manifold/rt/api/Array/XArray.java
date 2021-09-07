package Amulet.extensions.manifold.rt.api.Array;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Extension
public class XArray {
  public static <T> List<T> list(@This Object array) {
    return List.from((T[]) array);
  }

  public static <T> Stream<T> stream(@This Object array)
  {
    return Arrays.stream((T[]) array);
  }
}