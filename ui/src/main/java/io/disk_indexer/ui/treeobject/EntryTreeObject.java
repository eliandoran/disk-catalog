package io.disk_indexer.ui.treeobject;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.entity.Entry;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class EntryTreeObject extends RecursiveTreeObject<EntryTreeObject> {
	final Entry entry;

	final StringProperty name;
	final LongProperty size;
	final LongProperty date;

	final Image icon;

	public EntryTreeObject(Entry entry, Image icon) {
		this.entry = entry;
		this.name = new SimpleStringProperty(entry.getName());
		this.size = new SimpleLongProperty(entry.getSize());
		this.date = new SimpleLongProperty(entry.getModificationDate());
		this.icon = icon;
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

	public Entry getEntry() {
		return this.entry;
	}

	public Image getIcon() {
		return this.icon;
	}
}
