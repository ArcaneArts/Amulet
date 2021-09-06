package art.arcane.amulet.geometry;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
@Data
public class VecImpl3 implements Vec {
    private double x;
    private double y;
    private double z;

    /**
     * Create a new vector
     */
    VecImpl3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector
     */
    VecImpl3(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector v,v,v
     */
    VecImpl3(double v)
    {
        this(v,v,v);
    }

    /**
     * Create a new vector 0,0,0
     */
    VecImpl3()
    {
        this(0);
    }
}
