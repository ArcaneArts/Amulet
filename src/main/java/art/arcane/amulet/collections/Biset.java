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

package art.arcane.amulet.collections;

public interface Biset<A,B> {
    static <A,B> Biset<A,B> from(A a, B b)
    {
        return new ObjectBiset<>(a, b);
    }

    A getA();

    void setA(A a);

    B getB();

    void setB(B b);

    default <T> void set(int m, T v)
    {
        if(m == 0)
        {
            setA((A) v);
        }

        else
        {
            setB((B) v);
        }
    }

    default <T> T get(int m)
    {
        return m == 0 ? (T) getA() : (T) getB();
    }
}
