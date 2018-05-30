package io.disk_indexer.core.scanners.metadata.music;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import io.disk_indexer.core.entity.Metadata;

public class Mp3FileParser {
	private Mp3FileParser() {
		// Prevent instantiation.
	}

	public static Iterable<Metadata> parse(Mp3File mp3) {
		List<Metadata> metadata = new LinkedList<>();
		Map<MusicMetadata, String> fields = new HashMap<>();

		addMetaIfNotNull(fields, MusicMetadata.DURATION, mp3.getLengthInSeconds());
		addMetaIfNotNull(fields, MusicMetadata.BITRATE, mp3.getBitrate());
		addMetaIfNotNull(fields, MusicMetadata.SAMPLE_RATE, mp3.getSampleRate());

		for (ID3v1 tags : new ID3v1[] { mp3.getId3v1Tag(), mp3.getId3v2Tag() }) {
			if (tags == null) {
				continue;
			}

			addMetaIfNotNull(fields, MusicMetadata.TRACK, tags.getTrack());
			addMetaIfNotNull(fields, MusicMetadata.ARTIST, tags.getArtist());
			addMetaIfNotNull(fields, MusicMetadata.TITLE, tags.getTitle());
			addMetaIfNotNull(fields, MusicMetadata.ALBUM, tags.getAlbum());
			addMetaIfNotNull(fields, MusicMetadata.YEAR, tags.getYear());
			addMetaIfNotNull(fields, MusicMetadata.GENRE, tags.getGenreDescription());
			addMetaIfNotNull(fields, MusicMetadata.COMMENT, tags.getComment());
		}

		if (mp3.hasId3v2Tag()) {
			ID3v2 tags = mp3.getId3v2Tag();

			addMetaIfNotNull(fields, MusicMetadata.COMPOSER, tags.getComposer());
			addMetaIfNotNull(fields, MusicMetadata.PUBLISHER, tags.getPublisher());
			addMetaIfNotNull(fields, MusicMetadata.ORIGINAL_ARTIST, tags.getOriginalArtist());
			addMetaIfNotNull(fields, MusicMetadata.ALBUM_ARTIST, tags.getAlbumArtist());
			addMetaIfNotNull(fields, MusicMetadata.COPYRIGHT, tags.getCopyright());
			addMetaIfNotNull(fields, MusicMetadata.URL, tags.getUrl());
		}

		for (Entry<MusicMetadata, String> field : fields.entrySet()) {
			metadata.add(new Metadata(field.getKey().getKey(), field.getValue()));
		}

		return metadata;
	}

	private static void addMetaIfNotNull(Map<MusicMetadata, String> fields, MusicMetadata key, String value) {
		if (value == null || value.isEmpty())
			return;

		fields.put(key, value);
	}

	private static void addMetaIfNotNull(Map<MusicMetadata, String> fields, MusicMetadata key, Object value) {
		addMetaIfNotNull(fields, key, value.toString());
	}
}
