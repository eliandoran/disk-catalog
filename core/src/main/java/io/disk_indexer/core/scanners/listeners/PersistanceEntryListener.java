package io.disk_indexer.core.scanners.listeners;

import javax.persistence.EntityManager;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.scanners.EntryListener;

public class PersistanceEntryListener implements EntryListener {
	private final EntityManager entityManager;
	private Collection collection;
	private Entry rootEntry;

	public PersistanceEntryListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public int getPriority() {
		return 9999;
	}

	@Override
	public void onScanStarted(Collection collection) {
		this.entityManager.getTransaction().begin();
		this.collection = collection;
	}

	@Override
	public void processEntry(Entry entry) throws ScannerListenerFailedException {
		this.entityManager.persist(entry);
	}

	@Override
	public void onScanComplete() throws ScannerListenerFailedException {
		this.entityManager.getTransaction().commit();
	}
}
