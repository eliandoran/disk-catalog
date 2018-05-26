package io.disk_indexer.core.scanners.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;
import io.disk_indexer.core.scanners.StreamListener;
import io.disk_indexer.core.scanners.metadata.MetadataProvider;

public class MetadataStreamListener implements StreamListener {
	private Map<String, MetadataProvider> providers;

	public MetadataStreamListener() {
		this.providers = new HashMap<>();
	}

	public void addProvider(MetadataProvider provider) {
		for (String extension : provider.getSupportedExtensions()) {
			this.providers.put(extension, provider);
		}
	}

	@Override
	public boolean needsStream(Entry entry) {
		if (entry.getEntryType() != EntryTypes.File)
			return false;

		String extension = getFileExtension(entry.getName());

		MetadataProvider provider = this.providers.get(extension);
		if (provider != null) {
			System.out.println("Found provider for " + entry.getName() + ":" + provider.getClass().getName());
			return true;
		} else
			return false;
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void receiveStream(Entry entry, InputStream inputStream) {
		try {
			System.out.println(entry.getName() + " " + inputStream.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
