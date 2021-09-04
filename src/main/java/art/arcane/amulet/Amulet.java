package art.arcane.amulet;

import java.util.ArrayList;
import java.util.List;

public class Amulet {
    public static void main(String[] a)
    {
        List<String> f = new ArrayList<>();
        f.add("a");
        f.add("b");

        System.out.println("Last index " + f.last());
    }
}
