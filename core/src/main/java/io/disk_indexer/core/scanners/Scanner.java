package io.disk_indexer.core.scanners;

import java.util.LinkedList;
import java.util.List;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.model.Entry;

public abstract class Scanner {
	private List<EntryListener> entryListeners;
	
	public Scanner() {
		entryListeners = new LinkedList<>();
	}
	
	public void addEntryListener(EntryListener entryListener) {
		entryListeners.add(entryListener);
	}
	
	protected void invokeEntryListenerOnStart() throws EntryListenerFailedException {
		for (EntryListener entryListener : entryListeners)
			entryListener.onScanStarted();
	}
	
	protected void invokeEntryListenerOnComplete() throws EntryListenerFailedException {
		for (EntryListener entryListener : entryListeners)
			entryListener.onScanComplete();
	}
	
	protected void invokeEntryListeners(Entry entry) throws EntryListenerFailedException {
		for (EntryListener entryListener : entryListeners)
			entryListener.processEntry(entry);
	}
	
	public abstract void scan(String path) throws ScannerFailedException;
}
