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

package art.arcane.amulet.concurrent;

import art.arcane.bitpack.DataContainer;
import art.arcane.bitpack.NodeWritable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MultiBurst {
    public static final MultiBurst burst = new MultiBurst();
    private final AtomicLong last;
    private final String name;
    private final int priority;
    private ExecutorService service;

    public MultiBurst() {
        this("MBurst", Thread.MIN_PRIORITY);
    }

    public MultiBurst(String name, int priority) {
        this.name = name;
        this.priority = priority;
        last = new AtomicLong(Math.ms());
    }

    private synchronized ExecutorService getService() {
        last.set(Math.ms());
        if (service == null || service.isShutdown()) {
            service = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                    new ForkJoinPool.ForkJoinWorkerThreadFactory() {
                        int m = 0;

                        @Override
                        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
                            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
                            worker.setPriority(priority);
                            worker.setName(name + " " + ++m);
                            return worker;
                        }
                    },
                    (t, e) -> e.printStackTrace(), true);
        }

        return service;
    }

    public void burst(Runnable... r) {
        burst(r.length).queue(r).complete();
    }

    public void burst(boolean multicore, Runnable... r) {
        if (multicore) {
            burst(r);
        } else {
            sync(r);
        }
    }

    public void burst(List<Runnable> r) {
        burst(r.size()).queue(r).complete();
    }

    public void burst(boolean multicore, List<Runnable> r) {
        if (multicore) {
            burst(r);
        } else {
            sync(r);
        }
    }

    private void sync(List<Runnable> r) {
        for (Runnable i : r.copy()) {
            i.run();
        }
    }

    public void sync(Runnable... r) {
        for (Runnable i : r) {
            i.run();
        }
    }

    public BurstExecutor burst(int estimate) {
        return new BurstExecutor(getService(), estimate);
    }

    public BurstExecutor burst() {
        return burst(16);
    }

    public BurstExecutor burst(boolean multicore) {
        BurstExecutor b = burst();
        b.setMulticore(multicore);
        return b;
    }

    public <T> Future<T> lazySubmit(Callable<T> o) {
        return getService().submit(o);
    }

    public void lazy(Runnable o) {
        getService().execute(o);
    }

    public Future<?> future(Runnable o) {
        return getService().submit(o);
    }

    public Future<?> complete(Runnable o) {
        return getService().submit(o);
    }

    public <T> Future<T> completeValue(Callable<T> o) {
        return getService().submit(o);
    }

    public void close() {
        if (service != null) {
            service.shutdown();
            try {
                //noinspection StatementWithEmptyBody
                while (!service.awaitTermination(10, TimeUnit.SECONDS)) {

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
