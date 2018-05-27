package io.disk_indexer.core.dao.exceptions;

import io.disk_indexer.core.scanners.EntryListener;

/**
 * A wrapper exception thrown when a {@link EntryListener} fails to complete its task properly.
 */
public class EntryListenerFailedException extends Exception {
	private static final long serialVersionUID = -8461600713617403466L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public EntryListenerFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
