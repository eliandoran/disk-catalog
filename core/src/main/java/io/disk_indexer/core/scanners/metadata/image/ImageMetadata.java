package io.disk_indexer.core.scanners.metadata.image;

import java.util.HashMap;
import java.util.Map;

public enum ImageMetadata {
	WIDTH("Image:Width", "Image Width"),
	HEIGHT("Image:Height", "Image Height"),
	MAKE("Image:Camera Brand", "Make"),
	MODEL("Image:Camera Model", "Model"),
	EXPOSURE_TIME("Image:Exposure Time", "Exposure Time"),
	EXPOSURE_PROGRAM("Image:Exposure Program", "Exposure Program"),
	ISO_SPEED("Image:ISO Speed Rating", "ISO Speed Ratings"),
	FLASH_FIRED("Image:Flash Fired", "Flash"),
	METERING_MODE("Image:Metering Mode", "Metering Mode"),
	FOCAL_LENGTH("Image:Focal Length", "Focal Length"),
	SOFTWARE("Image:Software", "Software"),
	DATETIME("Image:Date/Time", "Date/Time"),
	ARTIST("Image:Artist", "Artist"),
	COPYRIGHT("Image:Copyright", "Copyright"),
	DATETIME_ORIGINAL("Image:Date/Time Original", "Date/Time Original"),
	DATETIME_DIGITIZED("Image:Date/Time Digitized", "Date/Time Digitized");

	private static Map<String, ImageMetadata> tagMappings = new HashMap<>();

	static {
		for (ImageMetadata data : ImageMetadata.values()) {
			tagMappings.put(data.getTag(), data);
		}
	}

	private String key;
	private String tag;

	private ImageMetadata(String key, String tagName) {
		this.key = key;
		this.tag = tagName;
	}

	public String getKey() {
		return this.key;
	}

	public String getTag() {
		return this.tag;
	}

	public static ImageMetadata mapTag(String tag) {
		return tagMappings.get(tag);
	}
}
