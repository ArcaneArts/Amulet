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

package art.arcane.amulet.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static art.arcane.amulet.MagicalSugar.*;

public class JarLoader {
    private final Map<String, Supplier<Class<?>>> classCache = new HashMap<>();

    public static JarLoader from(Class<?> jarClass) throws IOException {
        return new JarLoader(jarClass);
    }

    public JarLoader(File... jarFiles) throws IOException {
        List<File> jars = jarFiles.toList();

        for(File i : jars)
        {
            FileInputStream fin = open i;
            ZipInputStream zip = new ZipInputStream(fin);

            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    if (entry.getName().contains("$")) {
                        continue;
                    }

                    String c = entry.getName().replaceAll("/", ".").replace(".class", "");
                    classCache.put(c, () -> {
                        try {
                            return Class.forName(c);
                        } catch (Throwable ignored) {

                        }

                        return null;
                    });
                }
            }

            zip.close();
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    public JarLoader(Class<?>... baseClasses) throws IOException {
        this(Arrays.stream(baseClasses)
                .map(i -> i.jarFile())
                .toArray(File[]::new));
    }

    public Stream<Class<?>> all()
    {
        return classCache.keySet().parallelStream()
                .map(i -> classCache.get(i).get());
    }

    public Stream<Class<?>> all(Class<?> superType)
    {
        return all().filter(superType::isAssignableFrom)
                .filter(i -> !i.equals(superType));
    }

    public Stream<Class<?>> inPackageNested(String superPackage, Class<?> superType)
    {
        return classCache.keySet().parallelStream()
                .filter(i -> i.startsWith(superPackage))
                .map(i -> classCache.get(i).get())
                .filter(i -> !i.equals(superType))
                .filter(superType::isAssignableFrom)
                .map(i -> (Class<?>)i);
    }

    public Stream<Class<?>> inPackageSpecifically(String superPackage, Class<?> superType)
    {
        return classCache.keySet().parallelStream()
                .filter(i -> i.startsWith(superPackage)
                        && i.splitAbs(".").toList().removeLast()
                        .equals(superPackage.splitAbs(".").toList()))
                .map(i -> classCache.get(i).get())
                .filter(i -> !i.equals(superType))
                .filter(superType::isAssignableFrom)
                .map(i -> (Class<?>)i);
    }

    public Stream<Class<?>> inPackageNested(String superPackage)
    {
        return classCache.keySet().parallelStream()
                .filter(i -> i.startsWith(superPackage))
                .map(i -> classCache.get(i).get());
    }

    public Stream<Class<?>> inPackageSpecifically(String superPackage)
    {
        return classCache.keySet().parallelStream()
                .filter(i -> i.startsWith(superPackage)
                        && i.splitAbs(".").toList().removeLast()
                        .equals(superPackage.splitAbs(".").toList()))
                .map(i -> classCache.get(i).get());
    }
}
