/*
 * Amulet is an extension api for Java
 * Copyright (c) 2022 Arcane Arts
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    VecImpl3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector
     */
    VecImpl3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new vector v,v,v
     */
    VecImpl3(double v) {
        this(v, v, v);
    }

    /**
     * Create a new vector 0,0,0
     */
    VecImpl3() {
        this(0);
    }
}
