package art.arcane.amulet;

import art.arcane.amulet.geometry.Vec;

import java.util.List;
import java.util.Map;

import static art.arcane.amulet.MagicalSugar.*;
import static art.arcane.amulet.unit.UnitTime.*;

public class Amulet {
    public static void main(String[] ax) {
        System.println(365 clip 49 to 332);
    }

//    import art.arcane.amulet.geometry.Vec;
//    import org.junit.jupiter.api.Test;
//
//    import static art.arcane.amulet.MagicalSugar.*;
//    import static org.junit.jupiter.api.Assertions.*;
//
//    public class MagicSugarTests {
//        @Test
//        public void testVectors()
//        {
//            Vec a = Vec.random() * 100;
//            Vec b = Vec.random() * 100;
//
//            assertEquals(a direction b, (b - a).normalize(), "Incorrect direction sugar");
//            assertEquals(a distance b, a.distance(b), "Incorrect distance sugar");
//            assertEquals(a distanceSq b, a.distanceSquared(b), "Incorrect distanceSq sugar");
//            assertEquals(a ray b, b - a, "Incorrect ray sugar");
//        }
//    }
}
