package io.disk_indexer.core.scanners.metadata.music;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;
import io.disk_indexer.core.scanners.metadata.MetadataProvider;

public class Id3 implements MetadataProvider {
	@Override
	public String[] getSupportedExtensions() {
		return new String[] { "mp3" };
	}

	@Override
	public Iterable<Metadata> process(Entry entry, SeekableByteChannel byteChannel) {
		Mp3agicInputSource inputSource = new Mp3agicInputSource(byteChannel);
		try {
			Mp3File mp3File = new Mp3File(inputSource, 100, true);
			return Mp3FileParser.parse(mp3File);
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}