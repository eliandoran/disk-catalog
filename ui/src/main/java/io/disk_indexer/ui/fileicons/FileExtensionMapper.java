package io.disk_indexer.ui.fileicons;


import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.scene.image.Image;

public class FileExtensionMapper implements FileMapper {

	@Override
	public Image obtainIcon(Entry entry) {
		return loadImage(getImagePath(entry));
	}

	private static String getImagePath(Entry entry) {
		if (entry.getEntryType() == EntryTypes.Directory)
			return "directory.png";

		return "file.png";
	}

	private static Image loadImage(String imagePath) {
		return new Image("io/disk_indexer/ui/fileicons/" + imagePath);
	}
}
