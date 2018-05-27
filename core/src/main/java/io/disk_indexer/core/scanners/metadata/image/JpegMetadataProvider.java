package io.disk_indexer.core.scanners.metadata.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

import io.disk_indexer.core.model.Entry;
import io.disk_indexer.core.model.Metadata;
import io.disk_indexer.core.scanners.StreamListenerInputType;
import io.disk_indexer.core.scanners.metadata.MetadataProvider;

public class JpegMetadataProvider implements MetadataProvider {
	private static List<Class<?>> desiredDirectories = new LinkedList<>();

	private static Map<String, ImageMetadata> mappings = new HashMap<>();

	static {
		desiredDirectories.add(JpegDirectory.class);
		desiredDirectories.add(ExifIFD0Directory.class);
		desiredDirectories.add(ExifSubIFDDirectory.class);

		mappings.put("Image Width", ImageMetadata.WIDTH);
		mappings.put("Image Height", ImageMetadata.HEIGHT);
		mappings.put("Make", ImageMetadata.MAKE);
		mappings.put("Model", ImageMetadata.MODEL);
		mappings.put("Software", ImageMetadata.SOFTWARE);
		mappings.put("Date/Time", ImageMetadata.DATETIME);
		mappings.put("Artist", ImageMetadata.ARTIST);
		mappings.put("Copyright", ImageMetadata.COPYRIGHT);
		mappings.put("Exposure Time", ImageMetadata.EXPOSURE);
		mappings.put("F-Number", ImageMetadata.F_NUMBER);
		mappings.put("ISO Speed Ratings", ImageMetadata.ISO_SPEED);
		mappings.put("Date/Time Original", ImageMetadata.DATETIME_ORIGINAL);
		mappings.put("Date/Time Digitized", ImageMetadata.DATETIME_DIGITIZED);
		mappings.put("Shutter Speed Value", ImageMetadata.SHUTTER_SPEED);
	}

	@Override
	public StreamListenerInputType getInputType() {
		return StreamListenerInputType.INPUT_STREAM;
	}

	@Override
	public String[] getSupportedExtensions() {
		return new String[] { "jpg", "jpeg", "jpe", "jif", "jfif", "jfi" };
	}

	@Override
	public Iterable<Metadata> process(Entry entry, Object inputSource) {
		List<Metadata> metadata = new LinkedList<>();

		InputStream inputStream = (InputStream)inputSource;
		try {
			com.drew.metadata.Metadata tags = ImageMetadataReader.readMetadata(inputStream);

			for (Directory directory : tags.getDirectories()) {
				if (!desiredDirectories.contains(directory.getClass())) {
					continue;
				}

				for (Tag tag : directory.getTags()) {
					ImageMetadata correspondingMeta = mappings.get(tag.getTagName());
					String value = tag.getDescription().trim();

					if (correspondingMeta != null && !value.isEmpty()) {
						metadata.add(new Metadata(correspondingMeta.getKey(), value));
					}
				}
			}
		} catch (ImageProcessingException | IOException e) {
			e.printStackTrace();
			return null;
		}

		return metadata;
	}
}
