package io.disk_indexer.core;

import io.disk_indexer.core.scanners.FileSystemScanner;

public class Tester {

	public static void main(String[] args) {
		FileSystemScanner scanner = new FileSystemScanner();
		scanner.scan("/home/elian/Desktop");
	}

}
