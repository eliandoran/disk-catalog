package io.disk_indexer.core.exceptions;

/**
 * A wrapper exception thrown when the persistence layer fails to persist the data to the data source.
 */
public class PersistenceFailureException extends Exception {
	private static final long serialVersionUID = -3287155621912163284L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public PersistenceFailureException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
