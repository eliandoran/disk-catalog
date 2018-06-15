package io.disk_indexer.core.scanners.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.exceptions.ScannerFailedException;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.model.EntryTypes;
import io.disk_indexer.core.scanners.Scanner;
import io.disk_indexer.core.scanners.StreamListenerInputType;

public class FileSystemScanner extends Scanner {
	private Collection collection;

	@Override
	public void scan(Collection collection, String path) throws ScannerFailedException {
		this.collection = collection;

		try {
			beforeScan(collection);
			doScan(null, path);
			afterScan();
		} catch (ScannerListenerFailedException e) {
			throw new ScannerFailedException(e);
		}
	}

	private void doScan(Entry parentEntry, String path) throws ScannerListenerFailedException {
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

		invokeListeners(rootEntry, path);

		if (root.isDirectory()) {
			for (File child : root.listFiles()) {
				doScan(rootEntry, child.getPath());
			}
		}
	}

	@Override
	protected Object obtainStream(StreamListenerInputType inputType, Object tag) {
		final String fileName = (String)tag;

		try {
			switch (inputType) {
			case INPUT_STREAM:
				return new FileInputStream(fileName);
			case SEEKABLE_BYTE_CHANNEL:
				return Files.newByteChannel(Paths.get(fileName), StandardOpenOption.READ);
			default:
				throw new RuntimeException("Unknown input type.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}