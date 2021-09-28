package art.arcane.amulet.io;

import manifold.ext.rt.api.Structural;

import java.io.*;

@Structural
public interface Writable {
    void writeData(DataOutputStream dos) throws IOException;
    void readData(DataInputStream din) throws IOException;

    default void readBytes(byte[] bytes) throws IOException
    {
        readStream(new ByteArrayInputStream(bytes));
    }

    default byte[] writeBytes() throws IOException
    {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        writeStream(boas);
        return boas.toByteArray();
    }

    default void writeStream(OutputStream out) throws IOException
    {
        if(out instanceof DataOutputStream dos)
        {
            writeData(dos);
        }

        else
        {
            writeData(new DataOutputStream(out));
        }
    }

    default void readStream(InputStream in) throws IOException
    {
        if(in instanceof DataInputStream din)
        {
            readData(din);
        }

        else
        {
            readData(new DataInputStream(in));
        }
    }
}
