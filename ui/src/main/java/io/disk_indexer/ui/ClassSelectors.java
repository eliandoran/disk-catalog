package io.disk_indexer.ui;

import javafx.scene.Node;

public enum ClassSelectors {
	ENTRY_ICON;

	private String styleClass;

	private ClassSelectors() {
		this.styleClass = this.name().replace('_', '-').toLowerCase();
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void applyTo(Node node) {
		node.getStyleClass().add(this.getStyleClass());
	}
}
