package io.disk_indexer.ui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

public class FriendlyDate {
	private FriendlyDate() {
		// Prevent instantiation.
	}

	public static String getFriendlyDate(LocalDate date, FriendlyDateStrings friendlyDateStrings) {
		if (DateUtils.isToday(date))
			return friendlyDateStrings.getToday();

		if (DateUtils.isYesterday(date))
			return friendlyDateStrings.getYesterday();

		if (DateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, date, LocalDate.now()))
			return date.format(DateTimeFormatter.ofPattern("d MMM"));

		return date.format(DateTimeFormatter.ofPattern("d MMM YYYY"));
	}
}
