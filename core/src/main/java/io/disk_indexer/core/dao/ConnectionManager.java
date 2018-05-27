package io.disk_indexer.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.disk_indexer.core.dao.generic.CollectionDao;
import io.disk_indexer.core.dao.generic.EntryDao;
import io.disk_indexer.core.dao.generic.MetadataDao;
import io.disk_indexer.core.dao.impl.MetadataSqlDao;
import io.disk_indexer.core.exceptions.ConnectionFailedException;
import io.disk_indexer.core.exceptions.InitializationFailedException;

public abstract class ConnectionManager {
	protected Connection savedConnection;

	protected abstract void initialize() throws InitializationFailedException;

	protected EntryDao entryDao;

	protected CollectionDao collectionDao;

	protected MetadataSqlDao metadataDao;

	protected void connectUrl(String url) throws ConnectionFailedException, InitializationFailedException {
		Connection newConnection = null;

		try {
			newConnection = DriverManager.getConnection(url);
			newConnection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new ConnectionFailedException(e);
		}

		this.savedConnection = newConnection;
		initialize();
	}

	public Connection getConnection() {
		return this.savedConnection;
	}

	public EntryDao getEntryDao() {
		return this.entryDao;
	}

	public CollectionDao getCollectionDao() {
		return this.collectionDao;
	}

	public MetadataDao getMetadataDao() {
		return this.metadataDao;
	}
}
