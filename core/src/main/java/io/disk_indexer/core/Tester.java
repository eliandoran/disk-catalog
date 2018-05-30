package io.disk_indexer.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.scanners.impl.FileSystemScanner;
import io.disk_indexer.core.scanners.listeners.BasicProgressTrackerEntryListener;
import io.disk_indexer.core.scanners.listeners.MetadataStreamListener;
import io.disk_indexer.core.scanners.listeners.PersistanceEntryListener;
import io.disk_indexer.core.scanners.metadata.image.ImageMetadataProvider;
import io.disk_indexer.core.scanners.metadata.music.Mp3MetadataProvider;

public class Tester {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("core");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Collection collection = new Collection("Music");
			entityManager.persist(collection);

			MetadataStreamListener metadataStreamListener = new MetadataStreamListener();
			metadataStreamListener.addProvider(new Mp3MetadataProvider());
			metadataStreamListener.addProvider(new ImageMetadataProvider());

			FileSystemScanner scanner = new FileSystemScanner();
			scanner.addListener(new PersistanceEntryListener(entityManager));
			scanner.addListener(new BasicProgressTrackerEntryListener(System.out));
			scanner.addListener(metadataStreamListener);
			scanner.scan(collection, "/run/media/elian/Elian D./MiniTest");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
