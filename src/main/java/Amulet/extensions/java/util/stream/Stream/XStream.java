package Amulet.extensions.java.util.stream.Stream;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Extension
public class XStream {
  public static <T> List<T> toList(@This Stream<T> thiz) {
    return thiz.collect(Collectors.toList());
  }

  public static <T> Set<T> toSet(@This Stream<T> thiz) {
    return thiz.collect(Collectors.toSet());
  }

  public static <T> @Self Stream<T> and(@This Stream<T> thiz, Stream<T> add) {
    return Stream.concat(thiz, add);
  }

  public static <T> @Self Stream<T> plus(@This Stream<T> thiz, Stream<T> add) {
    return Stream.concat(thiz, add);
  }
}