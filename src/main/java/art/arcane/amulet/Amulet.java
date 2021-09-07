package art.arcane.amulet;

import art.arcane.amulet.geometry.Vec;

import static art.arcane.amulet.MagicalSugar.*;

public class Amulet {
    public static void main(String[] ax) {

        Vec a = Vec.of(0,0,0);
        Vec b = Vec.of(3,3,3);

        Vec v;

        v = a direction b; // Returns Vec direction with a magnitude of 1.0
        System.println(v);
        v = a ray b;       // Returns Vec(3,3,3)
        System.println(v);

    }
}
