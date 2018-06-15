package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class CollectionTreeItem extends CachedTreeItem<String> {
	private final Collection collection;

	public CollectionTreeItem(Collection collection) {
		super(collection.getTitle());
		this.collection = collection;
	}

	@Override
	public boolean determineIsLeaf() {
		return this.collection.getRootEntry().getChildEntries().isEmpty();
	}

	@Override
	public ObservableList<TreeItem<String>> determineChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
		Entry rootEntry = this.collection.getRootEntry();

		for (Entry subEntry : rootEntry.getChildEntries()) {
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}
}
