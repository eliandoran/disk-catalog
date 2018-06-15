package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DirectoryTreeItem extends CachedTreeItem<String> {
	private final Entry entry;

	public DirectoryTreeItem(Entry entry) {
		super(entry.getName());

		if (entry.getEntryType() != EntryTypes.Directory)
			throw new IllegalArgumentException("Only directory entries can be supplied.");

		this.entry = entry;
	}

	@Override
	public ObservableList<TreeItem<String>> determineChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();

		for (Entry subEntry : this.entry.getChildEntries()) {
			System.out.println("Subentry: " + subEntry.getName());
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}

}
