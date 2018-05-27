package io.disk_indexer.core.scanners;

import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.model.Entry;

public interface EntryListener extends ScannerListener {
	void onScanStarted() throws ScannerListenerFailedException;

	void processEntry(Entry entry) throws ScannerListenerFailedException;

	void onScanComplete() throws ScannerListenerFailedException;
}
