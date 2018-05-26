package io.disk_indexer.core.dao.exceptions;

public class PersistenceFailureException extends Exception {
	private static final long serialVersionUID = -3287155621912163284L;

	public PersistenceFailureException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
