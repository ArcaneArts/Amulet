package Amulet.extensions.java.lang.System;

import manifold.ext.rt.api.Extension;
import java.lang.System;

@Extension
public class XSystem {
  @Extension
  public static void println(Object os) {
    System.out.println(os);
  }

  @Extension
  public static void print(Object os) {
    System.out.print(os);
  }
}