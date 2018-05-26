package io.disk_indexer.core.dao;

import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Model;

public interface CrudInterface<EntityT extends Model<Integer>> {
	int getNextIndex();

	int create(EntityT entity) throws PersistenceFailureException;
}
