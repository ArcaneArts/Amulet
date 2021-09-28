package Amulet.extensions.java.io.DataOutputStream;

import art.arcane.amulet.data.Varint;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Extension
public class XDataOutputStream {
  public static void writeVarInt(@This DataOutputStream self, int i) throws IOException {
    Varint.writeSignedVarInt(i, self);
  }

  public static void writeVarLong(@This DataOutputStream self, long i) throws IOException {
    Varint.writeSignedVarLong(i, self);
  }

  public static void writeUnsignedVarInt(@This DataOutputStream self, int i) throws IOException {
    Varint.writeUnsignedVarInt(i, self);
  }

  public static void writeUnsignedVarLong(@This DataOutputStream self, long i) throws IOException {
    Varint.writeUnsignedVarLong(i, self);
  }
}