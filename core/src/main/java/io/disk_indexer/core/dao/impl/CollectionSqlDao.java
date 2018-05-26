package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import io.disk_indexer.core.dao.CollectionDao;
import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Collection;

public class CollectionSqlDao implements CollectionDao {
	private final ConnectionManager connectionManager;
	private int nextIndex = 1;

	public CollectionSqlDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public int create(Collection entity) throws PersistenceFailureException {
		final String createCollectionSQL = "INSERT INTO `Collections` (`collectionId`, `rootEntryId`, `title`) VALUES (?, ?, ?)";

		try {
			int paramIndex = 1;
			PreparedStatement createCollectionStmt = this.connectionManager.getConnection().prepareStatement(createCollectionSQL);

			createCollectionStmt.setInt(paramIndex++, this.nextIndex);

			if (entity.getRootEntry() != null && entity.getRootEntry().getId() != null) {
				createCollectionStmt.setInt(paramIndex++, entity.getRootEntry().getId());
			} else {
				createCollectionStmt.setNull(paramIndex++, Types.INTEGER);
			}

			createCollectionStmt.setString(paramIndex++, entity.getTitle());
			createCollectionStmt.execute();

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
