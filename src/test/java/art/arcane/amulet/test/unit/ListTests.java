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

package art.arcane.amulet.test.unit;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListTests {
    @Test
    public void testOperators() {
        List<Integer> v = List.from(1, 2, 3);

        assertEquals(v.copy().reverse(), -v);
        assertEquals(v.copy().remove(1, 2), v - List.from(1, 2));
        assertEquals(2, v[1]);
    }

    @Test
    public void testMethods() {
        List<Integer> v = List.from(1, 2, 3);

        assertEquals(v.copy().remove(1, 2), List.from(3));
        assertEquals(v.copy().remove(1, 2), v.copy().removeWhere(i -> i <= 2));
        assertEquals(v.copy().remove(1, 2), v.copy().keepWhere(i -> i > 2));
        assertEquals("1,2,3", v.toString(","));
        assertEquals(1, v.copy().pop());
        assertEquals(3, v.copy().popLast());
        assertEquals(2, v.last());
    }
}
