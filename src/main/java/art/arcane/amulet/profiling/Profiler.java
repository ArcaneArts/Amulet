package art.arcane.amulet.profiling;

import art.arcane.amulet.math.RollingSequence;
import art.arcane.amulet.metric.PrecisionStopwatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Profiler
{
    public static int METRIC_SIZE = 32;
    public static final Map<String, PrecisionStopwatch> stopwatches = new ConcurrentHashMap<>();
    public static final Map<String, RollingSequence> sequences = new ConcurrentHashMap<>();

    public static void begin(String key) {
        stopwatches.computeIfAbsent(key, (k) -> new PrecisionStopwatch()).resetAndBegin();
    }

    public static void end(String key) {
        PrecisionStopwatch p = stopwatches.get(key);

        if(p != null)
        {
            put(key, p.getMilliseconds());
        }
    }

    public static void put(String key, double value) {
        sequences.computeIfAbsent(key, (k)->new RollingSequence(METRIC_SIZE)).put(value);
    }

    public static double get(String key) {
        RollingSequence r = sequences.get(key);

        if(r != null)
        {
            return r.getAverage();
        }

        return 0;
    }
}
