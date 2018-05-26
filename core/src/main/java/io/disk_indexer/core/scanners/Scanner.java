package io.disk_indexer.core.scanners;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.dao.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Entry;

public abstract class Scanner {
	private List<EntryListener> entryListeners;
	private List<StreamListener> streamListeners;
	
	public Scanner() {
		entryListeners = new LinkedList<>();
		streamListeners = new LinkedList<>();
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
	
	public void addStreamListener(StreamListener streamListener) {
		streamListeners.add(streamListener);
	}
	
	protected void invokeStreamListeners(Entry entry, Object tag) throws StreamListenerFailedException {
		InputStream inputStream = null;
		
		for (StreamListener streamListener : streamListeners) {
			if (streamListener.needsStream(entry)) {
				if (inputStream == null) {
					inputStream = obtainStream(tag);
				}
				
				if (inputStream != null) {
					streamListener.receiveStream(entry, inputStream);					
				}				
			}
		}
	}
	
	protected abstract InputStream obtainStream(Object tag);
	
	public abstract void scan(String path) throws ScannerFailedException;
}
