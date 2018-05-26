package io.disk_indexer.core.dao;

public class ConnectionFailedException extends Exception {
	private static final long serialVersionUID = 2321884332815440785L;
	
	public ConnectionFailedException() {
		
	}
	
	public ConnectionFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
