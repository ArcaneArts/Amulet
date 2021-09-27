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

package art.arcane.amulet.functional;

public class Function {
    @FunctionalInterface
    public interface One<T0, R> {
        R apply(T0 t0);
    }

    @FunctionalInterface
    public interface Any<T0, R> {
        R apply(T0... t0);
    }

    @FunctionalInterface
    public interface Two<T0, T1, R> {
        R apply(T0 t0, T1 t1);
    }

    @FunctionalInterface
    public interface Three<T0, T1, T2, R> {
        R apply(T0 t0, T1 t1, T2 t2);
    }

    @FunctionalInterface
    public interface Four<T0, T1, T2, T3, R> {
        R apply(T0 t0, T1 t1, T2 t2, T3 t3);
    }

    @FunctionalInterface
    public interface Five<T0, T1, T2, T3, T4, R> {
        R apply(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4);
    }
}
