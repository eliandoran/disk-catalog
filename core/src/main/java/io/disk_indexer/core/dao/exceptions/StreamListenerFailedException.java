package io.disk_indexer.core.dao.exceptions;

public class StreamListenerFailedException extends Exception {
	private static final long serialVersionUID = -2603357253178211992L;

	public StreamListenerFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
