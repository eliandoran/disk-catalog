package io.disk_indexer.core.dao.exceptions;

/**
 * A wrapper exception thrown when the persistence layer has failed to initialize itself properly, after a connection has been made.
 */
public class InitializationFailedException extends Exception {
	private static final long serialVersionUID = -1908291601847210521L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public InitializationFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
