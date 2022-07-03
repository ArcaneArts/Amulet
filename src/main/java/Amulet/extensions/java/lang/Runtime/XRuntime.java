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

package Amulet.extensions.java.lang.Runtime;

import art.arcane.amulet.util.Platform;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.File;

@Extension
public class XRuntime {
    @Extension
    public static Runtime get() {
        return Runtime.getRuntime();
    }

    public static double getCPULoad(@This Runtime rt) {
        return Platform.CPU.getCPULoad();
    }

    public static long getTotalSystemMemory(@This Runtime rt) {
        return Platform.MEMORY.PHYSICAL.getTotalMemory();
    }

    public static long getUsedSystemMemory(@This Runtime rt) {
        return Platform.MEMORY.PHYSICAL.getUsedMemory();
    }

    public static long getFreeSystemMemory(@This Runtime rt) {
        return Platform.MEMORY.PHYSICAL.getUsedMemory();
    }

    public static long getTotalSystemSwap(@This Runtime rt) {
        return Platform.MEMORY.VIRTUAL.getTotalMemory();
    }

    public static long getUsedSystemSwap(@This Runtime rt) {
        return Platform.MEMORY.VIRTUAL.getUsedMemory();
    }

    public static long getFreeSystemSwap(@This Runtime rt) {
        return Platform.MEMORY.VIRTUAL.getUsedMemory();
    }

    public static long getFreeDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getFreeSpace();
    }

    public static long getFreeDiskSpace(@This Runtime rt, File root) {
        return Platform.STORAGE.getFreeSpace(root);
    }

    public static long getUsedDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getUsedSpace();
    }

    public static long getUsedDiskSpace(@This Runtime rt, File root) {
        return Platform.STORAGE.getUsedSpace(root);
    }

    public static long getTotalDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getTotalSpace();
    }

    public static long getTotalDiskSpace(@This Runtime rt, File root) {
        return Platform.STORAGE.getTotalSpace(root);
    }

    public static long getAbsoluteTotalDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getAbsoluteTotalSpace();
    }

    public static long getAbsoluteFreeDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getAbsoluteFreeSpace();
    }

    public static long getAbsoluteUsedDiskSpace(@This Runtime rt) {
        return Platform.STORAGE.getAbsoluteUsedSpace();
    }

    public static String getJavaVendor(@This Runtime rt) {
        return Platform.ENVIRONMENT.getJavaVendor();
    }

    public static String getJavaHome(@This Runtime rt) {
        return Platform.ENVIRONMENT.getJavaHome();
    }

    public static String getJavaVersion(@This Runtime rt) {
        return Platform.ENVIRONMENT.getJavaVersion();
    }

    public static double getProcessCPULoad(@This Runtime rt) {
        return Platform.CPU.getLiveProcessCPULoad();
    }
}