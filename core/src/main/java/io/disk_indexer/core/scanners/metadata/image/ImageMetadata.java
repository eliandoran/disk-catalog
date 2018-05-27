package io.disk_indexer.core.scanners.metadata.image;

public enum ImageMetadata {
	WIDTH("Image:Width"),
	HEIGHT("Image:Height"),
	MAKE("Image:Make"),
	MODEL("Image:Model"),
	SOFTWARE("Image:Software"),
	DATETIME("Image:Date/Time"),
	ARTIST("Image:Artist"),
	COPYRIGHT("Image:Copyright"),
	EXPOSURE("Image:Exposure Time"),
	F_NUMBER("Image:F-number"),
	ISO_SPEED("Image:ISO Speed Ratings"),
	DATETIME_ORIGINAL("Image:Date/Time Original"),
	DATETIME_DIGITIZED("Image:Date/Time Digitized") ,
	SHUTTER_SPEED("Image:Shutter Speed");

	private String key;

	private ImageMetadata(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
