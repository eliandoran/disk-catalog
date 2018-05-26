package io.disk_indexer.core.dao;

import io.disk_indexer.core.dao.exceptions.PersistenceFailureException;

public interface CrudInterface<EntityT> {
	void create(EntityT entity) throws PersistenceFailureException;
}