package io.disk_indexer.ui.fileicons;


import io.disk_indexer.core.entity.Entry;
import javafx.scene.image.Image;

public class FileExtensionMapper implements FileMapper {

	@Override
	public Image obtainIcon(Entry entry) {
		return new Image("io/disk_indexer/ui/fileicons/file.png");
	}
}
