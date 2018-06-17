package io.disk_indexer.ui.treeobject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.util.StringConverter;

public class EpochTimeStringConverter extends StringConverter<Number> {

	@Override
	public Long fromString(String string) {
		return null;
	}

	@Override
	public String toString(Number dateNumber) {
		long date = dateNumber.longValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
		Instant instant = Instant.ofEpochMilli(date);
		LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return dateTime.format(formatter);
	}

}
