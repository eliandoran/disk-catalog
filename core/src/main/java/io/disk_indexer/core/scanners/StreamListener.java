package io.disk_indexer.core.scanners;

import java.nio.channels.SeekableByteChannel;

import io.disk_indexer.core.model.Entry;

public interface StreamListener {
	boolean needsStream(Entry entry);

	void receiveStream(Entry entry, SeekableByteChannel byteStream);
}
