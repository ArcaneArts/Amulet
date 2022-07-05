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

package art.arcane.amulet.test.unit;

import art.arcane.amulet.io.nbt.nbt.io.SNBTDeserializer;
import art.arcane.amulet.io.nbt.nbt.tag.CompoundTag;
import art.arcane.amulet.io.nbt.objects.NBTObjectSerializer;
import art.arcane.amulet.io.nbt.objects.UnserializableClassException;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NBTTests {
    @Test
    public void testNBT() throws UnserializableClassException, IllegalAccessException, InstantiationException {
        NBTTestObject test = new NBTTestObject();
        CompoundTag tag = test.toNBT();
        assertEquals(tag, NBTObjectSerializer.deserialize(NBTTestObject.class, tag).toNBT());
    }

    @Test
    public void testSNBT() throws IOException {
        NBTTestObject test = new NBTTestObject();
        CompoundTag tag = test.toNBT();
        assertEquals(tag.toSNBT(), new SNBTDeserializer().fromString(tag.toSNBT()).toSNBT());
    }

    @Data
    public static class NBTTestObject
    {
        private int aint = 0;
        private double adouble = 0;
        private short ashort = 0;
        private byte abyte = 0;
        private long along = 0;
        private String astring = "str";
        private NBTTestObjectSub sub = new NBTTestObjectSub();
    }

    @Data
    public static class NBTTestObjectSub
    {
        private int aint = 0;
        private double adouble = 0;
        private short ashort = 0;
        private byte abyte = 0;
        private long along = 0;
        private String astring = "str";
    }
}
