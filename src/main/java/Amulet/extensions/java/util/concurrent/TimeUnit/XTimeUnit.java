package Amulet.extensions.java.util.concurrent.TimeUnit;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Extension
public class XTimeUnit {
  public static long postfixBind(@This TimeUnit thiz, Double d) {
    return switch(thiz)
            {
              case NANOSECONDS, MICROSECONDS -> 0L;
              case MILLISECONDS -> d.longValue();
              case SECONDS -> (long)(d * 1000D);
              case MINUTES -> (long)(d * 60_000D);
              case HOURS -> (long)(d * (3_600_000D));
              case DAYS -> (long)(d * (3_600_000D * 24D));
            };
  }

  public static long postfixBind(@This TimeUnit thiz, Integer d) {
    return switch(thiz)
            {
              case NANOSECONDS, MICROSECONDS -> 0L;
              case MILLISECONDS -> d.longValue();
              case SECONDS -> (long)(d * 1000);
              case MINUTES -> (long)(d * 60_000);
              case HOURS -> (long)(d * (3_600_000));
              case DAYS -> (long)(d * (3_600_000 * 24));
            };
  }

  public static long postfixBind(@This TimeUnit thiz, Long d) {
    return switch(thiz)
            {
              case NANOSECONDS, MICROSECONDS -> 0L;
              case MILLISECONDS -> d;
              case SECONDS -> d * 1000;
              case MINUTES -> d * 60_000;
              case HOURS -> d * (3_600_000);
              case DAYS -> d * (3_600_000 * 24);
            };
  }

  public void f()
  {
    long a = 15.36 SECONDS;
    long b = 20 MINUTES + 15 SECONDS;
    long c = (6 HOURS + 30 MINUTES) - 15 SECONDS;
  }
}