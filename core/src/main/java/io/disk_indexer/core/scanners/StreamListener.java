package io.disk_indexer.core.scanners;

import io.disk_indexer.core.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Entry;

public interface StreamListener {
	StreamListenerInputType needsStream(Entry entry) throws StreamListenerFailedException;

	void receiveStream(Entry entry, Object inputSource) throws StreamListenerFailedException;
}
