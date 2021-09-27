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

import art.arcane.amulet.collections.Biset;

import java.util.List;

/**
 * Represents an infinite space of 2D data
 */
public interface Space<T> {
    /**
     * Unload & save modified nodes until the total count of nodes is at or under maxSize
     * This operation prioritizes the most stale nodes (least accessed) nodes first
     * @param maxSize the max size allowed after this operation
     * @param force if force is true, it will ensure it's trimmed to size even if
     *              it needs to unload non-stale entries
     * @return the amout of nodes trimmed.
     */
    int trimToSize(int maxSize, boolean force);

    /**
     * Unload & save modified nodes until the total count of nodes is at or under maxSize
     * This operation prioritizes the most stale nodes (least accessed) nodes first
     * @param maxSize the max size allowed after this operation
     * @return the amout of nodes trimmed.
     */
    default int trimToSize(int maxSize)
    {
        return trimToSize(maxSize, false);
    }

    /**
     * Unload & save modified nodes until it has unloaded the trim count. This operation
     * respects non-stale nodes and will not unload nodes that are still being used / arent stale
     * @param trimCount the max amount to trim
     * @return the actual trimmed nodes
     */
    int trim(int trimCount);

    T getOrNull(int x, int z);

    boolean isModified(int x, int z);

    /**
     * Get a copy of all loaded positions
     * @return the positions
     */
    List<Biset<Integer, Integer>> getPositions();

    /**
     * Get the count of loaded nodes
     * @return the nodes
     */
    int getSize();

    /**
     * Save all data without unloading
     */
    void saveAll();

    /**
     * Save any data that has changed, then unload everything
     */
    void unloadAll();

    /**
     * Save the node without unloading
     * @param x the x coord
     * @param z the z coord
     */
    void save(int x, int z);

    /**
     * Unload the node saving if needed
     * @param x the x coord
     * @param z the z coord
     */
    void unload(int x, int z);

    /**
     * Get the data at the location
     * @param x the x
     * @param z the z
     * @return the data
     */
    T get(int x, int z);

    /**
     * Data is needed at the given location, either load the data or provide a
     * blank container. Cannot be null! This operation is atomic, stay sync.
     * @param x the x coord
     * @param z the z coord
     * @return the data
     */
    T load(int x, int z);

    /**
     * Save the data before it's removed from this space. This operation is atomic, stay sync.
     * @param data the data to save
     * @param x the x coord
     * @param z the z coord
     */
    void save(T data, int x, int z);
}
