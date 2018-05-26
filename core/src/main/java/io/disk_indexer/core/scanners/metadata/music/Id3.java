package io.disk_indexer.core.scanners.metadata.music;

import io.disk_indexer.core.scanners.metadata.MetadataProvider;

public class Id3 implements MetadataProvider {
	@Override
	public String[] getSupportedExtensions() {
		return new String[] { "mp3" };
	}
}
