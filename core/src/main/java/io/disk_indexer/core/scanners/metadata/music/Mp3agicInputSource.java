package io.disk_indexer.core.scanners.metadata.music;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;

import com.mpatric.mp3agic.InputSource;

public class Mp3agicInputSource implements InputSource {
	private SeekableByteChannel byteChannel;

	public Mp3agicInputSource(SeekableByteChannel byteChannel) {
		this.byteChannel = byteChannel;
	}

	@Override
	public String getResourceName() {
		return null;
	}

	@Override
	public boolean isReadable() {
		return true;
	}

	@Override
	public long getLastModified() throws IOException {
		return 0;
	}

	@Override
	public long getLength() throws IOException {
		return 0;
	}

	@Override
	public SeekableByteChannel openChannel(OpenOption... options) throws IOException {
		return this.byteChannel;
	}

}
