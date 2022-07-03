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

package art.arcane.amulet.io.nbt.objects;

import art.arcane.amulet.io.nbt.nbt.tag.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * order to be serialized to NBT format. This class will serialize all fields in the given object that are annotated
 *
 * @author Marius
 */
public class NBTObjectSerializer {
    /*
     * Minecraft NBT tag IDs.
     */
    public static final int NBT_TAG_END = 0;
    public static final int NBT_TAG_BYTE = 1;
    public static final int NBT_TAG_SHORT = 2;
    public static final int NBT_TAG_INT = 3;
    public static final int NBT_TAG_LONG = 4;
    public static final int NBT_TAG_FLOAT = 5;
    public static final int NBT_TAG_DOUBLE = 6;
    public static final int NBT_TAG_BYTE_ARRAY = 7;
    public static final int NBT_TAG_STRING = 8;
    public static final int NBT_TAG_LIST = 9;
    public static final int NBT_TAG_COMPOUND = 10;
    public static final int NBT_TAG_INT_ARRAY = 11;

    /**
     * @return The given instance represented as a serialized NBT data structure.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible.
     * @throws UnserializableClassException if an attempt is made to serialize a class that is
     *                                      not natively supported by the serializer and does not implement INBTSerializable.
     */
    public static final <T> CompoundTag serialize(T object) throws IllegalAccessException, UnserializableClassException {
        /*
         * First thing we need to do is create the NBT compound tag that will represent the
         * class instance in its serialized form.
         */
        CompoundTag t = new CompoundTag();
        /*
         * Then, we need to get a list of declared fields in the instance's class. We loop
         * over each of the fields and serialize any that are annotated with @NBTSerialize.
         */
        Class<?> definition = object.getClass();
        Field[] df = definition.getDeclaredFields();
        for (Field f : df) {
            if (!Modifier.isTransient(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                /*
                 * Any fields that are annotated with @NBTSerialize will need to be read, so
                 * we set them as accessible in case they are declared as private or
                 * protected.
                 */
                f.setAccessible(true);
                /*
                 * Get the value of the field and check if it is null. If it is, there is no
                 * need to serialize it, so we move on to the next field.
                 */
                Object fv = f.get(object);
                if (fv == null) continue;

                /*
                 * Not that we know that the value is not null, we get the name of the tag in
                 * the NBT structure that corresponds to the given field. If blank, use the
                 * name of the field itself.
                 */
                String tn = f.getName();
                /*
                 * Then, we get the declared class of the field.
                 */
                Class fc = f.getType();

                /*
                 * Check the assignability of the field against primitives. If any of these
                 * match, an NBT tag of the type corresponding to the primitive will be
                 * created and added to the NBT data structure with the value obtained from
                 * the field.
                 */
                if (fc.isAssignableFrom(byte.class)) t.putByte(tn, (Byte) fv);
                else if (fc.isAssignableFrom(boolean.class)) t.putBoolean(tn, (Boolean) fv);
                else if (fc.isAssignableFrom(short.class)) t.putShort(tn, (Short) fv);
                else if (fc.isAssignableFrom(int.class)) t.putInt(tn, (Integer) fv);
                else if (fc.isAssignableFrom(long.class)) t.putLong(tn, (Long) fv);
                else if (fc.isAssignableFrom(float.class)) t.putFloat(tn, (Float) fv);
                else if (fc.isAssignableFrom(double.class)) t.putDouble(tn, (Double) fv);

                    /*
                     * Then, check the assignability of the field against number classes, arrays
                     * and strings. If any of these match, an NBT tag of the type corresponding to
                     * the class of the field will be created and added to the NBT data structure
                     * with the value obtained from the field.
                     */
                else t.put(tn, objectToTag(fc, fv));
            }
        }
        /*
         * When serialization is done, return the completed NBT data structure.
         */
        return t;
    }

    /**
     * <p>Serializes the given {@link Collection} instance to an NBT list structure.</p>
     *
     * @param col A {@link Collection} instance.
     * @return The given instance represented as a serialized NBT list structure.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible.
     * @throws UnserializableClassException if an attempt is made to serialize a class that is
     *                                      not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static final <T> ListTag<?> serializeCollection(Collection<T> col) throws IllegalAccessException, UnserializableClassException {
        /*
         * First of all, create a blank NBT list tag to store our values. Unlike Java, NBT has
         * only one one type of list, namely NBTTagList. Java has different implementations of
         * Collection (e.g. ArrayList, LinkedList, Set, etc.), which makes deserialization
         * significantly harder than serialization.
         */

        if (col.size() <= 0) return ListTag.createUnchecked(EndTag.class);
        Class<T> subclass = (Class<T>) col.iterator().next().getClass();
        ListTag<Tag<?>> c = null;

        for (T element : col) {
            /*
             * Get a tag that represents this element in the list as an NBT tag.
             */
            Tag<?> tag = objectToTag(subclass, element);
            if (c == null) {
                c = new ListTag<>((Class<? super Tag<?>>) tag.getClass());
            }
            if (tag != null) {
                /*
                 * If a suitable tag type was found and a tag was created for the list
                 * element, add the element to the NBT list structure.
                 */
                c.add(tag);
            }
        }
        /*
         * When serialization is done, return the completed NBT list structure.
         */
        return c;
    }

    /**
     * <p>Serializes the given {@link Entry} instance to an NBT data structure.</p>
     *
     * @param entry An {@link Entry} instance.
     * @return The given instance represented as a serialized NBT entry list structure.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible.
     * @throws UnserializableClassException if an attempt is made to serialize a class that is
     *                                      not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static final <K, V> CompoundTag serializeEntry(Entry<K, V> entry) throws UnserializableClassException, IllegalAccessException {
        /*
         * We don't know what kind of elements the key/value pair contains, and instead of
         * using reflection sorcery (http://stackoverflow.com/a/14403515/1955334) to figure it
         * out, it's much easier and more more logical to just check the class of the key and
         * value of the entry.
         */
        Class<K> keyClass = (Class<K>) entry.getKey().getClass();
        Class<V> valueClass = (Class<V>) entry.getValue().getClass();

        /*
         * Pass this info on to the main entry serialization function, then return the
         * completed NBT map structure.
         */
        return serializeEntry(entry, keyClass, valueClass);
    }

    /**
     * <p>Serializes the given {@link Entry} instance to an NBT data structure, forcing the
     * key and value to be serialized as if they were instances of the given classes.</p>
     *
     * @param entry      An {@link Entry} instance.
     * @param keyClass   The class or a superclass of the entry key.
     * @param valueClass The class or a superclass of the entry value.
     * @return The given instance represented as a serialized NBT entry list structure.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible.
     * @throws UnserializableClassException if an attempt is made to serialize a class that is
     *                                      not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static final <K, V> CompoundTag serializeEntry(Entry<? extends K, ? extends V> entry, Class<K> keyClass, Class<V> valueClass) throws IllegalAccessException, UnserializableClassException {
        /*
         * First of all, create a blank NBT compound tag to store our key/value pair. The
         * child tags of this compound will contain the key and value of the pair.
         */
        CompoundTag te = new CompoundTag();

        /*
         * The key and value will be serialized as long as they are not null. If they are
         * null, the respective fragment of the pair will not be serialized, to indicate that
         * it is null.
         */
        if (entry.getKey() != null) {
            Tag<?> keyTag = objectToTag(keyClass, entry.getKey());
            te.put("k", keyTag);
        }
        if (entry.getValue() != null) {
            Tag<?> valueTag = objectToTag(valueClass, entry.getValue());
            te.put("v", valueTag);
        }

        /*
         * When serialization is done, return the completed NBT entry structure.
         */
        return te;
    }

    /**
     * <p>Serializes an object of the specified class into an NBT tag.</p>
     *
     * @param clazz A {@link Class} instance representing the class of the object.
     * @param obj   The object to serialize.
     * @return The given object represented as an NBT tag of the appropriate type.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class is inaccessible.
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static final <T, U extends T> Tag<?> objectToTag(Class<T> clazz, U obj) throws IllegalAccessException, UnserializableClassException {
        /*
         * First, check if the object is null. If it is, it should not be cast to a tag, so we
         * return null.
         */
        if (obj == null) return null;

        /*
         * Check the assignability of the class against number classes, arrays and strings. If
         * any of these match, an NBT tag of the type corresponding to the class of the list
         * will be created and added to the NBT list structure with the value obtained from
         * the list.
         */
        if (clazz.isAssignableFrom(Byte.class)) return new ByteTag((Byte) obj);
        else if (clazz.isAssignableFrom(Boolean.class)) return new ByteTag(((Boolean) obj) ? (byte) 1 : (byte) 0);
        else if (clazz.isAssignableFrom(Short.class)) return new ShortTag((Short) obj);
        else if (clazz.isAssignableFrom(Integer.class)) return new IntTag((Integer) obj);
        else if (clazz.isAssignableFrom(Long.class)) return new LongTag((Long) obj);
        else if (clazz.isAssignableFrom(Float.class)) return new FloatTag((Float) obj);
        else if (clazz.isAssignableFrom(Double.class)) return new DoubleTag((Double) obj);
        else if (clazz.isAssignableFrom(byte[].class)) return new ByteArrayTag((byte[]) obj);
        else if (clazz.isAssignableFrom(Byte[].class)) return new ByteArrayTag(ArrayUtils.toPrimitive((Byte[]) obj));
        else if (clazz.isAssignableFrom(String.class)) return new StringTag((String) obj);
        else if (clazz.isAssignableFrom(int[].class)) return new IntArrayTag((int[]) obj);
        else if (clazz.isAssignableFrom(Integer[].class))
            return new IntArrayTag(ArrayUtils.toPrimitive((Integer[]) obj));
        else if (Collection.class.isAssignableFrom(clazz)) return serializeCollection((Collection) obj);
        else if (Entry.class.isAssignableFrom(clazz)) return serializeEntry((Entry) obj);
        else if (Map.class.isAssignableFrom(clazz)) return serializeCollection(((Map) obj).entrySet());
        else {
            return serialize(obj);
        }
    }

    /**
     * instance. Deserialized objects will contain values from the NBT structure for all
     * deserialized annotated fields where the corresponding tag is available in the NBT
     * structure.</p>
     * <p><b>Note:</b> If an NBT tag is not found for a corresponding field of the given
     * serializable class, that field will be instantiated as {@code null}.</p>
     * <p>
     * deserialization.
     *
     * @param data The NBT data structure to deserialize.
     * @return A deserialized instance of the given class definition.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class or {@link List} is inaccessible.
     * @throws InstantiationException       if a serializable or {@link List} class represents
     *                                      an abstract class, an interface, an array class, a primitive type, or void; or if the
     *                                      class has no nullary constructor; or if the instantiation fails for some other reason.
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    public static final <T> T deserialize(Class<T> definition, CompoundTag data) throws IllegalAccessException, InstantiationException, UnserializableClassException {
        /*
         * The first thing we need to do is create an instance of the class that we want to
         * deserialize to. When using this method, the class MUST have a nullary constructor,
         * as we otherwise would have no idea what kind of arguments to pass to the
         * constructor.
         */
        T instance = definition.newInstance();
        /*
         * This instance will then be funneled into the main deserialization function.
         */
        deserialize(instance, data, true);
        /*
         * Finally, return this instance.
         */
        return instance;
    }

    /**
     * instance. The given instance will be overwritten with values from the NBT structure for
     * all deserialized annotated fields where the corresponding tag is available in the NBT
     * structure.</p>
     * <p><b>Note:</b> If an NBT tag is not found for a corresponding field of the given
     * serializable class, that field will not be overwritten unless the
     * interpretMissingFieldValuesAsNull argument is set to {@code true}, in which case the
     * field is set to {@code null}.</p>
     * <p>
     * deserialization.
     *
     * @param data                              The NBT data structure to deserialize.
     * @param interpretMissingFieldValuesAsNull Whether fields in the instance for which there
     *                                          is no corresponding NBT tag in the given data structure should be set to {@code null}
     *                                          or left as-is.
     * @return A deserialized instance of the given class definition.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class is inaccessible.
     * @throws InstantiationException       if a serializable class represents an abstract class,
     *                                      an interface, an array class, a primitive type, or void; or if the class has no nullary
     *                                      constructor; or if the instantiation fails for some other reason. If this exception is
     *                                      thrown when deserializing, you should consider specifying the {@code typeOverride}
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    public static final <T> void deserialize(T instance, CompoundTag data, boolean interpretMissingFieldValuesAsNull) throws IllegalAccessException, InstantiationException, UnserializableClassException {
        /*
         * First of all, we need to get a list of declared fields in the instance's class. We
         * then loop over each of the fields and process any that are annotated with
         * @NBTSerialize.
         */
        Field[] df = instance.getClass().getDeclaredFields();
        for (Field f : df) {
            if (!Modifier.isTransient(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                /*
                 * Once an annotated field is found, get the name of the tag in the NBT
                 * structure that corresponds to the given field. If blank, use the name of
                 * the field itself.
                 */
                String tn = f.getName();
                /*
                 * If the tag is not present in the NBT data structure, decide what to do with
                 * it - either ignore it and move on, or set it to null, depending on what is
                 * specified through the interpretMissingFieldValuesAsNull argument.
                 */
                if (!data.containsKey(tn)) {
                    if (interpretMissingFieldValuesAsNull) {
                        /*
                         * The developer has specified that they want missing tags to default
                         * to null in the class instance, so we set the field as accessible
                         * (in case it is private or protected) before setting its value to
                         * null.
                         */
                        f.setAccessible(true);
                        f.set(instance, null);
                    }
                    /*
                     * Finally, we continue to the next field.
                     */
                    continue;
                }
                /*
                 * If we reach this point, a tag that corresponds to the given field was found
                 * in the NBT data structure. No matter what happens now, the value of that
                 * field will end up overwritten, so we set it as accessible in case it is
                 * declared private or protected.
                 */
                f.setAccessible(true);
                /*
                 * Then, we get the declared class of the field. Override values from
                 * NBTSerialize will override this.
                 */
                Class<?> fc = f.getType();


                /*
                 * Check the assignability of the field against primitives. If any of these
                 * match, the value of the field will be set to a corresponding, valid
                 * instance of the primitive that matches from the NBT data structure.
                 */
                if (fc.isAssignableFrom(byte.class)) f.setByte(instance, data.getByte(tn));
                else if (fc.isAssignableFrom(boolean.class)) f.setBoolean(instance, data.getBoolean(tn));
                else if (fc.isAssignableFrom(short.class)) f.setShort(instance, data.getShort(tn));
                else if (fc.isAssignableFrom(int.class)) f.setInt(instance, data.getInt(tn));
                else if (fc.isAssignableFrom(long.class)) f.setLong(instance, data.getLong(tn));
                else if (fc.isAssignableFrom(float.class)) f.setFloat(instance, data.getFloat(tn));
                else if (fc.isAssignableFrom(double.class)) f.setDouble(instance, data.getDouble(tn));

                    /*
                     * Then, check the assignability of the field against number classes, arrays
                     * and strings. If any of these match, the value of the field will be set to a
                     * corresponding, valid instance of the class that matches from the NBT data
                     * structure.
                     */
                else f.set(instance, tagToObject(data.get(tn), fc, f.getGenericType()));
            }
        }
    }

    /**
     * <p>Deserializes an NBT list structure into a {@link Collection} instance.</p>
     *
     * @param list     The NBT list structure to deserialize.
     * @param colClass A {@link Class} instance representing the subclass of
     *                 {@link Collection} that the NBT list structure should be deserialized to.
     * @param subclass A {@link Class} instance representing the class of the elements in
     *                 the {@link Collection} definition.
     * @param subtype  A {@link Type} instance representing the type of the elements in the
     *                 {@link Collection} definition.
     * @return A deserialized {@link Collection} instance of the given subclass.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class is inaccessible.
     * @throws InstantiationException       if a serializable class represents an abstract class,
     *                                      an interface, an array class, a primitive type, or void; or if the class has no nullary
     *                                      constructor; or if the instantiation fails for some other reason.
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static <T> Collection<T> deserializeCollection(ListTag<?> list, Class<? extends Collection> colClass, Class<T> subclass, Type subtype) throws InstantiationException, IllegalAccessException, UnserializableClassException {
        /*
         * The reason we need both a Class and a List instance when deserializing here is that
         * we need to be able to cast objects directly into an instance of the subclass
         * specified. A Class instance may be parameterized, but a Type may not be, so passing
         * only the Type will not allow us to determine the generic class T. Furthermore, we
         * cannot cast using Class.cast because that method returns an Object instance, not an
         * instance of T. T allows us to cast to the subclass explicitly.
         */
        /*
         * The first thing we need to do is create an instance of the List class that the
         * field in the serializable class mandates. This will be passed on and eventually
         * assigned to the field (or, in the case of a nested object, added to the parent
         * object).
         */
        Collection<T> c = colClass.newInstance();

        /*
         * For each of the elements in the collection, deserialize the element into an object.
         * We do not need to separately handle primitives here because primitives cannot be
         * used as generics.
         */
        for (int i = 0; i < list.size(); i++) {
            c.add(tagToObject(list.get(i), subclass, subtype));
        }

        /*
         * When deserialization is complete, return the completed list.
         */
        return c;
    }

    /**
     * <p>Deserializes an NBT entry set structure into a {@link Map} instance.</p>
     *
     * @param map        The NBT entry set structure to deserialize.
     * @param mapClass   A {@link Class} instance representing the subclass of
     *                   {@link Map} that the NBT entry set structure should be deserialized to.
     * @param keyClass   A {@link Class} instance representing the class of the keys in the
     *                   {@link Map} definition.
     * @param keyType    A {@link Type} instance representing the type of the keys in the
     *                   {@link Map} definition.
     * @param valueClass A {@link Class} instance representing the class of the values in the
     *                   {@link Map} definition.
     * @param valueType  A {@link Type} instance representing the type of the values in the
     *                   {@link Map} definition.
     * @return A deserialized {@link Map} instance of the given subclass.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class is inaccessible.
     * @throws InstantiationException       if a serializable class represents an abstract class,
     *                                      an interface, an array class, a primitive type, or void; or if the class has no nullary
     *                                      constructor; or if the instantiation fails for some other reason.
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static <K, V> Map<K, V> deserializeMap(ListTag<?> map, Class<? extends Map> mapClass, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) throws InstantiationException, IllegalAccessException, UnserializableClassException {
        /*
         * The reason we need both a Class and a List instance when deserializing here is that
         * we need to be able to cast objects directly into an instance of the subclass
         * specified. A Class instance may be parameterized, but a Type may not be, so passing
         * only the Type will not allow us to determine the generic class T. Furthermore, we
         * cannot cast using Class.cast because that method returns an Object instance, not an
         * instance of T. T allows us to cast to the subclass explicitly.
         */
        /*
         * The first thing we need to do is create an instance of the Map class that the field
         * in the serializable class mandates. This will be passed on and eventually assigned
         * to the field (or, in the case of a nested object, added to the parent object).
         */
        Map<K, V> e = mapClass.newInstance();

        for (int i = 0; i < map.size(); i++) {
            /*
             * Deserialize the key and the value into objects. We do not need to separately handle
             * primitives here because primitives cannot be used as generics. We need to check if
             * the key and/or value is actually present; if not, assign null to the element.
             */
            K key;
            V value;
            CompoundTag kvp = (CompoundTag) map.get(i);

            if (kvp.containsKey("k")) {
                key = tagToObject(kvp.get("k"), keyClass, keyType);
            } else {
                key = null;
            }
            if (kvp.containsKey("v")) {
                value = tagToObject(kvp.get("v"), valueClass, valueType);
            } else {
                value = null;
            }

            /*
             * Put the data into the map instance.
             */
            e.put(key, value);
        }

        /*
         * When deserialization is complete, return the completed map.
         */
        return e;
    }

    /**
     * <p>Deserializes an NBT tag into an object instance of the specified class.</p>
     *
     * @param tag     The NBT tag to deserialize.
     * @param clazz   A {@link Class} instance representing the class of the deserialized object.
     * @param subtype A {@link Type} instance representing the type of the deserialized object.
     * @return The given NBT tag represented as an object of the given type.
     * @throws IllegalAccessException       if a Field object in a serializable class is enforcing
     *                                      Java language access control and the underlying field is inaccessible, or if the
     *                                      constructor for a serializable class is inaccessible.
     * @throws InstantiationException       if a serializable class represents an abstract class,
     *                                      an interface, an array class, a primitive type, or void; or if the class has no nullary
     *                                      constructor; or if the instantiation fails for some other reason.
     * @throws UnserializableClassException if an attempt is made to deserialize a class that
     *                                      is not natively supported by the serializer and does not implement INBTSerializable.
     */
    private static <T> T tagToObject(Tag<?> tag, Class<T> clazz, Type subtype) throws IllegalAccessException, InstantiationException, UnserializableClassException {
        /*
         * Handle special cases: These classes will cause class cast exceptions in most
         * circumstances (because they are a superclass or interface of any of the below
         * classes). They can't be serialized anyway, so discard them.
         */
        if (clazz.isAssignableFrom(Object.class) || clazz.isAssignableFrom(Number.class)
                || clazz.isAssignableFrom(CharSequence.class) || clazz.isAssignableFrom(Serializable.class)
                || clazz.isAssignableFrom(Comparable.class))

            throw new UnserializableClassException(clazz);
        /*
         * Check the assignability of the subclass against number classes, arrays and
         * strings. If any of these match, a valid instance of that class will be created
         * from an NBT tag of the corresponding type from the NBT list structure.
         */
        if (clazz.isAssignableFrom(Byte.class)) return (T) Byte.valueOf(((ByteTag) tag).getValue());
        else if (clazz.isAssignableFrom(Boolean.class)) return (T) Boolean.valueOf(((ByteTag) tag).getValue() != 0);
        else if (clazz.isAssignableFrom(Short.class)) return (T) Short.valueOf(((ShortTag) tag).getValue());
        else if (clazz.isAssignableFrom(Integer.class)) return (T) Integer.valueOf(((IntTag) tag).getValue());
        else if (clazz.isAssignableFrom(Long.class)) return (T) Long.valueOf(((LongTag) tag).getValue());
        else if (clazz.isAssignableFrom(Float.class)) return (T) Float.valueOf(((FloatTag) tag).getValue());
        else if (clazz.isAssignableFrom(Double.class)) return (T) Double.valueOf(((DoubleTag) tag).getValue());
        else if (clazz.isAssignableFrom(byte[].class)) return (T) ((ByteArrayTag) tag).getValue();
        else if (clazz.isAssignableFrom(Byte[].class)) return (T) ArrayUtils.toObject(((ByteArrayTag) tag).getValue());
        else if (clazz.isAssignableFrom(String.class)) return (T) ((StringTag) tag).getValue();
        else if (clazz.isAssignableFrom(int[].class)) return (T) ((IntArrayTag) tag).getValue();
        else if (clazz.isAssignableFrom(Integer[].class))
            return (T) ArrayUtils.toObject(((IntArrayTag) tag).getValue());
            /*
             * Lists and other serializable classes require special treatment on
             * deserialization. Many classes can subclass java.util.List, including user-
             * defined ones, so we need to check if a List can be assigned from the field
             * type instead of checking if the field can be assigned from List (which it
             * in most cases cannot, e.g. if the field is of type ArrayList, you can't
             * assign a List instance to it). The same goes for INBTSerializable
             * instances. An INBTSerializable instance cannot be assigned to an
             * INBTSerializable subclass field.
             */
        else if (Collection.class.isAssignableFrom(clazz)) {
            /*
             * We need to figure out what type the list contains. This is important,
             * so that we get the right type object added to the list and so that the
             * correct type of value is extracted from the NBT list structure.
             */
            Type listType = ((ParameterizedType) subtype).getActualTypeArguments()[0];
            /*
             * We also need to get a Class instance that corresponds to this Type.
             * This is primarily done to be able to directly and explicitly cast
             * values obtained from the NBT structure into the type that the List
             * holds, but also to be able to look up the correct NBT tag type for
             * the given class.
             */
            Class<?> lct;
            /*
             * It may be that the types of the list elements themselves are
             * parameterized. This case must be handled differently from non-
             * parameterized types because they cannot be explicitly casted to Class
             * instances. We need to get the raw type from the parameterized type,
             * which in turn can be casted to a Class.
             */
            if (listType instanceof ParameterizedType) {
                lct = (Class<?>) ((ParameterizedType) listType).getRawType();
            } else {
                lct = (Class<?>) listType;
            }
            /*
             * Then, cast the list element that contains the collection.
             */
            ListTag<?> ntl = (ListTag<?>) tag;
            /*
             * Finally, the list may be deserialized.
             */
            Collection c2 = deserializeCollection(ntl, (Class<? extends Collection>) clazz, lct, listType);
            return (T) c2;
        } else if (Map.class.isAssignableFrom(clazz)) {
            /*
             * We need to figure out the type of both the key and the value of the entry.
             * This is important, so that we get the right type of objects as the key and
             * value of the key/value pair and so that the correct type of value is extracted
             * from the NBT entry structure.
             */
            Type[] types = ((ParameterizedType) subtype).getActualTypeArguments();
            Type keyType = types[0];
            Type valueType = types[1];
            /*
             * We also need to get a Class instance that corresponds to these Types.
             * This is primarily done to be able to directly and explicitly cast
             * values obtained from the NBT structure into the types that the Map
             * holds, but also to be able to look up the correct NBT tag types for
             * the given classes.
             */
            Class<?> keyClass;
            Class<?> valueClass;
            /*
             * It may be that the keys and values of the map elements themselves are
             * parameterized. This case must be handled differently from non-
             * parameterized types because they cannot be explicitly casted to Class
             * instances. We need to get the raw types from the parameterized types,
             * which in turn can be casted to Class instances.
             */
            if (keyType instanceof ParameterizedType) {
                keyClass = (Class<?>) ((ParameterizedType) keyType).getRawType();
            } else {
                keyClass = (Class<?>) keyType;
            }
            if (valueType instanceof ParameterizedType) {
                valueClass = (Class<?>) ((ParameterizedType) valueType).getRawType();
            } else {
                valueClass = (Class<?>) valueType;
            }

            /*
             * Then, cast the list element that contains the map.
             */
            ListTag<?> ntl = (ListTag<?>) tag;
            /*
             * Finally, the map may be deserialized.
             */
            Map c2 = deserializeMap(ntl, (Class<? extends Map>) clazz, keyClass, keyType, valueClass, valueType);
            return (T) c2;
        } else {
            /*
             * INBTSerializable instances are easy to deserialize, as they can
             * just recursively be passed back into the main deserialization function.
             */
            CompoundTag ntc = (CompoundTag) tag;
            return (T) deserialize(clazz, ntc);
        }
    }

    /**
     * <p>Returns the NBT tag ID that corresponds to the given Java {@link Class}.
     *
     * @param clazz The {@link Class} to match against an NBT tag ID.
     * @return An NBT tag ID.
     */
    private static int getIDFromClass(Class<?> clazz) {
        /*
         * Here, we just check assignability of the class against the various supported NBT
         * tag types to pick the one that is best fit for serializing the given class.
         */
        if (clazz.isAssignableFrom(byte.class) || clazz.isAssignableFrom(Byte.class) ||
                clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class)) {
            return NBT_TAG_BYTE;
        } else if (clazz.isAssignableFrom(short.class) || clazz.isAssignableFrom(Short.class)) return NBT_TAG_SHORT;
        else if (clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class)) return NBT_TAG_INT;
        else if (clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class)) return NBT_TAG_LONG;
        else if (clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class)) return NBT_TAG_FLOAT;
        else if (clazz.isAssignableFrom(double.class) || clazz.isAssignableFrom(Double.class)) return NBT_TAG_DOUBLE;
        else if (clazz.isAssignableFrom(byte[].class) || clazz.isAssignableFrom(Byte[].class))
            return NBT_TAG_BYTE_ARRAY;
        else if (clazz.isAssignableFrom(String.class)) return NBT_TAG_STRING;
        else if (clazz.isAssignableFrom(int[].class) || clazz.isAssignableFrom(Integer[].class))
            return NBT_TAG_INT_ARRAY;
        else if (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) return NBT_TAG_LIST;

        return NBT_TAG_COMPOUND;
    }
}
