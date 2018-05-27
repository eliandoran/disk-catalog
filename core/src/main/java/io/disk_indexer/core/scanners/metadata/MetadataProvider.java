package io.disk_indexer.core.scanners.metadata;

import io.disk_indexer.core.dao.exceptions.MetadataProviderFailedException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;
import io.disk_indexer.core.scanners.StreamListenerInputType;

public interface MetadataProvider {
	StreamListenerInputType getInputType();

	String[] getSupportedExtensions();

	Iterable<Metadata> process(Entry entry, Object inputSource) throws MetadataProviderFailedException;
}
