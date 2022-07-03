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

package Amulet.extensions.java.lang.Integer;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.LinkedHashMap;
import java.util.Map;

@Extension
public class XInteger {
    private static final Map<String, Integer> romanNumerals =
            new LinkedHashMap<String, Integer>().qput("M", 1000)
                    .qput("CM", 900).qput("D", 500).qput("CD", 400).qput("C", 100)
                    .qput("XC", 90).qput("L", 50).qput("XL", 40).qput("X", 10)
                    .qput("IX", 9).qput("V", 5).qput("IV", 4).qput("I", 1);

    public static String toRoman(@This Integer num) {
        StringBuilder res = new StringBuilder();

        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            res.append(entry.getKey().repeat(num / entry.getValue()));
            num %= entry.getValue();
        }

        return res.toString();
    }
}