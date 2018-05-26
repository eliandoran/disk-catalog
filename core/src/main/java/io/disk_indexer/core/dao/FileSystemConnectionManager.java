package io.disk_indexer.core.dao;

import io.disk_indexer.core.dao.exceptions.ConnectionFailedException;
import io.disk_indexer.core.dao.exceptions.InitializationFailedException;

public abstract class FileSystemConnectionManager extends ConnectionManager {
	public abstract void connect(String filePath) throws ConnectionFailedException, InitializationFailedException;
}
