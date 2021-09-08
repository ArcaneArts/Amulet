package Amulet.extensions.java.util.Set;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Extension
public class XSet {
  public static <E> @Self Set<E> copy(@This Set<E> self)
  {
    return self.copy(HashSet::new);
  }

  public static <E> @Self Set<E> unmodifiable(@This Set<E> self)
  {
    return Collections.unmodifiableSet(self);
  }

  public static <E> @Self Set<E> copy(@This Set<E> self, Supplier<Set<E>> factory)
  {
    Set<E> t = factory.get();
    t.addAll(self);
    return t;
  }

  public static <E> boolean isNotEmpty(@This Set<E> self)
  {
    return !self.isEmpty();
  }

  @SafeVarargs
  public static <E> @Self Set<E> add(@This Set<E> self, E... o)
  {
    Collections.addAll(self, o);
    return self;
  }

  public static <E> @Self Set<E> add(@This Set<E> self, Set<E> o)
  {
    self.addAll(o);
    return self;
  }

  @SafeVarargs
  public static <E> @Self Set<E> remove(@This Set<E> self, E... o)
  {
    for(E i : o)
    {
      self.remove(i);
    }

    return self;
  }

  public static <E> @Self Set<E> remove(@This Set<E> self, Set<E> o)
  {
    for(E i : o)
    {
      self.remove(i);
    }

    return self;
  }

  public static <E> @Self Set<E> removeWhere(@This Set<E> self, Predicate<E> predicate)
  {
    if(self.isEmpty())
    {
      return self;
    }

    return self.stream().filter(predicate).toSet();
  }

  public static <E> @Self Set<E> keepWhere(@This Set<E> self, Predicate<E> predicate)
  {
    if(self.isEmpty())
    {
      return self;
    }

    return self.stream().filter(predicate.negate()).toSet();
  }

  public static <E, R> @Self Set<R> convert(@This Set<E> self, Function<E, R> converter)
  {
    Set<R> f = new HashSet<>();

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

  public static <E> E pop(@This Set<E> self)
  {
    if(self.isEmpty())
    {
      return null;
    }

    Iterator<E> it = self.iterator();
    E o = it.next();
    it.remove();

    return o;
  }

  public static <E> E getFirst(@This Set<E> self)
  {
    if(self.isEmpty())
    {
      return null;
    }

    return self.iterator().next();
  }

  @Extension
  public static <E> Set<E> from(Collection<E> collection)
  {
    return Set.from(collection, HashSet::new);
  }

  @Extension
  public static <E> Set<E> from(Collection<E> collection, Supplier<Set<E>> factory)
  {
    Set<E> l = factory.get();
    l.addAll(collection);

    return l;
  }

  @SafeVarargs
  @Extension
  public static <E> Set<E> from(E... collection)
  {
    return from(HashSet::new, collection);
  }

  @SafeVarargs
  public static <E> Set<E> from(Supplier<Set<E>> factory, E... collection)
  {
    Set<E> l = factory.get();
    l.add(collection);

    return l;
  }

  public static <E> @Self Set<E> qclear(@This Set<E> self)
  {
    self.clear();
    return self;
  }

  public static <E> List<E> list(@This Set<E> self)
  {
    return List.from(self);
  }

  public static <E> @Self Set<E> plus(@This Set<E> self, E that)
  {
    Set<E> s = Set.from(self);
    s.add(that);
    return s;
  }

  public static <E> @Self Set<E> minus(@This Set<E> self, E that)
  {
    Set<E> s = Set.from(self);
    s.remove(that);
    return s;
  }

  public static <E> @Self Set<E> plus(@This Set<E> self, Collection<E> that)
  {
    Set<E> s = Set.from(self);
    s.addAll(that);
    return s;
  }

  public static <E> @Self Set<E> minus(@This Set<E> self, Collection<E> that)
  {
    Set<E> s = Set.from(self);
    s.removeAll(that);
    return s;
  }

  public static <E> String toString(@This Set<E> self, String split)
  {
    if (self.isEmpty()) {
      return "";
    }

    if (self.size() == 1) {
      return self.getFirst() + "";
    }

    Iterator<E> it = self.iterator();
    StringBuilder b = new StringBuilder();
    while(it.hasNext())
    {
      E e = it.next();
      b.append(split).append(e);
    }

    return b.substring(split.length());
  }
}