package io.disk_indexer.core.scanners.metadata.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.Metadata;
import io.disk_indexer.core.exceptions.MetadataProviderFailedException;
import io.disk_indexer.core.scanners.metadata.InputStreamMetadataProvider;

public class ImageMetadataProvider implements InputStreamMetadataProvider {
	private static List<Class<?>> desiredDirectories = new LinkedList<>();

	static {
		desiredDirectories.add(JpegDirectory.class);
		desiredDirectories.add(PngDirectory.class);
		desiredDirectories.add(ExifIFD0Directory.class);
		desiredDirectories.add(ExifSubIFDDirectory.class);
	}

	@Override
	public String[] getSupportedExtensions() {
		return new String[] {
				// JPEG
				"jpg", "jpeg", "jpe", "jif", "jfif", "jfi",

				// TIFF
				"tiff", "tif",

				// BMP
				"bmp", "dib",

				// Camera raw
				"nef", "cr2", "orf", "arw", "rw2", "rwl", "srw",

				// Different file types with single extension
				"psd", "png", "gif", "ico", "pcx"
		};
	}

	@Override
	public Iterable<Metadata> process(Entry entry, InputStream inputStream) throws MetadataProviderFailedException {
		List<Metadata> metadata = new LinkedList<>();

		try {
			com.drew.metadata.Metadata tags = ImageMetadataReader.readMetadata(inputStream);

			for (Directory directory : tags.getDirectories()) {
				if (!desiredDirectories.contains(directory.getClass())) {
					continue;
				}

				for (Tag tag : directory.getTags()) {
					ImageMetadata correspondingMeta = ImageMetadata.mapTag(tag.getTagName());
					String value = (tag.getDescription() != null ? tag.getDescription().trim() : "");

					if (correspondingMeta != null && !value.isEmpty()) {
						metadata.add(new Metadata(correspondingMeta.getKey(), value));
					}
				}
			}
		} catch (ImageProcessingException | IOException e) {
			throw new MetadataProviderFailedException(e);
		}

		return metadata;
	}
}
