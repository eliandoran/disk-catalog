package io.disk_indexer.core.exceptions;

import io.disk_indexer.core.scanners.ScannerListener;

/**
 * A wrapper exception thrown when a {@link ScannerListener} fails to complete its task properly.
 */
public class ScannerListenerFailedException extends Exception {
	private static final long serialVersionUID = -8461600713617403466L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public ScannerListenerFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
