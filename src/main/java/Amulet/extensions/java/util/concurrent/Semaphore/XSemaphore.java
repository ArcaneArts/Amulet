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

package Amulet.extensions.java.util.concurrent.Semaphore;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

@Extension
public class XSemaphore {
    public static void run(@This Semaphore self, int permits, Runnable r) throws InterruptedException {
        self.acquire(permits);
        r.run();
        self.release(permits);
    }

    public static <T> T run(@This Semaphore self, int permits, Supplier<T> r) throws InterruptedException {
        self.acquire(permits);
        T tt = r.get();
        self.release(permits);
        return tt;
    }

    public static int getPermitCount(@This Semaphore self) {
        String s = self.toString().split("\\Q=\\E")[1].trim();
        s = s.substring(0, s.length() - 1);
        return Integer.parseInt(s);
    }

    public static void withAll(@This Semaphore self, Runnable r) {
        int drained = self.drainPermits();
        int p = self.getPermitCount();
        while (drained < p) {
            int a = self.availablePermits();
            if (a > 0) {
                if (self.tryAcquire(a)) {
                    drained += a;
                }
            } else if (self.tryAcquire()) {
                drained++;
            } else {
                try {
                    self.acquire();
                    drained++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            r.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        self.release(drained);
    }

    public static void run(@This Semaphore self, Runnable r) throws InterruptedException {
        self.acquire();
        r.run();
        self.release();
    }

    public static <T> T run(@This Semaphore self, Supplier<T> r) throws InterruptedException {
        self.acquire();
        T tt = r.get();
        self.release();
        return tt;
    }
}