package io.disk_indexer.core.exceptions;

import io.disk_indexer.core.scanners.Scanner;

/**
 * A wrapper exception thrown when a {@link Scanner} fails to complete its task properly.
 */
public class ScannerFailedException extends Exception {
	private static final long serialVersionUID = 490978753917985418L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public ScannerFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
