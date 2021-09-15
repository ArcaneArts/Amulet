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

package Amulet.extensions.java.lang.Class;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.File;
import java.lang.Class;
import java.lang.reflect.InvocationTargetException;

@Extension
public class XClass {
  public static <T> String simpleName(@This Class<T>  thiz) {
    return thiz.getSimpleName();
  }

  public static <T> File jarFile(@This Class<T>  thiz) {
    return new File(thiz.getProtectionDomain().getCodeSource().getLocation().getFile());
  }

  public static <T> T construct(@This Class<T>  thiz) {
    try {
      return thiz.getConstructor().access().newInstance();
    } catch (Throwable ignored) {

    }

    return null;
  }

  public static <T> T forceConstruct(@This Class<T>  thiz) {
    try {
      return thiz.getConstructor().access().newInstance();
    } catch (Throwable ignored) {
      try {
        return thiz.newInstance();
      } catch (Throwable ignored1) {

      }
    }

    return null;
  }
}