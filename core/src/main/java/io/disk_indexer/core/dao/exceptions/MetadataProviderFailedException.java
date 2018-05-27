package io.disk_indexer.core.dao.exceptions;

public class MetadataProviderFailedException extends Exception {
	private static final long serialVersionUID = 6587046043287197921L;

	public MetadataProviderFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
