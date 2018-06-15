package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Collection;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class CollectionTreeItem extends TreeItem<String> {
	private final Collection collection;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;
	private boolean isLeaf = true;

	public CollectionTreeItem(Collection collection) {
		super(collection.getTitle());
		this.collection = collection;
		System.out.println("Simon says Hi");
	}

	@Override
	public boolean isLeaf() {
		if (this.isFirstTimeLeaf) {
			this.isLeaf = this.collection.getRootEntry().getChildEntries().isEmpty();
		}

		return this.isLeaf;
	}

	@Override
	public ObservableList<TreeItem<String>> getChildren() {
		if (this.isFirstTimeChildren) {
			super.getChildren().setAll(buildChildren());
			this.isFirstTimeChildren = false;
		}

		return super.getChildren();
	}

	private ObservableList<TreeItem<String>> buildChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
		Entry rootEntry = this.collection.getRootEntry();
		System.out.println("Root entry: " + rootEntry.getName());

		for (Entry subEntry : rootEntry.getChildEntries()) {
			System.out.println(subEntry.getName() + " " + subEntry.getChildEntries().size());

			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}
}
