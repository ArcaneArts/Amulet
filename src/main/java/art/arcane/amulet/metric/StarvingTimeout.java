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

package art.arcane.amulet.metric;

import static art.arcane.amulet.MagicalSugar.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class StarvingTimeout {
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private final AtomicLong last;
    private final Runnable timeout;
    private final AtomicBoolean ticking;
    private final ScheduledFuture<?> future;
    private final long patience;
    private final AtomicReference<Thread> waiting;

    public StarvingTimeout(long patience, Runnable timeout)
    {
        waiting = new AtomicReference<>();
        this.patience = patience;
        this.ticking = new AtomicBoolean(true);
        this.timeout = timeout;
        this.last = new AtomicLong(Math.ms());
        future = service.scheduleWithFixedDelay(() -> {
            if(Math.ms() - last.get() > patience)
            {
                timedOut();
            }
        }, patience, patience, TimeUnit.MILLISECONDS);
    }

    public boolean waitForTimeout()
    {
        waiting.set(Thread.currentThread());
        while(ticking.get())
        {
            try {
                Thread.sleep(patience/2);
            } catch(InterruptedException ignored) {

            }
        }
        waiting.set(null);

        return true;
    }

    public void stop()
    {
        timedOut();
    }

    public void feed()
    {
        if(!ticking.get())
        {
            return;
        }

        last.set(Math.ms());
    }

    private void timedOut()
    {
        if(!ticking.get())
        {
            return;
        }

        future.cancel(false);
        ticking.set(false);
        timeout.run();

        if(waiting.get() != null)
        {
            waiting.get().interrupt();
        }
    }
}
