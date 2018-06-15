package io.disk_indexer.ui.treeobject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import io.disk_indexer.core.entity.Entry;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EntryTreeObject extends RecursiveTreeObject<EntryTreeObject> {
	final StringProperty name;
	final LongProperty size;
	final StringProperty date;

	public EntryTreeObject(Entry entry) {
		this.name = new SimpleStringProperty(entry.getName());
		this.size = new SimpleLongProperty(entry.getSize());
		this.date = new SimpleStringProperty(parseDate(entry.getModificationDate()));
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public LongProperty sizeProperty() {
		return this.size;
	}

	public StringProperty dateProperty() {
		return this.date;
	}

	private String parseDate(long date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
		Instant instant = Instant.ofEpochMilli(date);
		LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return dateTime.format(formatter);
	}
}
