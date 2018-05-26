package io.disk_indexer.core;

import java.io.File;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.FileSystemConnectionManager;
import io.disk_indexer.core.dao.exceptions.ConnectionFailedException;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.dao.exceptions.ScannerFailedException;
import io.disk_indexer.core.dao.impl.SqliteConnectionManager;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.scanners.BasicProgressTrackerEntryListener;
import io.disk_indexer.core.scanners.FileSystemScanner;
import io.disk_indexer.core.scanners.PersistanceEntryListener;

public class Tester {

	public static void main(String[] args) {	
		FileSystemConnectionManager connectionManager = new SqliteConnectionManager();		
		File testDb = new File("/home/elian/db.sqlite");
		
		try {			
			testDb.delete();
			connectionManager.connect(testDb.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
				
		try {
			FileSystemScanner scanner = new FileSystemScanner();			
			scanner.addEntryListener(new PersistanceEntryListener(connectionManager));
			scanner.addEntryListener(new BasicProgressTrackerEntryListener(System.out));
			scanner.scan("/run/media/elian/Elian D./Music");
		} catch (ScannerFailedException e) {
			e.printStackTrace();
		}
	}

}
