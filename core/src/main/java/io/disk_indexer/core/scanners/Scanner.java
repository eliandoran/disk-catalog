package io.disk_indexer.core.scanners;

import java.util.LinkedList;
import java.util.List;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.dao.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Collection;
import io.disk_indexer.core.model.Entry;

public abstract class Scanner {
	private List<EntryListener> entryListeners;
	private List<StreamListener> streamListeners;

	public Scanner() {
		this.entryListeners = new LinkedList<>();
		this.streamListeners = new LinkedList<>();
	}

	public void addEntryListener(EntryListener entryListener) {
		this.entryListeners.add(entryListener);
	}

	protected void invokeEntryListenerOnStart() throws EntryListenerFailedException {
		for (EntryListener entryListener : this.entryListeners) {
			entryListener.onScanStarted();
		}
	}

	protected void invokeEntryListenerOnComplete() throws EntryListenerFailedException {
		for (EntryListener entryListener : this.entryListeners) {
			entryListener.onScanComplete();
		}
	}

	protected void invokeEntryListeners(Entry entry) throws EntryListenerFailedException {
		for (EntryListener entryListener : this.entryListeners) {
			entryListener.processEntry(entry);
		}
	}

	public void addStreamListener(StreamListener streamListener) {
		this.streamListeners.add(streamListener);
	}

	protected void invokeStreamListeners(Entry entry, Object tag) throws StreamListenerFailedException {
		for (StreamListener streamListener : this.streamListeners) {
			StreamListenerInputType inputType = streamListener.needsStream(entry);

			if (inputType != null) {
				streamListener.receiveStream(entry, obtainStream(inputType, tag));
			}
		}
	}

	protected abstract Object obtainStream(StreamListenerInputType inputType, Object tag);

	public abstract void scan(Collection collection, String path) throws ScannerFailedException;
}
