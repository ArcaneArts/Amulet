package Amulet.extensions.java.lang.Integer;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.lang.Integer;
import java.util.LinkedHashMap;
import java.util.Map;

@Extension
public class XInteger {
  private static final Map<String, Integer> romanNumerals =
          new LinkedHashMap<String, Integer>().qput("M", 1000)
          .qput("CM", 900).qput("D", 500).qput("CD", 400).qput("C", 100)
          .qput("XC", 90).qput("L", 50).qput("XL", 40).qput("X", 10)
          .qput("IX", 9).qput("V", 5).qput("IV", 4).qput("I", 1);

  public static String toRoman(@This Integer num) {
    StringBuilder res = new StringBuilder();

    for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
      res.append(entry.getKey().repeat(num / entry.getValue()));
      num %= entry.getValue();
    }

    return res.toString();
  }
}