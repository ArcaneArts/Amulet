package extensions.java.util.List;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Extension
public class XList {
    /**
     * Returns a copy of this list without duplicates (set conversion)
     */
    public static <E> @Self List<E> withoutDuplicates(@This List<E> self)
    {
        return self.withoutDuplicates(ArrayList::new);
    }

    /**
     * Returns a copy of this list without duplicates (set conversion)
     * @param factory the list implementation to use
     */
    public static <E> @Self List<E> withoutDuplicates(@This List<E> self, Supplier<List<E>> factory)
    {
        List<E> f = factory.get();
        f.addAll(new HashSet<>(self));
        return f;
    }

    public static <E> @Self List<E> copy(@This List<E> self)
    {
        return self.copy(ArrayList::new);
    }

    public static <E> @Self List<E> unmodifiable(@This List<E> self)
    {
        return Collections.unmodifiableList(self);
    }

    public static <E> @Self List<E> copy(@This List<E> self, Supplier<List<E>> factory)
    {
        List<E> t = factory.get();
        t.addAll(self);
        return t;
    }

    public static <E> int last(@This List<E> self)
    {
        return self.size() - 1;
    }

    public static <E> @Self List<E> removeLast(@This List<E> self)
    {
        if(self.isEmpty())
        {
            return self;
        }

        self.remove(self.last());
        return self;
    }

    public static <E> boolean isNotEmpty(@This List<E> self)
    {
        return self.isNotEmpty();
    }

    @SafeVarargs
    public static <E> @Self List<E> add(@This List<E> self, E... o)
    {
        Collections.addAll(self, o);
        return self;
    }

    @SafeVarargs
    public static <E> @Self List<E> remove(@This List<E> self, E... o)
    {
        for(E i : o)
        {
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

    public static <E> @Self List<E> removeWhere(@This List<E> self, Predicate<E> predicate)
    {
        if(self.isEmpty())
        {
            return self;
        }

        for(int i = self.last(); i >= 0; i--)
        {
            if(predicate.test(self.get(i)))
            {
                self.remove(i);
            }
        }

        return self;
    }

    public static <E> @Self List<E> keepWhere(@This List<E> self, Predicate<E> predicate)
    {
        if(self.isEmpty())
        {
            return self;
        }

        for(int i = self.last(); i >= 0; i--)
        {
            if(!predicate.test(self.get(i)))
            {
                self.remove(i);
            }
        }

        return self;
    }

    public static <E, R> @Self List<R> convert(@This List<E> self, Function<E, R> converter)
    {
        List<R> f = new ArrayList<>();

        for(E i : self)
        {
            R r = converter.apply(i);

            if(r == null)
            {
                continue;
            }

            f.add(r);
        }

        return f;
    }

    public static <E> E pop(@This List<E> self)
    {
        if(self.isEmpty())
        {
            return null;
        }

        return self.remove(0);
    }

    public static <E> E popLast(@This List<E> self)
    {
        if(self.isEmpty())
        {
            return null;
        }

        return self.remove(self.last());
    }

    @Extension
    public static <E> List<E> from(Collection<E> collection)
    {
        return List.from(collection, ArrayList::new);
    }

    @Extension
    public static <E> List<E> from(Collection<E> collection, Supplier<List<E>> factory)
    {
        List<E> l = factory.get();
        l.addAll(collection);

        return l;
    }

    @SafeVarargs
    @Extension
    public static <E> List<E> from(E... collection)
    {
        return List.from(ArrayList::new, collection);
    }

    @SafeVarargs
    @Extension
    public static <E> List<E> from(Supplier<List<E>> factory, E... collection)
    {
        List<E> l = factory.get();
        l.add(collection);

        return l;
    }

    public static <E> E popRandom(@This List<E> self)
    {
        return self.popRandom(Random.r());
    }

    public static <E> E popRandom(@This List<E> self, Random r)
    {
        if(self.isEmpty())
        {
            return null;
        }

        return self.remove(r.i(0, self.last()));
    }

    public static <E> @Self List<E> qclear(@This List<E> self)
    {
        self.clear();
        return self;
    }

    public static <E> boolean hasIndex(@This List<E> self, int index)
    {
        return self.size() > index && index >= 0;
    }

    public static <E> String toString(@This List<E> self, String split)
    {
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
