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

package art.arcane.amulet.io;

import art.arcane.amulet.concurrent.J;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class ClassRegistry
{
    public static <T> Stream<T> implementing(Class<T> interfaceClass, Function<Class<?>, T> instantiator, Class<?>... jarClassess) {
        return J.attempt(() -> new JarLoader(jarClassess).all()
                .filter(interfaceClass::isAssignableFrom)
                .filter(i -> !i.isInterface()).map(instantiator));
    }

    public static <T> Stream<T> implementing(Class<T> interfaceClass, Function<Class<?>, T> instantiator) {
        return implementing(interfaceClass, instantiator, ClassRegistry.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> implementing(Class<T> interfaceClass) {
        return implementing(interfaceClass, (i) -> (T) i.construct(), ClassRegistry.class);
    }

    public static Stream<Class<?>> annotatedTypes(Class<? extends Annotation> annotationClass, Class<?>... jarClassess) {
        return J.attempt(() -> new JarLoader(jarClassess).all()
                .filter(i -> i.isAnnotationPresent(annotationClass)));
    }

    public static Stream<Class<?>> annotatedTypes(Class<? extends Annotation> annotationClass) {
        return annotatedTypes(annotationClass, ClassRegistry.class);
    }


    public static Stream<Class<?>> annotatedFields(Class<? extends Annotation> annotationClass, Class<?>... jarClassess) {
        return J.attempt(() -> new JarLoader(jarClassess).all()
                .mapMulti((Class<?> c, Consumer<Field> out) -> {
                    for(Field i : c.getDeclaredFields()) {
                        out.accept(i);
                    }
                })
                .filter(i -> i.isAnnotationPresent(annotationClass))
                .map(Field::getDeclaringClass));
    }

    public static Stream<Class<?>> annotatedFields(Class<? extends Annotation> annotationClass) {
        return annotatedFields(annotationClass, ClassRegistry.class);
    }

    public static Stream<Class<?>> annotatedMethods(Class<? extends Annotation> annotationClass, Class<?>... jarClassess) {
        return J.attempt(() -> new JarLoader(jarClassess).all()
                .mapMulti((Class<?> c, Consumer<Method> out) -> {
                    for(Method i : c.getDeclaredMethods()) {
                        out.accept(i);
                    }
                })
                .filter(i -> i.isAnnotationPresent(annotationClass))
                .map(Method::getDeclaringClass));
    }

    public static Stream<Class<?>> annotatedMethods(Class<? extends Annotation> annotationClass) {
        return annotatedMethods(annotationClass, ClassRegistry.class);
    }

    public static Stream<Class<?>> annotatedMembers(Class<? extends Annotation> annotationClass, Class<?>... jarClassess) {
        return J.attempt(() -> new JarLoader(jarClassess).all()
                .mapMulti((Class<?> c, Consumer<Class<?>> out) -> {
                    for(Method i : c.getDeclaredMethods()) {
                        if(i.isAnnotationPresent(annotationClass)) {
                            out.accept(i.getDeclaringClass());
                        }
                    }
                    for(Field i : c.getDeclaredFields()) {
                        if(i.isAnnotationPresent(annotationClass)) {
                            out.accept(i.getDeclaringClass());
                        }
                    }
                }));
    }

    public static Stream<Class<?>> annotatedMembers(Class<? extends Annotation> annotationClass) {
        return annotatedMembers(annotationClass, ClassRegistry.class);
    }
}
