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

import art.arcane.amulet.metric.StarvingTimeout;
import manifold.ext.rt.api.Self;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public interface Flow<T> {
    @Self Flow<T> emit(T t);

    @Self Flow<T> emit(Iterable<T> t);

    default @Self Flow<T> emit(Iterator<T> t)
    {
        while(t.hasNext())
        {
            emit(t.next());
        }

        return this;
    }

    default List<T> drain(int max)
    {
        return drain(max, Long.MAX_VALUE);
    }

    default List<T> drain(int max, long timeout)
    {
        List<T> data = new ArrayList<>();
        AtomicReference<StarvingTimeout> starving = new AtomicReference<>();
        AtomicReference<BoundFlowListener<T>> listener = new AtomicReference<>();
         listener.set(bindListen((i) -> {
            starving.get().feed();
            data.add(i);

            if(data.size() >= max)
            {
                close(listener.get().getId());
            }
        }));
        starving.set(new StarvingTimeout(timeout, () -> close(listener.get().getId())));
        starving.get().waitForTimeout();
        return data;
    }

    default @Self Flow<T> on(FlowListener<T> listener)
    {
        bindListen(listener);
        return this;
    }

    default <V> Flow<V> map(FlowMapper<T, V> mapper)
    {
        Flow<V> flow = new DirectFlow<>();
        on(t -> {
            V v = mapper.on(t);

            if(v != null)
            {
                flow.emit(v);
            }
        });
        return flow;
    }

    BoundFlowListener<T> bindListen(FlowListener<T> listener);

    void close();

    void closeListeners();

    void close(int id);
}
