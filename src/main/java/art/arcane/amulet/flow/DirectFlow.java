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

package art.arcane.amulet.flow;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import manifold.ext.rt.api.Self;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectFlow<T> implements Flow<T> {
    private final Int2ObjectOpenHashMap<BoundFlowListener<T>> listeners;
    private final AtomicInteger id;
    private final AtomicBoolean closed;

    public DirectFlow()
    {
        closed = new AtomicBoolean(false);
        id = new AtomicInteger(0);
        this.listeners = new Int2ObjectOpenHashMap<>();
    }

    @Override
    public @Self DirectFlow<T> emit(T t) {
        if(closed.get())
        {
            throw new RuntimeException("Flow is Closed!");
        }

        listeners.values().forEach(i -> i.getListener().on(t));

        return this;
    }

    @Override
    public @Self DirectFlow<T> emit(Iterable<T> t) {
        if(closed.get())
        {
            throw new RuntimeException("Flow is Closed!");
        }

        listeners.values().forEach((i) -> {
            for(T j : t)
            {
                i.getListener().on(j);
            }
        });

        return this;
    }

    @Override
    public synchronized BoundFlowListener<T> bindListen(FlowListener<T> listener) {
        int i = id.getAndIncrement();
        BoundFlowListener<T> b = new BoundFlowListener<T>(listener, this, i);
        listeners.put(i, b);
        return b;
    }

    @Override
    public synchronized void close() {
        if(closed.get())
        {
            throw new RuntimeException("Flow is already Closed!");
        }

        closeListeners();
        closed.set(true);
    }

    @Override
    public synchronized void closeListeners() {
        if(closed.get())
        {
            throw new RuntimeException("Flow is already Closed!");
        }

        listeners.clear();
    }

    @Override
    public synchronized void close(int id) {
        if(closed.get())
        {
            throw new RuntimeException("Flow is Closed!");
        }

        listeners.remove(id);
    }
}
