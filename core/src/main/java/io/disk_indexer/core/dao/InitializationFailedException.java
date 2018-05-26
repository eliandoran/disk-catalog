package io.disk_indexer.core.dao;

public class InitializationFailedException extends Exception {
	private static final long serialVersionUID = -1908291601847210521L;

	public InitializationFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
