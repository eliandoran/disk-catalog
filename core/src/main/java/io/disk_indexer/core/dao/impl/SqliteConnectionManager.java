package io.disk_indexer.core.dao.impl;

import io.disk_indexer.core.dao.ConnectionFailedException;
import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.FileSystemConnectionManager;

public class SqliteConnectionManager extends FileSystemConnectionManager {
	@Override
	public void connect(String filePath) throws ConnectionFailedException {
		String connectionString = obtainConnectionString(filePath);
		connectUrl(connectionString);
	}
	
	private static String obtainConnectionString(String filePath) {
		StringBuilder builder = new StringBuilder();
		builder.append("jdbc:sqlite:");
		builder.append(filePath);
		return builder.toString();
	}
}
