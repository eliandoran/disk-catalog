package io.disk_indexer.core.dao.exceptions;

import io.disk_indexer.core.scanners.StreamListener;

/**
 * A wrapper exception thrown when a {@link StreamListener} fails to complete its task properly.
 */
public class StreamListenerFailedException extends Exception {
	private static final long serialVersionUID = -2603357253178211992L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public StreamListenerFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
