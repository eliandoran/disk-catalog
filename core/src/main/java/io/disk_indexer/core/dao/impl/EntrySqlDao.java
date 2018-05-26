package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.EntryDao;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Entry;

public class EntrySqlDao implements EntryDao {
	private final ConnectionManager connectionManager;
	
	public EntrySqlDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}	
	
	@Override
	public void create(Entry entity) throws PersistenceFailureException {		
		final String createEntrySQL = "INSERT INTO `Entries` (`name`, `modificationDate`, `size`) VALUES (?, ?, ?)";
		
		try {
			int paramIndex = 1;
			PreparedStatement createEntryStmt = connectionManager.getConnection().prepareStatement(createEntrySQL);			
			createEntryStmt.setString(paramIndex++, entity.getName());
			createEntryStmt.setLong(paramIndex++, entity.getModificationDate());
			createEntryStmt.setLong(paramIndex++, entity.getSize());
			createEntryStmt.execute();
		} catch (SQLException e) {
			throw new PersistenceFailureException(e);
		}
	}
}
