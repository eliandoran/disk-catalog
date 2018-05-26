package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.MetadataDao;
import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Metadata;

public class MetadataSqlDao implements MetadataDao {
	private final ConnectionManager connectionManager;
	private int nextIndex = 0;

	public MetadataSqlDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public int create(Metadata entity) throws PersistenceFailureException {
		final String createMetadataSQL = "INSERT INTO `Metadata` (`metadataId`, `entryId`, `name`, `value`) VALUES (?, ?, ?, ?)";

		try {
			int paramIndex = 1;
			PreparedStatement createMetadataStmt = this.connectionManager.getConnection().prepareStatement(createMetadataSQL);

			createMetadataStmt.setInt(paramIndex++, this.nextIndex);

			if (entity.getParentEntry() != null && entity.getParentEntry().getId() != null) {
				createMetadataStmt.setInt(paramIndex++, entity.getParentEntry().getId().intValue());
			} else {
				createMetadataStmt.setNull(paramIndex++, Types.INTEGER);
			}

			createMetadataStmt.setString(paramIndex++, entity.getName());
			createMetadataStmt.setString(paramIndex++, entity.getValue());
			createMetadataStmt.execute();

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
