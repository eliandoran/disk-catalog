package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.EntryDao;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Entry;

public class EntrySqlDao implements EntryDao {
	private final ConnectionManager connectionManager;
	private int nextIndex = 5;

	public EntrySqlDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public int create(Entry entity) throws PersistenceFailureException {
		final String createEntrySQL = "INSERT INTO `Entries` (`entryId`, `parentEntryId`, `name`, `modificationDate`, `size`) VALUES (?, ?, ?, ?, ?)";

		try {
			int paramIndex = 1;
			PreparedStatement createEntryStmt = this.connectionManager.getConnection().prepareStatement(createEntrySQL);
			createEntryStmt.setInt(paramIndex++, this.nextIndex);

			if (entity.getParentEntry() != null && entity.getParentEntry().getId() != null) {
				createEntryStmt.setInt(paramIndex++, entity.getParentEntry().getId().intValue());
			} else {
				createEntryStmt.setNull(paramIndex++, Types.INTEGER);
			}

			createEntryStmt.setString(paramIndex++, entity.getName());
			createEntryStmt.setLong(paramIndex++, entity.getModificationDate());
			createEntryStmt.setLong(paramIndex++, entity.getSize());
			createEntryStmt.execute();

			return this.nextIndex++;
		} catch (SQLException e) {
			throw new PersistenceFailureException(e);
		}
	}

	@Override
	public int getNextIndex() {
		return this.nextIndex;
	}
}
