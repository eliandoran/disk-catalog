package io.disk_indexer.core.dao.generic;

import io.disk_indexer.core.dao.CrudInterface;
import io.disk_indexer.core.exceptions.PersistenceFailureException;
import io.disk_indexer.core.model.Collection;

public interface CollectionDao extends CrudInterface<Collection> {
	Iterable<Collection> readAll() throws PersistenceFailureException;
}
