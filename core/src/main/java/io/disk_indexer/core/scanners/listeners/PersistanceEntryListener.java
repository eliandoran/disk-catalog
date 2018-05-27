package io.disk_indexer.core.scanners.listeners;

import java.sql.SQLException;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;
import io.disk_indexer.core.scanners.EntryListener;

public class PersistanceEntryListener implements EntryListener {
	private final ConnectionManager connectionManager;

	public PersistanceEntryListener(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public int getPriority() {
		return 9999;
	}

	@Override
	public void onScanStarted() {
		// No action needed.
	}

	@Override
	public void processEntry(Entry entry) throws ScannerListenerFailedException {
		try {
			int index = this.connectionManager.getEntryDao().create(entry);
			entry.setId(Integer.valueOf(index));

			if (entry.getMetadata() != null) {
				for (Metadata data : entry.getMetadata()) {
					this.connectionManager.getMetadataDao().create(data);
				}
			}
		} catch (PersistenceFailureException e) {
			throw new ScannerListenerFailedException(e);
		}
	}

	@Override
	public void onScanComplete() throws ScannerListenerFailedException {
		try {
			this.connectionManager.getConnection().commit();
		} catch (SQLException e) {
			throw new ScannerListenerFailedException(e);
		}
	}
}
