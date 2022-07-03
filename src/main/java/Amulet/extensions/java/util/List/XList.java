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

package Amulet.extensions.java.util.List;

import art.arcane.amulet.functional.Consume;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static art.arcane.amulet.MagicalSugar.index;
import static art.arcane.amulet.MagicalSugar.reverse;

@Extension
public class XList {
    /**
     * Returns a copy of this list without duplicates (set conversion)
     */
    public static <E> @Self List<E> withoutDuplicates(@This List<E> self) {
        return self.withoutDuplicates(ArrayList::new);
    }


    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, Object[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, int[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, double[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, float[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, byte[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, short[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, long[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    @SuppressWarnings("unchecked")
    public static <E> @Self List<E> forceAdd(@This List<E> self, boolean[] values) {
        for (Object i : values) {
            self.add((E) i);
        }

        return self;
    }

    public static <E> @Self List<E> sort(@This List<E> self) {
        self.sort(Comparator.comparing(Object::toString));
        return self;
    }

    /**
     * Returns a copy of this list without duplicates (set conversion)
     *
     * @param factory the list implementation to use
     */
    public static <E> @Self List<E> withoutDuplicates(@This List<E> self, Supplier<List<E>> factory) {
        List<E> f = factory.get();
        f.addAll(new HashSet<>(self));
        return f;
    }

    public static <E> E middleValue(@This List<E> self) {
        return self.get(self.middleIndex());
    }

    public static <E> int middleIndex(@This List<E> self) {
        return self.size() % 2 == 0 ? (self.size() / 2) : ((self.size() / 2) + 1);
    }

    public static <E> @Self List<E> forEachIndex(@This List<E> self, Consume.Two<List<E>, Integer> itr) {
        for (int i : index self) {
            itr.accept(self, i);
        }

        return self;
    }

    public static <E> @Self List<E> forEachReverseIndex(@This List<E> self, Consume.Two<List<E>, Integer> itr) {
        for (int i : reverse index self) {
            itr.accept(self, i);
        }

        return self;
    }

    public static <E> @Self List<E> evenValues(@This List<E> self) {
        List<E> even = new ArrayList<>();

        for (int i : reverse index self) {
            if (i % 2 == 0) {
                even.addFirst(self.remove(i));
            }
        }

        return even;
    }

    public static <E> @Self List<E> oddValues(@This List<E> self) {
        List<E> odd = new ArrayList<>();

        for (int i : reverse index self) {
            if (i % 2 != 0) {
                odd.addFirst(self.remove(i));
            }
        }

        return odd;
    }

    public static <E> @Self List<E> copy(@This List<E> self) {
        return self.copy(ArrayList::new);
    }

    public static <E> @Self List<E> copyClear(@This List<E> self) {
        List<E> t = self.copy();
        self.clear();
        return t;
    }

    public static <E> @Self List<E> unmodifiable(@This List<E> self) {
        return Collections.unmodifiableList(self);
    }

    public static <E> @Self List<E> copy(@This List<E> self, Supplier<List<E>> factory) {
        List<E> t = factory.get();
        t.addAll(self);
        return t;
    }

    public static <E> int last(@This List<E> self) {
        return self.size() - 1;
    }

    public static <E> @Self List<E> removeLast(@This List<E> self) {
        if (self.isEmpty()) {
            return self;
        }

        self.remove(self.last());
        return self;
    }

    public static <E> boolean isNotEmpty(@This List<E> self) {
        return !self.isEmpty();
    }

    @SafeVarargs
    public static <E> @Self List<E> add(@This List<E> self, E... o) {
        Collections.addAll(self, o);
        return self;
    }

    public static <E> @Self List<E> add(@This List<E> self, List<E> o) {
        self.addAll(o);
        return self;
    }

    public static <E> @Self List<E> addFirst(@This List<E> self, E o) {
        self.add(0, o);
        return self;
    }

    @SafeVarargs
    public static <E> @Self List<E> remove(@This List<E> self, E... o) {
        for (E i : o) {
            self.remove(i);
        }

        return self;
    }

    public static <E> @Self List<E> remove(@This List<E> self, List<E> o) {
        for (E i : o) {
            self.remove(i);
        }

        return self;
    }

    public static <E> @Self List<E> swapIndexes(@This List<E> self, int a, int b) {
        E aa = self.remove(a);
        E bb = self.get(b);
        self.add(a, bb);
        self.remove(b);
        self.add(b, aa);
        return self;
    }

    public static <E> @Self List<E> removeWhere(@This List<E> self, Predicate<E> predicate) {
        if (self.isEmpty()) {
            return self;
        }

        List<E> drop = new ArrayList<>();

        for (int i : reverse index self) {
            if (predicate.test(self[i])) {
                drop.add(self[i]);
            }
        }

        self.removeAll(drop);

        return self;
    }

    public static <E> @Self List<E> keepWhere(@This List<E> self, Predicate<E> predicate) {
        return self.removeWhere(predicate.negate());
    }

    public static <E, R> @Self List<R> convert(@This List<E> self, Function<E, R> converter) {
        List<R> f = new ArrayList<>();

        for (E i : self) {
            R r = converter.apply(i);

            if (r == null) {
                continue;
            }

            f.add(r);
        }

        return f;
    }

    public static <E> E pop(@This List<E> self) {
        if (self.isEmpty()) {
            return null;
        }

        return self.remove(0);
    }

    public static <E> E popLast(@This List<E> self) {
        if (self.isEmpty()) {
            return null;
        }

        return self.remove(self.last());
    }

    @Extension
    public static <E> List<E> from(Collection<E> collection) {
        return List.from(collection, ArrayList::new);
    }

    @Extension
    public static <E> CopyOnWriteArrayList<E> copyOnWrite() {
        return new CopyOnWriteArrayList<>();
    }

    @Extension
    public static <E> ArrayList<E> array() {
        return new ArrayList<>();
    }

    @Extension
    public static <E> LinkedList<E> linked() {
        return new LinkedList<>();
    }

    @Extension
    public static <E> List<E> from(Collection<E> collection, Supplier<List<E>> factory) {
        List<E> l = factory.get();
        l.addAll(collection);

        return l;
    }

    @SafeVarargs
    @Extension
    public static <E> List<E> from(E... collection) {
        return from(ArrayList::new, collection);
    }

    @SafeVarargs
    public static <E> List<E> from(Supplier<List<E>> factory, E... collection) {
        List<E> l = factory.get();
        l.add(collection);

        return l;
    }

    public static <E> E popRandom(@This List<E> self) {
        return self.popRandom(Random.r());
    }

    public static <E> E popRandom(@This List<E> self, Random r) {
        if (self.isEmpty()) {
            return null;
        }

        return self.remove(r.i(0, self.last()));
    }

    public static <E> @Self List<E> minus(@This List<E> self, E that) {
        List<E> s = List.from(self);
        s.remove(that);
        return s;
    }

    public static <E> @Self List<E> minus(@This List<E> self, Collection<E> that) {
        List<E> s = List.from(self);
        s.removeAll(that);
        return s;
    }

    public static <E> @Self List<E> reverse(@This List<E> self) {
        Collections.reverse(self);
        return self;
    }

    public static <E> @Self List<E> unaryMinus(@This List<E> self) {
        return List.from(self).reverse();
    }

    public static <E> @Self List<E> minus(@This List<E> self, List<E> that) {
        return List.from(self).remove(that);
    }

    public static <E> boolean hasIndex(@This List<E> self, int index) {
        return self.size() > index && index >= 0;
    }

    public static <E> String toString(@This List<E> self, String split) {
        if (self.isEmpty()) {
            return "";
        }

        if (self.size() == 1) {
            return self.get(0) + "";
        }

        StringBuilder b = new StringBuilder();

        for (E i : self) {
            b.append(split).append(i);
        }

        return b.substring(split.length());
    }
}
