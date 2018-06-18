package io.disk_indexer.ui.treeobject;

import com.airhacks.afterburner.injection.Injector;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.ui.fileicons.FileMapper;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntryImageNameTreeTableCell extends TreeTableCell<EntryTreeObject, Entry> {
	private ImageView imageView;

	public EntryImageNameTreeTableCell() {
		this.imageView = new ImageView();

		setContentDisplay(ContentDisplay.CENTER);
	}

	@Override
	protected void updateItem(Entry item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) {
			setGraphic(null);
			return;
		}

		FileMapper fileMapper = Injector.instantiateModelOrService(FileMapper.class);
		Image icon = fileMapper.obtainIcon(item);
		this.imageView.setImage(icon);

		setGraphic(this.imageView);
	}
}