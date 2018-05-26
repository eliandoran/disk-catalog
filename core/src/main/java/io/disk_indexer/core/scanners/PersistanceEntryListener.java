package io.disk_indexer.core.scanners;

import java.sql.SQLException;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.exceptions.EntryListenerFailedException;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Entry;

public class PersistanceEntryListener implements EntryListener {
	private final ConnectionManager connectionManager;

	public PersistanceEntryListener(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public void onScanStarted() {
		// No action needed.
	}

	@Override
	public void processEntry(Entry entry) throws EntryListenerFailedException {
		try {
			int index = this.connectionManager.getEntryDao().create(entry);
			entry.setId(Integer.valueOf(index));
		} catch (PersistenceFailureException e) {
			throw new EntryListenerFailedException(e);
		}
	}

	@Override
	public void onScanComplete() throws EntryListenerFailedException {
		try {
			this.connectionManager.getConnection().commit();
		} catch (SQLException e) {
			throw new EntryListenerFailedException(e);
		}
	}
}