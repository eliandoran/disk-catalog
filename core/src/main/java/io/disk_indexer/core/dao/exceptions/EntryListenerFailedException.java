package io.disk_indexer.core.dao.exceptions;

public class EntryListenerFailedException extends Exception {
	private static final long serialVersionUID = -8461600713617403466L;

	public EntryListenerFailedException(Throwable underlyingException) {
		addSuppressed(underlyingException);
	}
}
