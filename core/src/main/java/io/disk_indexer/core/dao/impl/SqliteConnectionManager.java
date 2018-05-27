package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.disk_indexer.core.dao.FileSystemConnectionManager;
import io.disk_indexer.core.exceptions.ConnectionFailedException;
import io.disk_indexer.core.exceptions.InitializationFailedException;

public class SqliteConnectionManager extends FileSystemConnectionManager {
	public SqliteConnectionManager() {
		this.entryDao = new EntrySqlDao(this);
		this.collectionDao = new CollectionSqlDao(this);
		this.metadataDao = new MetadataSqlDao(this);
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
						"  `collectionId` INT PRIMARY KEY,\n" +
						"  `rootEntryId` INT,\n" +
						"  `title` VARCHAR NOT NULL)";

		final String createEntriesSQL =
				"CREATE TABLE IF NOT EXISTS `Entries` (\n" +
						"  `entryId` INT PRIMARY KEY,\n" +
						"  `parentEntryId` INT,\n" +
						"  `collectionId` INT,\n" +
						"  `name` VARCHAR NOT NULL,\n" +
						"  `type` INT NOT NULL,\n" +
						"  `modificationDate` INT NOT NULL,\n" +
						"  `size` INT NOT NULL)";

		final String createMetadataSQL =
				"CREATE TABLE IF NOT EXISTS `Metadata` (\n" +
						"  `metadataId` INT PRIMARY KEY,\n" +
						"  `entryId` INT NOT NULL,\n" +
						"  `name` VARCHAR NOT NULL,\n" +
						"  `value` VARCHAR)";

		try {
			PreparedStatement createCollectionsStmt = this.savedConnection.prepareStatement(createCollectionsSQL);
			PreparedStatement createEntriesStmt = this.savedConnection.prepareStatement(createEntriesSQL);
			PreparedStatement createMetadataStmt = this.savedConnection.prepareStatement(createMetadataSQL);

			createCollectionsStmt.execute();
			createEntriesStmt.execute();
			createMetadataStmt.execute();

			this.savedConnection.commit();
		} catch (SQLException e) {
			throw new InitializationFailedException(e);
		}
	}
}
