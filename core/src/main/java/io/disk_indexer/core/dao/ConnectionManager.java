package io.disk_indexer.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionManager {
	private Connection savedConnection;
	
	protected void connectUrl(String url) throws ConnectionFailedException {
		Connection newConnection = null;
		
		try {
			newConnection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new ConnectionFailedException(e);
		}			
					
		savedConnection = newConnection;
	}
}
