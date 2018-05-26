package io.disk_indexer.core.scanners;

import java.io.File;
import java.sql.SQLException;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;

public class FileSystemScanner extends Scanner {
	@Override
	public void scan(String path) throws ScannerFailedException {
		try {
			invokeEntryListenerOnStart();		
			
			doScan(null, path);
			
			invokeEntryListenerOnComplete();			
		} catch (EntryListenerFailedException e) {
			throw new ScannerFailedException(e);
		}
	}	
	
	private void doScan(Entry parentEntry, String path) throws EntryListenerFailedException {
		File root = new File(path);
		Entry rootEntry;		
						
		if (root.isDirectory()) {
			rootEntry = new Entry(EntryTypes.Directory);
			 
			for (File child : root.listFiles()) {
				doScan(rootEntry, child.getPath());				
			}								
		} else {
			rootEntry = new Entry(EntryTypes.File);
		}			
		
		rootEntry.setName(root.getName());
		rootEntry.setModificationDate(root.lastModified());
		rootEntry.setSize(root.length());

		invokeEntryListeners(rootEntry);
	}
}
