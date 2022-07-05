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

package Amulet.extensions.java.lang.Object;

import art.arcane.amulet.logging.LogListener;
import art.arcane.amulet.profiling.Profiler;
import art.arcane.nbtson.io.NBTObjectSerializer;
import art.arcane.nbtson.io.SNBTSerializer;
import art.arcane.nbtson.io.UnserializableClassException;
import art.arcane.nbtson.tag.CompoundTag;
import art.arcane.nbtson.tag.Tag;
import com.google.gson.Gson;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.IOException;

@Extension
public abstract class XObject {
    private static final Gson gson = new Gson();

    public static String toSNBT(@This Object o) {
        try {
            if(o instanceof Tag<?> t) {
                return new SNBTSerializer().toString(t);
            }

            return new SNBTSerializer().toString(toNBT(o));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompoundTag toNBT(@This Object o) {
        try {
            return NBTObjectSerializer.serialize(o);
        } catch (IllegalAccessException | UnserializableClassException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(@This Object o) {
        return gson.toJson(o);
    }

    public static int identity(@This Object o) {
        return System.identityHashCode(o);
    }

    public static void profileStart(@This Object o, String key) {
        Profiler.begin(key);
    }

    public static void profileEnd(@This Object o, String key) {
        Profiler.end(key);
    }

    public static double getProfileAverage(@This Object o, String key) {
        return Profiler.get(key);
    }

    public static String typeName(@This Object o) {
        return o.getClass().simpleName();
    }

    public static void i(@This Object o, Object msg) {
        LogListener.logger().i(o.typeName(), msg);
    }

    public static void f(@This Object o, Object msg) {
        LogListener.logger().f(o.typeName(), msg);
    }

    public static void w(@This Object o, Object msg) {
        LogListener.logger().w(o.typeName(), msg);
    }

    public static void d(@This Object o, Object msg) {
        LogListener.logger().d(o.typeName(), msg);
    }

    public static void ex(@This Object o, Throwable e) {
        for (String i : e.printAsStrings()) {
            LogListener.logger().f(o.typeName(), i);
        }
    }
}