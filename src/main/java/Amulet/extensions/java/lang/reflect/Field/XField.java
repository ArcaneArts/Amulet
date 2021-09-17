package Amulet.extensions.java.lang.reflect.Field;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;
import java.lang.reflect.Field;

@Extension
public class XField {
  public static @Self Field access(@This Field thiz) {
    thiz.setAccessible(true);
    return thiz;
  }
}