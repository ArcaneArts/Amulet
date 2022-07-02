package art.arcane.amulet.io.nbt.nbt.io;

import art.arcane.amulet.io.nbt.nbt.tag.Tag;

import java.io.IOException;

public interface NBTInput {

	NamedTag readTag(int maxDepth) throws IOException;

	Tag<?> readRawTag(int maxDepth) throws IOException;
}
