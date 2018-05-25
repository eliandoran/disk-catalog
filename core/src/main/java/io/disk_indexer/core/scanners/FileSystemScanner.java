package io.disk_indexer.core.scanners;

import java.io.File;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;

public class FileSystemScanner implements Scanner {
	@Override
	public Entry scan(String path) {
		File root = new File(path);				
		Entry rootEntry = new Entry(EntryTypes.Directory);
		rootEntry.setName(root.getName());
		
		for (File child : root.listFiles()) {
			Entry childEntry;
			
			if (child.isDirectory()) {
				childEntry = scan(child.getPath());
			} else {
				childEntry = new Entry(EntryTypes.File);
				childEntry.setName(child.getName());
			}
			
			rootEntry.addChildEntry(childEntry);
		}
		
		return rootEntry;
	}
}
