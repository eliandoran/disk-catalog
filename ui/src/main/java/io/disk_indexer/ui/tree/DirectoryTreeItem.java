package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.model.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DirectoryTreeItem extends TreeItem<String> {
	private final Entry entry;

	public DirectoryTreeItem(Entry entry) {
		if (entry.getEntryType() != EntryTypes.Directory)
			throw new IllegalArgumentException("Only directory entries can be supplied.");

		this.entry = entry;
	}

	@Override
	public ObservableList<TreeItem<String>> getChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();

		for (Entry subEntry : this.entry.getChildEntries()) {
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}
}
