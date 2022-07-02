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

/**
 * <p>An exception that is thrown whenever {@link NBTObjectSerializer} attempts to serialize or
 * deserialize a class that it cannot handle.</p>
 * 
 * @author Marius
 */
public class UnserializableClassException extends Exception {
	/**
	 * The class on which serialization was attempted.
	 */
	private final Class<?> clazz;
	
	public UnserializableClassException(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * Gets the class that {@link NBTObjectSerializer} failed to serialize or deserialize.
	 * @return The {@link Class} instance that caused the error
	 */
	public Class<?> getOffendingClass() {
		return this.clazz;
	}
}
