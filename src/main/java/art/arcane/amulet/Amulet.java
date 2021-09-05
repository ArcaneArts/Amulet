package art.arcane.amulet;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Amulet {
    public static void main(String[] a)
    {
        // Hello I'm a list
        List<Integer> ints = List.from(1, 2, 3);

        // Let's add some more
        ints += List.from(4, 5, 6);

        ints.toString(", "); // "1, 2, 3, 4, 5, 6"

        // EZPZ
        List<Integer> fin = ints.unmodifiable();

        // Add dupes?
        ints += fin;

        // Or without?
        ints = ints.withoutDuplicates();

        // Make a copy using the CopyOnWriteArrayList implementation
        List<Integer> insanity = ints.copy(CopyOnWriteArrayList::new);

        // Random is easy too!
        Random.r().i(3, 7); // int between 3 - 7

        // Math has more stuff
        Math.ms(); Math.ns(); // Just like the M class!

        "hello,world".replaceAbs(",", "|"); // Instead of .replaceAll("\\Q,\\E", "|")
        "hello,world".splitAbs(","); // Instead of .split("\\Q,\\E")
        ".".isNotEmpty();
        "   is    this   not    cool?  ".remove("not").normalize(); // "is this not cool?"
    }
}
