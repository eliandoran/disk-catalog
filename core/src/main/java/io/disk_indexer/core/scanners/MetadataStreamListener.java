package io.disk_indexer.core.scanners;

import java.io.IOException;
import java.io.InputStream;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.EntryTypes;

public class MetadataStreamListener implements StreamListener {
	@Override
	public boolean needsStream(Entry entry) {				
		if (entry.getEntryType() != EntryTypes.File)
			return false;
		
		return true;
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
