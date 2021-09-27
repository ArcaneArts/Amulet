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

package art.arcane.amulet.logging;

import java.util.concurrent.atomic.AtomicReference;

public interface LogListener {
    AtomicReference<LogListener> listener = new AtomicReference<>(new LogListener() {
        @Override
        public void i(String tag, Object f) {
            System.out.println("[I/" + tag + "]: " + f);
        }

        @Override
        public void f(String tag, Object f) {
            System.out.println("[F/" + tag + "]: " + f);
        }

        @Override
        public void w(String tag, Object f) {
            System.out.println("[W/" + tag + "]: " + f);
        }

        @Override
        public void d(String tag, Object f) {
            System.out.println("[D/" + tag + "]: " + f);
        }
    });

    static LogListener logger()
    {
        return listener.get();
    }

    void i(String tag, Object f);
    void f(String tag, Object f);
    void w(String tag, Object f);
    void d(String tag, Object f);
}
