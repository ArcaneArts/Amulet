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

import static art.arcane.amulet.MagicalSugar.*;
import art.arcane.amulet.geometry.Vec;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class Ray
{
    private final Vec from;
    private final Vec to;
    private final int hits;

    public void cast(Consumer<Vec> onHit) {
        Vec direction = from.direction(to).multiply((hits-1) inverted);
        Vec cursor = from.copy();
        onHit.accept(from);

        for(int i = 0; i <= hits; i++) {
            cursor += direction;
            onHit.accept(cursor);
        }
    }
}
