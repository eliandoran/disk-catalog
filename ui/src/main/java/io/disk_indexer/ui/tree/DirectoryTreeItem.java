package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.EntityBase;
import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DirectoryTreeItem extends CachedTreeItem<EntityBase> {
	private final Entry entry;

	public DirectoryTreeItem(Entry entry) {
		super(entry);

		if (entry.getEntryType() != EntryTypes.Directory)
			throw new IllegalArgumentException("Only directory entries can be supplied.");

		this.entry = entry;
	}

	@Override
	public ObservableList<TreeItem<EntityBase>> determineChildren() {
		ObservableList<TreeItem<EntityBase>> children = FXCollections.observableArrayList();

		for (Entry subEntry : this.entry.getChildEntries()) {
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}

}
