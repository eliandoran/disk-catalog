package io.disk_indexer.core.scanners.metadata.music;

public enum MusicMetadata {
	DURATION("Music:Duration"),
	BITRATE("Music:Bitrate"),
	SAMPLE_RATE("Music:Sample Rate"),
	TRACK("Music:Track"),
	ARTIST("Music:Artist"),
	TITLE("Music:Title"),
	ALBUM("Music:Album"),
	YEAR("Music:Year"),
	GENRE("Music:Genre"),
	COMMENT("Music:Comment"),
	COMPOSER("Music:Composer"),
	PUBLISHER("Music:Publisher"),
	ORIGINAL_ARTIST("Music:Original Artist"),
	ALBUM_ARTIST("Music:Album Artist"),
	COPYRIGHT("Music:Copyright"),
	URL("Music:URL");

	private String key;

	private MusicMetadata(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
