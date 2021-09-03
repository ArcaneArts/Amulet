package extensions.java.util.List;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Extension
public class XList {
    public static <E> @Self List<E> copy(@This List<E> self)
    {
        return new ArrayList<>(self);
    }

    public static <E> @Self List<E> copy(@This List<E> self, Supplier<List<E>> factory)
    {
        List<E> t = factory.get();
        t.addAll(self);
        return t;
    }
}
