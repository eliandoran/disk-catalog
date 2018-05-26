package io.disk_indexer.core.dao.exceptions;

public class ScannerFailedException extends Exception {
	private static final long serialVersionUID = 490978753917985418L;

	public ScannerFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
