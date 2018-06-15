package io.disk_indexer.ui.tree;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public abstract class CachedTreeItem<T> extends TreeItem<T> {
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;
	private boolean isLeaf = true;

	public CachedTreeItem() {
		super();
	}

	public CachedTreeItem(T value) {
		super(value);
	}

	public CachedTreeItem(T value, Node graphic) {
		super(value, graphic);
	}

	@Override
	public boolean isLeaf() {
		if (this.isFirstTimeLeaf) {
			this.isLeaf = determineIsLeaf();
			this.isFirstTimeLeaf = false;
		}

		return this.isLeaf;
	}

	@Override
	public ObservableList<TreeItem<T>> getChildren() {
		if (this.isFirstTimeChildren) {
			ObservableList<TreeItem<T>> children = determineChildren();

			if (children != null && !children.isEmpty()) {
				super.getChildren().setAll(children);
			}

			this.isFirstTimeChildren = false;
		}

		return super.getChildren();
	}

	public boolean determineIsLeaf() {
		return getChildren().isEmpty();
	}

	public abstract ObservableList<TreeItem<T>> determineChildren();
}
