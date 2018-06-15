package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class CollectionTreeItem extends TreeItem<String> {
	private final Collection collection;

	public CollectionTreeItem(Collection collection) {
		super(collection.getTitle());
		this.collection = collection;
		System.out.println("Simon says Hi");
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public ObservableList<TreeItem<String>> getChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
		Entry rootEntry = this.collection.getRootEntry();

		System.out.println("Listing: " + this.collection.getTitle());

		for (Entry subEntry : rootEntry.getChildEntries()) {
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		System.out.println("Number of children: " + children.size());

		return children;
	}
}
