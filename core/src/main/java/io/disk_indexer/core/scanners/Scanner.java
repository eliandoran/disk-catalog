package io.disk_indexer.core.scanners;

import java.util.TreeSet;

import io.disk_indexer.core.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.exceptions.ScannerFailedException;
import io.disk_indexer.core.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Collection;
import io.disk_indexer.core.model.Entry;

/**
 * A scanner is the central part of the indexer. Given a path of some sort on {@link #scan(Collection, String)}, it will recursively iterate through all the files available at the given path.
 *
 * <p>
 * For each file found, the {@link Scanner} will call all the registered {@link EntryListener}(s) and {@link StreamListener}(s) to attach additional functionality such as persistence to database,
 * metadata processing, etc. These can be manually added via {@link #addEntryListener(EntryListener)} and {@link #addStreamListener(StreamListener)}. With no listeners attached, the scanner will
 * simply iterate over all the files without any further action.
 */
public abstract class Scanner {
	private TreeSet<ScannerListener> listeners;

	/**
	 * Creates a new scanner.
	 */
	public Scanner() {
		this.listeners = new TreeSet<>(new ScannerListenerPriorityComparator());
	}

	public void addListener(ScannerListener listener) {
		this.listeners.add(listener);
	}

	protected void beforeScan() throws EntryListenerFailedException {
		for (ScannerListener listener : this.listeners) {
			if (listener instanceof EntryListener) {
				EntryListener entryListener = (EntryListener)listener;
				entryListener.onScanStarted();
			}

			System.out.println(listener.getClass().getName() + " ");
		}

		System.out.println();
	}

	protected void afterScan() throws EntryListenerFailedException {
		for (ScannerListener listener : this.listeners) {
			if (listener instanceof EntryListener) {
				EntryListener entryListener = (EntryListener)listener;
				entryListener.onScanComplete();
			}
		}
	}

	protected void invokeListeners(Entry entry, Object tag) throws EntryListenerFailedException, StreamListenerFailedException {
		for (ScannerListener listener : this.listeners) {
			if (listener instanceof EntryListener) {
				EntryListener entryListener = (EntryListener)listener;
				entryListener.processEntry(entry);
			} else if (listener instanceof StreamListener) {
				StreamListener streamListener = (StreamListener)listener;
				StreamListenerInputType inputType = streamListener.needsStream(entry);

				if (inputType != null) {
					streamListener.receiveStream(entry, obtainStream(inputType, tag));
				}
			}
		}
	}

	protected abstract Object obtainStream(StreamListenerInputType inputType, Object tag);

	public abstract void scan(Collection collection, String path) throws ScannerFailedException;
}
