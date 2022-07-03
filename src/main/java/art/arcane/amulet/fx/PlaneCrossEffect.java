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

import art.arcane.amulet.geometry.Vec;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PlaneCrossEffect implements Effect {
    private final Vec a;
    private final Vec b;
    private final Vec c;
    private final int nodes;
    private final EffectPlayer player;

    @Override
    public int getMaximumNodes() {
        return nodes;
    }

    @Override
    public void play() {
        Vec d = a + b.directionNoNormal(c);
        int nodesPerLine = nodes / 4;
        new LineEffect(a, c, nodesPerLine, player).play();
        new LineEffect(d, b, nodesPerLine, player).play();
    }
}
