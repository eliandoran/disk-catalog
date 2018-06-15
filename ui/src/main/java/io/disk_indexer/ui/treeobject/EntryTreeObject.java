package io.disk_indexer.ui.treeobject;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.entity.Entry;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EntryTreeObject extends RecursiveTreeObject<EntryTreeObject> {
	final StringProperty name;
	final LongProperty size;
	final LongProperty date;

	public EntryTreeObject(Entry entry) {
		this.name = new SimpleStringProperty(entry.getName());
		this.size = new SimpleLongProperty(entry.getSize());
		this.date = new SimpleLongProperty(entry.getModificationDate());
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public LongProperty sizeProperty() {
		return this.size;
	}

	public LongProperty dateProperty() {
		return this.date;
	}
}
