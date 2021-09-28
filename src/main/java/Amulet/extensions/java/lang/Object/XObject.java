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

package Amulet.extensions.java.lang.Object;

import art.arcane.amulet.io.Writable;
import art.arcane.amulet.logging.LogListener;
import com.google.gson.Gson;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.LogManager;

@Extension
public abstract class XObject implements Writable {
  private static final Gson gson = new Gson();


  public static void writeData(@This Object o, DataOutputStream dos) throws IOException
  {
      dos.writeUTF(o.getClass().getCanonicalName());
      dos.writeUTF(o.toJson());
  }

  public static void readData(@This Object o, DataInputStream din) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  public static String toJson(@This Object o) {
    return gson.toJson(o);
  }

  public static int identity(@This Object o) {
    return System.identityHashCode(o);
  }

  public static String typeName(@This Object o)
  {
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

  @Extension
  public static <T> T fromJson(String json, Class<? extends T> clazz)
  {
    return gson.fromJson(json, clazz);
  }
}