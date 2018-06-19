package io.disk_indexer.ui.treeobject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import io.disk_indexer.ui.util.time.FriendlyDate;
import io.disk_indexer.ui.util.time.locale.EnglishFriendlyDateLocale;
import javafx.util.StringConverter;

public class EpochTimeStringConverter extends StringConverter<Number> {

	@Override
	public Long fromString(String string) {
		return null;
	}

	@Override
	public String toString(Number dateNumber) {
		long dateEpoch = dateNumber.longValue();
		Instant instant = Instant.ofEpochMilli(dateEpoch);
		LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDate date = LocalDate.from(dateTime);
		return FriendlyDate.getFriendlyDate(date, new EnglishFriendlyDateLocale());
	}

}
