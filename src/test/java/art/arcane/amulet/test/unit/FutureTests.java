/*
 * Amulet is an extension api for Java
 * Copyright (c) 2021 Arcane Arts
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

import art.arcane.amulet.concurrent.J;
import org.junit.jupiter.api.Test;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTests {
    @Test
    public void testThenChaining() {
        assertEquals(50, Future.of(50).force());
        assertEquals(25, Future.of(50).then((i) -> i/2).force());
        assertEquals(100, Future.of(25).then(i -> i*2).then(i -> i * 2).force());
    }

    @Test
    public void testAndCalling() {
        assertEquals(15, J.get(this::get)
                .andCall(this::get, this::get)
                .force().stream().mapToInt(i -> i).sum());
    }

    public int get()
    {
        return 5;
    }
}
