package Amulet.extensions.java.io.DataInputStream;

import art.arcane.amulet.data.Varint;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@Extension
public class XDataInputStream {
  public static int readVarInt(@This DataInputStream self) throws IOException {
    return Varint.readSignedVarInt(self);
  }

  public static long readVarLong(@This DataInputStream self) throws IOException {
    return Varint.readSignedVarLong(self);
  }

  public static int readUnsignedVarInt(@This DataInputStream self) throws IOException {
    return Varint.readUnsignedVarInt(self);
  }

  public static long readUnsignedVarLong(@This DataInputStream self) throws IOException {
    return Varint.readUnsignedVarLong(self);
  }
}