package io.disk_indexer.core.scanners.listeners;

import java.util.HashMap;
import java.util.Map;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.Metadata;
import io.disk_indexer.core.exceptions.MetadataProviderFailedException;
import io.disk_indexer.core.exceptions.ScannerListenerFailedException;
import io.disk_indexer.core.model.EntryTypes;
import io.disk_indexer.core.scanners.StreamListener;
import io.disk_indexer.core.scanners.StreamListenerInputType;
import io.disk_indexer.core.scanners.metadata.InputStreamMetadataProvider;
import io.disk_indexer.core.scanners.metadata.MetadataProvider;
import io.disk_indexer.core.scanners.metadata.SeekableByteChannelMetadataProvider;

public class MetadataStreamListener implements StreamListener {
	private Map<String, MetadataProvider> providers;

	private MetadataProvider lastProvider;

	public MetadataStreamListener() {
		this.providers = new HashMap<>();
	}

	public void addProvider(MetadataProvider provider) {
		for (String extension : provider.getSupportedExtensions()) {
			this.providers.put(extension, provider);
		}
	}

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public StreamListenerInputType needsStream(Entry entry) {
		if (entry.getEntryType() != EntryTypes.File)
			return null;

		String extension = getFileExtension(entry.getName()).toLowerCase();

		MetadataProvider<?> provider = this.providers.get(extension);
		if (provider != null) {
			System.out.println("Found provider for " + entry.getName() + ":" + provider.getClass().getName());
			this.lastProvider = provider;

			if (provider instanceof InputStreamMetadataProvider)
				return StreamListenerInputType.INPUT_STREAM;
			else if (provider instanceof SeekableByteChannelMetadataProvider)
				return StreamListenerInputType.SEEKABLE_BYTE_CHANNEL;
		}

		return null;
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void receiveStream(Entry entry, Object inputSource) throws ScannerListenerFailedException {
		if (this.lastProvider == null)
			throw new RuntimeException("Last provider is null. This shouldn't have happened.");

		try {
			Iterable<Metadata> metadata = this.lastProvider.process(entry, inputSource);
			entry.addMetadata(metadata);
		} catch (MetadataProviderFailedException e) {
			throw new ScannerListenerFailedException(e);
		}

		this.lastProvider = null;
	}
}
