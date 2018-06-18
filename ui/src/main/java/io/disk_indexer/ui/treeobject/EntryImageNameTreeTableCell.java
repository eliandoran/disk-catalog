package io.disk_indexer.ui.treeobject;

import io.disk_indexer.core.entity.Entry;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.ImageView;

public class EntryImageNameTreeTableCell extends TreeTableCell<EntryTreeObject, Entry> {
	private ImageView imageView;

	public EntryImageNameTreeTableCell() {
		setContentDisplay(ContentDisplay.CENTER);
		this.imageView = new ImageView("io/disk_indexer/ui/fileicons/file.png");
	}

	@Override
	protected void updateItem(Entry item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) {
			setGraphic(null);
			return;
		}

		setGraphic(this.imageView);
	}
}