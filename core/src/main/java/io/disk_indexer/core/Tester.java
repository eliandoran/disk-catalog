package io.disk_indexer.core;

import java.io.File;

import io.disk_indexer.core.dao.FileSystemConnectionManager;
import io.disk_indexer.core.dao.impl.SqliteConnectionManager;
import io.disk_indexer.core.model.Collection;
import io.disk_indexer.core.scanners.impl.FileSystemScanner;
import io.disk_indexer.core.scanners.listeners.BasicProgressTrackerEntryListener;
import io.disk_indexer.core.scanners.listeners.MetadataStreamListener;
import io.disk_indexer.core.scanners.listeners.PersistanceEntryListener;

public class Tester {

	public static void main(String[] args) {
		FileSystemConnectionManager connectionManager = new SqliteConnectionManager();
		File testDb = new File("/home/elian/db.sqlite");

		try {
			testDb.delete();
			connectionManager.connect(testDb.getAbsolutePath());

			Collection collection = new Collection("Music");
			int collectionId = connectionManager.getCollectionDao().create(collection);
			collection.setId(collectionId);
			connectionManager.getConnection().commit();

			FileSystemScanner scanner = new FileSystemScanner();
			scanner.addEntryListener(new PersistanceEntryListener(connectionManager));
			scanner.addEntryListener(new BasicProgressTrackerEntryListener(System.out));
			scanner.addStreamListener(new MetadataStreamListener());
			scanner.scan(collection, "/run/media/elian/Elian D./Music");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
