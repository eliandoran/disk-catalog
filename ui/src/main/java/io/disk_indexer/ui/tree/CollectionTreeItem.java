package io.disk_indexer.ui.tree;

import io.disk_indexer.core.model.Collection;
import javafx.scene.control.TreeItem;

public class CollectionTreeItem extends TreeItem<String> {
	private final Collection collection;

	public CollectionTreeItem(Collection collection) {
		super(collection.getTitle());
		this.collection = collection;
	}
}
