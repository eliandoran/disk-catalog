package io.disk_indexer.ui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

public class FriendlyDate {
	private FriendlyDate() {
		// Prevent instantiation.
	}

	public static String getFriendlyDate(LocalDate date, FriendlyDateLocale friendlyDateStrings) {
		String format = getFormat(date, friendlyDateStrings);
		return DateTimeFormatter.ofPattern(format).format(date);
	}

	private static String getFormat(LocalDate date, FriendlyDateLocale friendlyLocale) {
		if (DateUtils.isToday(date))
			return friendlyLocale.getTodayFormat();

		if (DateUtils.isYesterday(date))
			return friendlyLocale.getYesterdayFormat();

		if (DateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, date, LocalDate.now()))
			return friendlyLocale.getSameYearFormat();

		return friendlyLocale.getDefaultFormat();
	}
}
