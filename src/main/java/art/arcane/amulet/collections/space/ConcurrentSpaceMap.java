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

package art.arcane.amulet.collections.space;

import art.arcane.amulet.collections.Biset;
import art.arcane.amulet.data.CompressedNumbers;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.googlecode.concurrentlinkedhashmap.Weigher;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static art.arcane.amulet.MagicalSugar.min;
import static art.arcane.amulet.MagicalSugar.or;

public class ConcurrentSpaceMap<T> implements Space<T>, Weigher<SpaceNode<T>>, EvictionListener<Long, SpaceNode<T>> {
    private final ConcurrentLinkedHashMap<Long, SpaceNode<T>> data;
    private final SpaceLoader<T> loader;
    private final SpaceSaver<T> saver;

    public ConcurrentSpaceMap(SpaceLoader<T> loader, SpaceSaver<T> saver) {
        this(65_536, loader, saver);
    }

    public ConcurrentSpaceMap(int absoluteMax, SpaceLoader<T> loader, SpaceSaver<T> saver) {
        this(absoluteMax min((absoluteMax / 4)min 128), absoluteMax, loader, saver);
    }

    public ConcurrentSpaceMap(int initialMax, int absoluteMax, SpaceLoader<T> loader, SpaceSaver<T> saver) {
        this.loader = loader;
        this.saver = saver;
        data = new ConcurrentLinkedHashMap.Builder<Long, SpaceNode<T>>()
                .initialCapacity(initialMax).maximumWeightedCapacity(absoluteMax)
                .concurrencyLevel(32).weigher(this).listener(this).build();
    }

    @Override
    public int weightOf(SpaceNode<T> value) {
        return value.getWeight();
    }

    @Override
    public void onEviction(Long key, SpaceNode<T> value) {
        if (value.isModified()) {
            save(value.get(), CompressedNumbers.i2a(key), CompressedNumbers.i2b(key));
        }
    }

    private LongStream worstKeys() {
        return data.entrySet().copy().stream()
                .sorted(Comparator.comparingLong((Map.Entry<Long, SpaceNode<T>> i) -> i.getValue().getWeight()).reversed())
                .sorted(Comparator.comparingLong((Map.Entry<Long, SpaceNode<T>> i) -> i.getValue().getLastAccess()))
                .sorted(Comparator.comparingLong((Map.Entry<Long, SpaceNode<T>> i) -> i.getValue().getAccessCount()))
                .sorted(Comparator.comparingLong((Map.Entry<Long, SpaceNode<T>> i) -> i.getValue().getAge()).reversed())
                .mapToLong(Map.Entry::getKey);
    }

    private Stream<Map.Entry<Long, SpaceNode<T>>> modifiedKeys() {
        return data.entrySet().copy().stream().filter(i -> i.getValue().isModified());
    }

    @Override
    public int trimToSize(int maxSize, boolean force) {
        AtomicInteger is = new AtomicInteger(0);
        int s = getSize();
        worstKeys().takeWhile(__ -> s - is.incrementAndGet() > maxSize)
                .forEach(i -> unload(CompressedNumbers.i2a(i), CompressedNumbers.i2b(i)));
        return is.get();
    }

    @Override
    public int trim(int trimCount) {
        AtomicInteger is = new AtomicInteger(0);
        worstKeys().limit(trimCount)
                .forEach(i -> {
                    unload(CompressedNumbers.i2a(i), CompressedNumbers.i2b(i));
                    is.incrementAndGet();
                });
        return is.get();
    }

    @Override
    public T getOrNull(int x, int z) {
        SpaceNode<T> t = data.get(CompressedNumbers.i2(x, z));

        if (t != null) {
            return t.get();
        }

        return null;
    }

    @Override
    public boolean isModified(int x, int z) {
        SpaceNode<T> t = data.get(CompressedNumbers.i2(x, z));

        if (t != null) {
            return t.isModified();
        }

        return false;
    }

    @Override
    public List<Biset<Integer, Integer>> getPositions() {
        return data.keySet().stream()
                .map(i -> Biset.from(CompressedNumbers.i2a(i), CompressedNumbers.i2b(i)))
                .toList();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public void saveAll() {
        modifiedKeys().parallel().forEach(i -> save(i.getValue().peek(), CompressedNumbers.i2a(i.getKey()), CompressedNumbers.i2b(i.getKey())));
    }

    @Override
    public void unloadAll() {
        data.entrySet().copy().forEach(i -> {
            if (i.getValue().isModified()) {
                save(i.getValue().peek(), CompressedNumbers.i2a(i.getKey()), CompressedNumbers.i2b(i.getKey()));
            }

            data.remove(i.getKey());
        });
    }

    @Override
    public void save(int x, int z) {
        if (isModified(x, z)) {
            save(getOrNull(x, z), x, z);
        }
    }

    @Override
    public void unload(int x, int z) {
        if (isModified(x, z)) {
            save(x, z);
        }

        data.remove(CompressedNumbers.i2(x, z));
    }

    @Override
    public T get(int x, int z) {
        return getOrNull(x, z)or(load(x, z));
    }

    @Override
    public T load(int x, int z) {
        try {
            return loader.load(x, z);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(T data, int x, int z) {
        try {
            saver.save(data, x, z);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
