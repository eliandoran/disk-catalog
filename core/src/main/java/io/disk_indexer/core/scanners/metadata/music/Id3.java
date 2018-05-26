package io.disk_indexer.core.scanners.metadata.music;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;
import io.disk_indexer.core.scanners.metadata.MetadataProvider;

public class Id3 implements MetadataProvider {
	@Override
	public String[] getSupportedExtensions() {
		return new String[] { "mp3" };
	}

	@Override
	public Iterable<Metadata> process(Entry entry, InputStream inputStream) {
		Metadata dummyMetadata = new Metadata("Type", "Song");

		List<Metadata> metadataList = new LinkedList<>();
		metadataList.add(dummyMetadata);
		return metadataList;
	}
}
