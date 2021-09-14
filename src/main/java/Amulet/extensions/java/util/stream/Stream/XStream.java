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

package Amulet.extensions.java.util.stream.Stream;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Extension
public class XStream {
    public static <T> List<T> toList(@This Stream<T> thiz) {
        return thiz.collect(Collectors.toList());
    }

    public static <T> Set<T> toSet(@This Stream<T> thiz) {
        return thiz.collect(Collectors.toSet());
    }

    public static <T> @Self Stream<T> and(@This Stream<T> thiz, Stream<T> add) {
        return Stream.concat(thiz, add);
    }

    public static <T> @Self Stream<T> and(@This Stream<T> thiz, T add) {
        return thiz.and(Stream.of(add));
    }

    public static <T> @Self Stream<T> plus(@This Stream<T> thiz, Stream<T> add) {
        return Stream.concat(thiz, add);
    }
}