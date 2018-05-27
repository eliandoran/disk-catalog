package io.disk_indexer.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import io.disk_indexer.core.dao.ConnectionManager;
import io.disk_indexer.core.dao.generic.CollectionDao;
import io.disk_indexer.core.exceptions.PersistenceFailureException;
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
	public Iterable<Collection> readAll() throws PersistenceFailureException {
		final String listCollectionsSQL = "SELECT `collectionId`, `rootEntryId`, `title` FROM `Collections`";
		List<Collection> collectionsList = new LinkedList<>();

		try {
			PreparedStatement listCollectionsStmt = this.connectionManager.getConnection().prepareStatement(listCollectionsSQL);
			ResultSet result = listCollectionsStmt.executeQuery();
			Collection parsedCollection = parse(result);
			collectionsList.add(parsedCollection);
		} catch (SQLException e) {
			throw new PersistenceFailureException(e);
		}

		return collectionsList;
	}

	private Collection parse(ResultSet resultSet) throws SQLException {
		Collection newCollection = new Collection();
		int paramIndex = 1;
		newCollection.setId(resultSet.getInt(paramIndex++));
		newCollection.setRootEntry(null);
		paramIndex++;
		newCollection.setTitle(resultSet.getString(paramIndex++));

		return newCollection;
	}

	@Override
	public int getNextIndex() {
		return this.nextIndex;
	}
}
