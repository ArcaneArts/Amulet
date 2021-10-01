package Amulet.extensions.manifold.rt.api.Array;

import art.arcane.amulet.io.IO;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Extension
public class XArray {


  public static byte[] fromBase64(@This Object self)
  {
    if(self instanceof byte[] b)
    {
      return Base64.getUrlDecoder().decode(b);
    }

    throw new UnsupportedOperationException("This method only supports byte arrays!");
  }

  public static String fromBase64String(@This Object self)
  {
    if(self instanceof byte[] b)
    {
      return new String(Base64.getUrlDecoder().decode(b), StandardCharsets.UTF_8);
    }

    throw new UnsupportedOperationException("This method only supports byte arrays!");
  }

  /**
   * Byte Arrays only. Converts these bytes into a hex string
   * @return the hex string representing these bytes
   */
  public static String hex(@This Object self) {
    if(self instanceof byte[] b)
    {
      return IO.bytesToHex(b);
    }

    throw new UnsupportedOperationException("This method only supports byte arrays!");
  }

  /**
   * Byte Arrays Only. Converts this into a bytearrayinputstream
   * @return the input stream
   */
  public static InputStream read(@This Object self) {
    if(self instanceof byte[] b)
    {
      return new ByteArrayInputStream(b);
    }

    throw new UnsupportedOperationException("This method only supports byte arrays!");
  }
}