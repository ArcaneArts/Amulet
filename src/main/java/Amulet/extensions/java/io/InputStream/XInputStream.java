package Amulet.extensions.java.io.InputStream;

import art.arcane.amulet.data.VarInt;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.IOException;
import java.io.InputStream;

@Extension
public class XInputStream {
  public static int writeVarInt(@This InputStream self) throws IOException {
    return VarInt.readVInt(self);
  }

  public static long writeVarLong(@This InputStream self) throws IOException {
    return VarInt.readVLong(self);
  }
}