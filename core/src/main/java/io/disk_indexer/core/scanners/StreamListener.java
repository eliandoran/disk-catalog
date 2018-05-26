package io.disk_indexer.core.scanners;

import java.io.InputStream;

import io.disk_indexer.core.model.Entry;

public interface StreamListener {
	boolean needsStream(Entry entry);
	
	void receiveStream(Entry entry, InputStream inputStream);
}
