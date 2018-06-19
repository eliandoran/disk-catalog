package io.disk_indexer.ui;

import javafx.css.Styleable;

public enum ClassSelectors {
	ENTRY_ICON,
	COLUMN_DATE_MODIFIED;

	private String styleClass;

	private ClassSelectors() {
		this.styleClass = this.name().replace('_', '-').toLowerCase();
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void applyTo(Styleable styleable) {
		styleable.getStyleClass().add(this.getStyleClass());
	}
}
