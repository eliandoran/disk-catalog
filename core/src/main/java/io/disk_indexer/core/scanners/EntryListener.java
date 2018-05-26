package io.disk_indexer.core.scanners;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.model.Entry;

public interface EntryListener {
	void onScanStarted() throws EntryListenerFailedException;
	
	void processEntry(Entry entry) throws EntryListenerFailedException;
	
	void onScanComplete() throws EntryListenerFailedException;
}
