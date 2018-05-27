package io.disk_indexer.core.scanners.metadata;

import io.disk_indexer.core.exceptions.MetadataProviderFailedException;
import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;

public interface MetadataProvider<StreamT> {
	String[] getSupportedExtensions();

	Iterable<Metadata> process(Entry entry, StreamT inputSource) throws MetadataProviderFailedException;
}
