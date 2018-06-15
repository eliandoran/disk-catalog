package io.disk_indexer.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.scanners.impl.FileSystemScanner;
import io.disk_indexer.core.scanners.listeners.BasicProgressTrackerEntryListener;
import io.disk_indexer.core.scanners.listeners.MetadataStreamListener;
import io.disk_indexer.core.scanners.listeners.PersistanceEntryListener;

public class Tester {
	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;

	public static void main(String[] args) {
		entityManagerFactory = Persistence.createEntityManagerFactory("core");
		entityManager = entityManagerFactory.createEntityManager();

		try {
			Collection collection = new Collection("Music");
			entityManager.persist(collection);

			MetadataStreamListener metadataStreamListener = new MetadataStreamListener();
			//metadataStreamListener.addProvider(new Mp3MetadataProvider());
			//metadataStreamListener.addProvider(new ImageMetadataProvider());

			FileSystemScanner scanner = new FileSystemScanner();
			scanner.addListener(new PersistanceEntryListener(entityManager));
			scanner.addListener(new BasicProgressTrackerEntryListener(System.out));
			scanner.addListener(metadataStreamListener);
			scanner.scan(collection, "/home/elian/Desktop");

			Collection x = entityManager.createQuery("from Collection", Collection.class).getSingleResult();
			Entry rootEntry = x.getRootEntry();

			if (x.getRootEntry() == null) {
				System.out.println("Null root entry.");
			}

			if (rootEntry.getChildEntries() == null) {
				System.out.println("Empty child entries.");
			}

			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
