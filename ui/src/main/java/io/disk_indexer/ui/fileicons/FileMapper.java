package io.disk_indexer.ui.fileicons;

import io.disk_indexer.core.entity.Entry;
import javafx.scene.image.Image;

public interface FileMapper {
	Image obtainIcon(Entry entry);
}
