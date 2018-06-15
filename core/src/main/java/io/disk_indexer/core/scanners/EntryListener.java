package io.disk_indexer.core.scanners;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;

public interface EntryListener extends ScannerListener {
	void onScanStarted(Collection collection) throws ScannerListenerFailedException;

	void processEntry(Entry entry) throws ScannerListenerFailedException;

	void onScanComplete() throws ScannerListenerFailedException;
}
