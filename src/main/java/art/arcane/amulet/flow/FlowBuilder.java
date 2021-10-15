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

import java.util.Iterator;

public class FlowBuilder<T> implements Flow<T> {
    private final Flow<T> flow;
    private final Iterator<T> pending;

    public FlowBuilder(Iterator<T> pending)
    {
        this.flow = new DirectFlow<>();
        this.pending = pending;
    }

    public Flow<T> open()
    {
        flow.emit(pending);
        return flow;
    }

    @Override
    public Flow<T> emit(T t) {
        return flow.emit(t);
    }

    @Override
    public Flow<T> emit(Iterable<T> t) {
        return flow.emit(t);
    }

    @Override
    public BoundFlowListener<T> bindListen(FlowListener<T> listener) {
        return flow.bindListen(listener);
    }

    @Override
    public void close() {

    }

    @Override
    public void closeListeners() {
        flow.closeListeners();
    }

    @Override
    public void close(int id) {
        flow.close(id);
    }
}
