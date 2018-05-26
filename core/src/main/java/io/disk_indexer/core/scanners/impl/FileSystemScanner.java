package io.disk_indexer.core.scanners.impl;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.dao.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Collection;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;
import io.disk_indexer.core.scanners.Scanner;

public class FileSystemScanner extends Scanner {
	private Collection collection;

	@Override
	public void scan(Collection collection, String path) throws ScannerFailedException {
		this.collection = collection;

		try {
			invokeEntryListenerOnStart();

			doScan(null, path);

			invokeEntryListenerOnComplete();
		} catch (EntryListenerFailedException | StreamListenerFailedException e) {
			throw new ScannerFailedException(e);
		}
	}

	private void doScan(Entry parentEntry, String path) throws EntryListenerFailedException, StreamListenerFailedException {
		File root = new File(path);
		Entry rootEntry;

		if (root.isDirectory()) {
			rootEntry = new Entry(EntryTypes.Directory);
		} else {
			rootEntry = new Entry(EntryTypes.File);
		}

		rootEntry.setName(root.getName());
		rootEntry.setModificationDate(root.lastModified());
		rootEntry.setSize(root.length());
		rootEntry.setParentEntry(parentEntry);
		rootEntry.setCollection(this.collection);

		invokeStreamListeners(rootEntry, path);
		invokeEntryListeners(rootEntry);

		if (root.isDirectory()) {
			for (File child : root.listFiles()) {
				doScan(rootEntry, child.getPath());
			}
		}
	}

	@Override
	protected SeekableByteChannel obtainStream(Object tag) {
		try {
			return Files.newByteChannel(Paths.get((String)tag), StandardOpenOption.READ);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
