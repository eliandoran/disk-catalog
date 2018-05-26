package io.disk_indexer.core.dao;

public abstract class FileSystemConnectionManager extends ConnectionManager {
	public abstract void connect(String filePath) throws ConnectionFailedException, InitializationFailedException;
}
