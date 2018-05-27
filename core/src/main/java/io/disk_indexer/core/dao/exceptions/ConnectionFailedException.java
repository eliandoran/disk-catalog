package io.disk_indexer.core.dao.exceptions;

/**
 * A wrapper exception thrown when the persistence layer fails to maintain a proper connection to its data source (i.e. a database).
 */
public class ConnectionFailedException extends Exception {
	private static final long serialVersionUID = 2321884332815440785L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public ConnectionFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
