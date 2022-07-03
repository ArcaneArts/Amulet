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

package Amulet.extensions.java.util.concurrent.locks.ReentrantLock;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Extension
public class XReentrantLock {
    public static void run(@This ReentrantLock lock, Runnable r) {
        lock.lock();
        r.run();
        lock.unlock();
    }

    public static <T> T run(@This ReentrantLock lock, Supplier<T> v) {
        lock.lock();
        T vv = v.get();
        lock.unlock();
        return vv;
    }
}
