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

package art.arcane.amulet.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a list from a queue. Iteration is fast & generally concurrent
 * if the right queue is used, however get(index) and set(index, o) is incredibly slow.
 *
 * @param <T>
 */
public class QueuedList<T> implements List<T> {
    private final Queue<T> queue;

    public QueuedList(Queue<T> queue) {
        this.queue = queue;
    }

    public QueuedList() {
        this(new ConcurrentLinkedQueue<>());
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return queue.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        List<T> f = copy(ArrayList::new);
        clear();

        for (int i = 0; i < index; i++) {
            add(pop());
        }

        addAll(c);
        addAll(f);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public T get(int index) {
        return (T) toArray()[index];
    }

    @Override
    public T set(int index, T element) {
        List<T> f = copy(ArrayList::new);
        clear();

        for (int i = 0; i < index - 1; i++) {
            add(pop());
        }

        add(element);
        T x = f.pop();
        addAll(f);
        return x;
    }

    @Override
    public void add(int index, T element) {
        List<T> f = copy(ArrayList::new);
        clear();

        for (int i = 0; i < index - 1; i++) {
            add(pop());
        }

        add(element);
        addAll(f);
    }

    @Override
    public T remove(int index) {
        List<T> f = copy(ArrayList::new);
        clear();

        for (int i = 0; i < index - 1; i++) {
            add(pop());
        }

        T x = f.pop();
        addAll(f);
        return x;
    }

    @Override
    public int indexOf(Object o) {
        return copy(ArrayList::new).indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return copy(ArrayList::new).lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return copy(ArrayList::new).subList(fromIndex, toIndex);
    }
}
