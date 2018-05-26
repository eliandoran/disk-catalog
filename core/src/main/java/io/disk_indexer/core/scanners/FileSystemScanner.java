package io.disk_indexer.core.scanners;

import java.io.File;
import java.io.InputStream;
import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.dao.exceptions.StreamListenerFailedException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;

public class FileSystemScanner extends Scanner {
	@Override
	public void scan(String path) throws ScannerFailedException {
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
		invokeStreamListeners(rootEntry, path);
	}

	@Override
	protected InputStream obtainStream(Object tag) {
		// TODO Auto-generated method stub
		return null;
	}
}
