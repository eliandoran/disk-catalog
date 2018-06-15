package io.disk_indexer.ui.tree;

import io.disk_indexer.core.entity.Entry;
import io.disk_indexer.core.entity.EntryTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DirectoryTreeItem extends TreeItem<String> {
	private final Entry entry;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;
	private boolean isLeaf = true;

	public DirectoryTreeItem(Entry entry) {
		super(entry.getName());

		System.out.println("Built: " + entry.getName());

		if (entry.getEntryType() != EntryTypes.Directory)
			throw new IllegalArgumentException("Only directory entries can be supplied.");

		System.out.println("Built done: " + entry.getName());

		this.entry = entry;
	}

	@Override
	public boolean isLeaf() {
		if (this.isFirstTimeLeaf) {
			this.isLeaf = this.entry.getChildEntries().isEmpty();
		}

		return this.isLeaf;
	}

	@Override
	public ObservableList<TreeItem<String>> getChildren() {
		System.out.println("Listing children");

		if (this.isFirstTimeChildren) {
			super.getChildren().setAll(buildChildren());
		}

		return super.getChildren();
	}

	private ObservableList<TreeItem<String>> buildChildren() {
		ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
		System.out.println("YOLO");

		if (this.entry.getChildEntries().isEmpty()) {
			System.out.println("Empty childlist.");
		}

		for (Entry subEntry : this.entry.getChildEntries()) {
			System.out.println("Subentry: " + subEntry.getName());
			if (subEntry.getEntryType() == EntryTypes.Directory) {
				children.add(new DirectoryTreeItem(subEntry));
			}
		}

		return children;
	}


}
