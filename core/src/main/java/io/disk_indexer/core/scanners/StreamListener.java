package io.disk_indexer.core.scanners;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;

public interface StreamListener extends ScannerListener {
	StreamListenerInputType needsStream(Entry entry) throws ScannerListenerFailedException;

	void receiveStream(Entry entry, Object inputSource) throws ScannerListenerFailedException;
}
