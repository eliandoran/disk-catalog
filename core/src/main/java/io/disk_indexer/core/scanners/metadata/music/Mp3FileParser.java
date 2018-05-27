package io.disk_indexer.core.scanners.metadata.music;

import java.util.LinkedList;
import java.util.List;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import io.disk_indexer.core.model.Metadata;

public class Mp3FileParser {
	private Mp3FileParser() {
		// Prevent instantiation.
	}

	public static Iterable<Metadata> parse(Mp3File mp3) {
		List<Metadata> metadata = new LinkedList<>();

		long duration = mp3.getLengthInSeconds();
		metadata.add(new Metadata(MusicMetadata.DURATION.getKey(), String.valueOf(duration)));

		int bitRate = mp3.getBitrate();
		metadata.add(new Metadata(MusicMetadata.BITRATE.getKey(), String.valueOf(bitRate)));

		int sampleRate = mp3.getSampleRate();
		metadata.add(new Metadata(MusicMetadata.SAMPLE_RATE.getKey(), String.valueOf(sampleRate)));

		String track = null;
		String artist = null;
		String title = null;
		String album = null;
		String year = null;
		String genre = null;
		String comment = null;

		if (mp3.hasId3v1Tag()) {
			ID3v1 tags = mp3.getId3v1Tag();

			track = tags.getTrack();
			artist = tags.getArtist();
			title = tags.getTitle();
			album = tags.getAlbum();
			year = tags.getYear();
			genre = (!tags.getGenreDescription().isEmpty() ? tags.getGenreDescription() : genre);
			comment = tags.getComment();
		}

		if (mp3.hasId3v2Tag()) {
			ID3v2 tags = mp3.getId3v2Tag();

			track = (!tags.getTrack().isEmpty() ? tags.getTrack() : track);
			artist = (!tags.getArtist().isEmpty() ? tags.getArtist() : artist);
			title = (!tags.getTitle().isEmpty() ? tags.getTitle() : title);
			album = (!tags.getAlbum().isEmpty() ? tags.getAlbum() : album);
			year = (!tags.getYear().isEmpty() ? tags.getYear() : year);
			genre = (!tags.getGenreDescription().isEmpty() ? tags.getGenreDescription() : genre);
			comment = (!tags.getComment().isEmpty() ? tags.getComment() : comment);

			addMetaIfNotNull(metadata, MusicMetadata.COMPOSER, tags.getComposer());
			addMetaIfNotNull(metadata, MusicMetadata.PUBLISHER, tags.getPublisher());
			addMetaIfNotNull(metadata, MusicMetadata.ORIGINAL_ARTIST, tags.getOriginalArtist());
			addMetaIfNotNull(metadata, MusicMetadata.ALBUM_ARTIST, tags.getAlbumArtist());
			addMetaIfNotNull(metadata, MusicMetadata.COPYRIGHT, tags.getCopyright());
			addMetaIfNotNull(metadata, MusicMetadata.URL, tags.getUrl());
		}

		addMetaIfNotNull(metadata, MusicMetadata.TRACK, track);
		addMetaIfNotNull(metadata, MusicMetadata.ARTIST, artist);
		addMetaIfNotNull(metadata, MusicMetadata.TITLE, title);
		addMetaIfNotNull(metadata, MusicMetadata.ALBUM, album);
		addMetaIfNotNull(metadata, MusicMetadata.YEAR, year);
		addMetaIfNotNull(metadata, MusicMetadata.GENRE, genre);
		addMetaIfNotNull(metadata, MusicMetadata.COMMENT, comment);

		return metadata;
	}

	private static void addMetaIfNotNull(List<Metadata> metadataList, MusicMetadata key, String value) {
		if (value == null || value.isEmpty())
			return;

		metadataList.add(new Metadata(key.getKey(), value.toString()));
	}
}
