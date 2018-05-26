package io.disk_indexer.core.scanners;

import java.io.File;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;

public class FileSystemScanner implements Scanner {
	private ConnectionManager connectionManager;
	
	public FileSystemScanner(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	@Override
	public Entry scan(String path) throws PersistenceFailureException {			
		File root = new File(path);
		Entry rootEntry;
		
		if (root.isDirectory()) {
			System.out.println(root.getPath());
			rootEntry = new Entry(EntryTypes.Directory);			
			
			for (File child : root.listFiles()) {
				Entry childEntry = scan(child.getPath());
				rootEntry.addChildEntry(childEntry);
			}			
		} else {
			rootEntry = new Entry(EntryTypes.File);
		}
		
		rootEntry.setName(root.getName());
		rootEntry.setModificationDate(root.lastModified());
		rootEntry.setSize(root.length());
		
		connectionManager.getEntryDao().create(rootEntry);
		
		return rootEntry;
	}	
}
