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

package Amulet.extensions.java.util.Set;

import art.arcane.amulet.flow.FlowBuilder;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;
import manifold.util.concurrent.ConcurrentHashSet;
import org.checkerframework.checker.units.qual.K;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Extension
public class XSet {
    public static <E> @Self Set<E> copy(@This Set<E> self) {
        return self.copy(HashSet::new);
    }

    public static <E> FlowBuilder<E> flow(@This Set<E> self) {
        return new FlowBuilder<>(self.iterator());
    }

    public static <E> @Self Set<E> unmodifiable(@This Set<E> self) {
        return Collections.unmodifiableSet(self);
    }

    public static <E> @Self Set<E> copy(@This Set<E> self, Supplier<Set<E>> factory) {
        Set<E> t = factory.get();
        t.addAll(self);
        return t;
    }

    public static <E> boolean isNotEmpty(@This Set<E> self) {
        return !self.isEmpty();
    }

    @SafeVarargs
    public static <E> @Self Set<E> add(@This Set<E> self, E... o) {
        Collections.addAll(self, o);
        return self;
    }

    public static <E> @Self Set<E> add(@This Set<E> self, Set<E> o) {
        self.addAll(o);
        return self;
    }

    @SafeVarargs
    public static <E> @Self Set<E> remove(@This Set<E> self, E... o) {
        for (E i : o) {
            self.remove(i);
        }

        return self;
    }

    public static <E> @Self Set<E> remove(@This Set<E> self, Set<E> o) {
        for (E i : o) {
            self.remove(i);
        }

        return self;
    }

    public static <E> @Self Set<E> removeWhere(@This Set<E> self, Predicate<E> predicate) {
        if (self.isEmpty()) {
            return self;
        }

        var s = self.stream().filter(predicate.negate()).toSet();
        self.clear();
        self.addAll(s);
        return self;
    }

    public static <E> @Self Set<E> keepWhere(@This Set<E> self, Predicate<E> predicate) {
        return self.removeWhere(predicate.negate());
    }

    public static <E, R> @Self Set<R> convert(@This Set<E> self, Function<E, R> converter) {
        Set<R> f = new HashSet<>();

        for (E i : self) {
            R r = converter.apply(i);

            if (r == null) {
                continue;
            }

            f.add(r);
        }

        return f;
    }

    public static <E> E pop(@This Set<E> self) {
        if (self.isEmpty()) {
            return null;
        }

        Iterator<E> it = self.iterator();
        E o = it.next();
        it.remove();

        return o;
    }

    public static <E> E getFirst(@This Set<E> self) {
        if (self.isEmpty()) {
            return null;
        }

        return self.iterator().next();
    }

    @Extension
    public static <E> ConcurrentHashSet<E> concurrent()
    {
        return new ConcurrentHashSet<>();
    }

    @Extension
    public static <E> HashSet<E> hash()
    {
        return new HashSet<>();
    }

    @Extension
    public static <E> LinkedHashSet<E> linked()
    {
        return new LinkedHashSet<>();
    }

    @Extension
    public static <E> CopyOnWriteArraySet<E> copyOnWriteArraySet()
    {
        return new CopyOnWriteArraySet<>();
    }

    @Extension
    public static <E> Set<E> from(Collection<E> collection) {
        return Set.from(collection, HashSet::new);
    }

    @Extension
    public static <E> Set<E> from(Collection<E> collection, Supplier<Set<E>> factory) {
        Set<E> l = factory.get();
        l.addAll(collection);

        return l;
    }

    @SafeVarargs
    @Extension
    public static <E> Set<E> from(E... collection) {
        return from(HashSet::new, collection);
    }

    @SafeVarargs
    public static <E> Set<E> from(Supplier<Set<E>> factory, E... collection) {
        Set<E> l = factory.get();
        l.add(collection);

        return l;
    }

    public static <E> @Self Set<E> qclear(@This Set<E> self) {
        self.clear();
        return self;
    }

    public static <E> List<E> list(@This Set<E> self) {
        return List.from(self);
    }

    public static <E> @Self Set<E> plus(@This Set<E> self, E that) {
        Set<E> s = Set.from(self);
        s.add(that);
        return s;
    }

    public static <E> @Self Set<E> minus(@This Set<E> self, E that) {
        Set<E> s = Set.from(self);
        s.remove(that);
        return s;
    }

    public static <E> @Self Set<E> plus(@This Set<E> self, Collection<E> that) {
        Set<E> s = Set.from(self);
        s.addAll(that);
        return s;
    }

    public static <E> @Self Set<E> minus(@This Set<E> self, Collection<E> that) {
        Set<E> s = Set.from(self);
        s.removeAll(that);
        return s;
    }

    public static <E> String toString(@This Set<E> self, String split) {
        if (self.isEmpty()) {
            return "";
        }

        if (self.size() == 1) {
            return self.getFirst() + "";
        }

        Iterator<E> it = self.iterator();
        StringBuilder b = new StringBuilder();
        while (it.hasNext()) {
            E e = it.next();
            b.append(split).append(e);
        }

        return b.substring(split.length());
    }
}