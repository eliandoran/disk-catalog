package io.disk_indexer.core.scanners.metadata;

import java.io.InputStream;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;

public interface MetadataProvider {
	String[] getSupportedExtensions();

	Iterable<Metadata> process(Entry entry, InputStream inputStream);
}
