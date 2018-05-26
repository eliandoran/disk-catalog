package io.disk_indexer.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.disk_indexer.core.dao.exceptions.ConnectionFailedException;
import io.disk_indexer.core.dao.exceptions.InitializationFailedException;

public abstract class ConnectionManager {
	protected Connection savedConnection;
	
	protected abstract void initialize() throws InitializationFailedException;
	
	protected EntryDao entryDao;
	
	protected void connectUrl(String url) throws ConnectionFailedException, InitializationFailedException {
		Connection newConnection = null;
		
		try {
			newConnection = DriverManager.getConnection(url);
			newConnection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new ConnectionFailedException(e);
		}			
					
		savedConnection = newConnection;
		initialize();
	}
	
	public Connection getConnection() {
		return savedConnection;
	}
	
	public EntryDao getEntryDao() {
		return entryDao;
	}
}
