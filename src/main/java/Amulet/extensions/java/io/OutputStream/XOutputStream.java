package Amulet.extensions.java.io.OutputStream;

import art.arcane.amulet.data.VarInt;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.IOException;
import java.io.OutputStream;

@Extension
public class XOutputStream {
  public static void writeVarInt(@This OutputStream self, int v) throws IOException {
    VarInt.writeVInt(self, v);
  }
  public static void writeVarLong(@This OutputStream self, long v) throws IOException {
    VarInt.writeVLong(self, v);
  }
}