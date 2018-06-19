package io.disk_indexer.ui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FriendlyDate {
	private FriendlyDate() {
		// Prevent instantiation.
	}

	public static String getFriendlyDate(LocalDate date, FriendlyDateStrings friendlyDateStrings) {
		if (DateUtils.isToday(date))
			return friendlyDateStrings.getToday();

		if (DateUtils.isYesterday(date))
			return friendlyDateStrings.getYesterday();

		return date.format(DateTimeFormatter.ofPattern("d E YYYY"));
	}
}
