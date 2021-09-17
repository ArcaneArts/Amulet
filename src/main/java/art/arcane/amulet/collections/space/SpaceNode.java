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

package art.arcane.amulet.collections.space;

import java.util.concurrent.atomic.AtomicLong;

public class SpaceNode<T> {
    private final T t;
    private final int hash;
    private final AtomicLong lastAccess;
    private final AtomicLong accesses;
    private final boolean weighted;

    public SpaceNode(T t)
    {
        this.t = t;
        this.hash = t.hashCode();
        this.lastAccess = new AtomicLong(Math.ms());
        this.accesses = new AtomicLong(0);
        this.weighted = t instanceof Weighted;
    }

    public int getWeight()
    {
        return weighted ? ((Weighted)t).getWeight() : 1;
    }

    public long getLastAccess()
    {
        return lastAccess.get();
    }

    public long getAccessCount()
    {
        return accesses.get();
    }

    public long getAge()
    {
        return (100 + (Math.ms() - lastAccess.get())) / accesses.get();
    }

    public boolean isModified()
    {
        return t.hashCode() != hash;
    }

    public T peek()
    {
        return t;
    }

    public T get()
    {
        lastAccess.set(Math.ms());
        accesses.incrementAndGet();
        return t;
    }
}
