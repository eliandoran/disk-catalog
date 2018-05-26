package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.disk_indexer.core.dao.FileSystemConnectionManager;
import io.disk_indexer.core.dao.exceptions.ConnectionFailedException;
import io.disk_indexer.core.dao.exceptions.InitializationFailedException;

public class SqliteConnectionManager extends FileSystemConnectionManager {		
	public SqliteConnectionManager() {
		entryDao = new EntrySqlDao(this);
	}
	
	@Override
	public void connect(String filePath) throws ConnectionFailedException, InitializationFailedException {
		String connectionString = obtainConnectionString(filePath);
		connectUrl(connectionString);
	}
	
	private static String obtainConnectionString(String filePath) {
		StringBuilder builder = new StringBuilder();
		builder.append("jdbc:sqlite:");
		builder.append(filePath);
		return builder.toString();
	}
	
	@Override
	protected void initialize() throws InitializationFailedException {
		final String createCollectionsSQL =
				"CREATE TABLE IF NOT EXISTS `Collections` (\n" + 
				"  `collectionId` INT NOT NULL,\n" + 
				"  `rootEntryId` INT NOT NULL,\n" +  
				"  PRIMARY KEY (`collectionId`))\n";
		
		final String createEntriesSQL = 
				"CREATE TABLE IF NOT EXISTS `Entries` (\n" +
				"  `entryId` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
				"  `parentEntryId` INT,\n" +
				"  `collectionId` INT,\n" +
				"  `name` VARCHAR NOT NULL,\n" +
				"  `modificationDate` INT NOT NULL,\n" +
				"  `size` INT NOT NULL)";
				
		try {
			PreparedStatement createCollectionsStmt = this.savedConnection.prepareStatement(createCollectionsSQL);
			PreparedStatement createEntriesStmt = this.savedConnection.prepareStatement(createEntriesSQL);
			
			createCollectionsStmt.execute();
			createEntriesStmt.execute();
			savedConnection.commit();
		} catch (SQLException e) {
			throw new InitializationFailedException(e);
		}
	}
}
