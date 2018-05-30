package io.disk_indexer.core.scanners.listeners;

import java.io.PrintStream;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.scanners.EntryListener;

public class BasicProgressTrackerEntryListener implements EntryListener {
	private PrintStream outputStream;
	private long startTime;
	private long entryCount;

	public BasicProgressTrackerEntryListener(PrintStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void onScanStarted() throws ScannerListenerFailedException {
		this.outputStream.println("Scan started.");
		this.startTime = System.nanoTime();
		this.entryCount = 0;
	}

	@Override
	public void processEntry(Entry entry) throws ScannerListenerFailedException {
		this.entryCount++;

		if (this.entryCount % 500 == 0) {
			this.outputStream.println("Processed: " + this.entryCount);
		}
	}

	@Override
	public void onScanComplete() throws ScannerListenerFailedException {
		this.outputStream.println("\nScan completed in " + ((System.nanoTime() - this.startTime) / 1000000) + " ms.");
		this.outputStream.println("Total number of files: " + this.entryCount);
	}
}
