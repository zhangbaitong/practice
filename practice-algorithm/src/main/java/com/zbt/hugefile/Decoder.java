package com.zbt.hugefile;

import java.nio.ByteBuffer;

public interface Decoder<T> {
	public T decode(ByteBuffer buffer);
}
