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

package art.arcane.amulet.fx;

import art.arcane.amulet.functional.Consume;
import art.arcane.amulet.functional.Function;
import art.arcane.amulet.geometry.Vec;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CubeEffect implements Effect
{
    private Vec center;
    private double radius;
    private int nodes;
    private Function.Four<Vec, Vec, Vec, Integer, Effect> planePlayer;

    @Override
    public int getMaximumNodes() {
        return nodes;
    }

    @Override
    public void play() {
        int nodesPerPlane = nodes / 6;

        Vec top = center + (Vec.of(0, radius, 0));
        Vec bottom = center + (Vec.of(0, -radius, 0));
        Vec a = center + (Vec.of(radius,0, 0));
        Vec b = center + (Vec.of(-radius, 0, 0));
        Vec c = center + (Vec.of(0, 0, radius));
        Vec d = center + (Vec.of(0, 0, radius));
        planePlayer.apply(top + (Vec.of(-radius, 0, -radius)),
                top + (Vec.of(radius, 0, -radius)),
                top + (Vec.of(radius, 0, radius)),
                nodesPerPlane
        ).play();
        planePlayer.apply(bottom + (Vec.of(-radius, 0, -radius)),
                bottom + (Vec.of(radius, 0, -radius)),
                bottom + (Vec.of(radius, 0, radius)),
                nodesPerPlane
        ).play();
        planePlayer.apply(a + (Vec.of(0, -radius, -radius)),
                a + (Vec.of(0, radius, -radius)),
                a + (Vec.of(0, radius, radius)),
                nodesPerPlane
        ).play();
        planePlayer.apply(b + (Vec.of(0, -radius, -radius)),
                b + (Vec.of(0, radius, -radius)),
                b + (Vec.of(0, radius, radius)),
                nodesPerPlane
        ).play();
        planePlayer.apply(c + (Vec.of(-radius, -radius, 0)),
                c + (Vec.of(radius, -radius, 0)),
                c + (Vec.of(radius, radius, 0)),
                nodesPerPlane
        ).play();
        planePlayer.apply(d + (Vec.of(-radius, -radius, 0)),
                d + (Vec.of(radius, -radius, 0)),
                d + (Vec.of(radius, radius, 0)),
                nodesPerPlane
        ).play();
    }
}
