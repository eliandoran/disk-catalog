package io.disk_indexer.core.dao.exceptions;

import io.disk_indexer.core.scanners.metadata.MetadataProvider;

/**
 * A wrapper exception thrown when a {@link MetadataProvider} fails to complete its task properly.
 */
public class MetadataProviderFailedException extends Exception {
	private static final long serialVersionUID = 6587046043287197921L;

	/**
	 * Creates a new exception.
	 * @param underlyingException The underlying exception that has caused the failure.
	 */
	public MetadataProviderFailedException(Exception underlyingException) {
		addSuppressed(underlyingException);
	}
}
