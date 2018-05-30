package io.disk_indexer.core.scanners.metadata;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.Metadata;
import io.disk_indexer.core.exceptions.MetadataProviderFailedException;

public interface MetadataProvider<StreamT> {
	String[] getSupportedExtensions();

	Iterable<Metadata> process(Entry entry, StreamT inputSource) throws MetadataProviderFailedException;
}
