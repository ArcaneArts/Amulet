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

import com.google.gson.Gson;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.lang.Object;

@Extension
public class XObject {
  private static final Gson gson = new Gson();

  public static String toJson(@This Object o) {
    return gson.toJson(o);
  }

  @Extension
  public static <T> T fromJson(String json, Class<? extends T> clazz)
  {
    return gson.fromJson(json, clazz);
  }
}