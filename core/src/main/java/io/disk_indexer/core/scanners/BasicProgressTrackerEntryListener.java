package io.disk_indexer.core.scanners;

import java.io.PrintStream;

import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.model.Entry;

public class BasicProgressTrackerEntryListener implements EntryListener {
	private PrintStream outputStream;
	private long startTime;
	private long entryCount;
	
	public BasicProgressTrackerEntryListener(PrintStream outputStream) {
		this.outputStream = outputStream;
	}
	
	@Override
	public void onScanStarted() throws EntryListenerFailedException {
		outputStream.println("Scan started.");
		startTime = System.nanoTime();
		entryCount = 0;
	}

	@Override
	public void processEntry(Entry entry) throws EntryListenerFailedException {
		entryCount++;
		
		if (entryCount % 500 == 0) {
			outputStream.println("Processed: " + entryCount);
		}
	}

	@Override
	public void onScanComplete() throws EntryListenerFailedException {
		outputStream.println("\nScan completed in " + ((System.nanoTime() - startTime) / 1000000) + " ms.");
		outputStream.println("Total number of files: " + entryCount);
	}
}
