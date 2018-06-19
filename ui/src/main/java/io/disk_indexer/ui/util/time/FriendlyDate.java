package io.disk_indexer.ui.util.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.Map;

public class FriendlyDate {
	private static Map<String, DateTimeFormatter> formatters = new HashMap<>();

	private FriendlyDate() {
		// Prevent instantiation
	}

	public static String getFriendlyDate(LocalDate date, FriendlyDateLocale friendlyDateStrings) {
		String format = getFormat(date, friendlyDateStrings);
		DateTimeFormatter formatter = formatters.get(format);

		if (formatter == null ) {
			formatter = DateTimeFormatter.ofPattern(format);
			formatters.put(format, formatter);
		}

		return formatter.format(date);
	}

	private static String getFormat(LocalDate date, FriendlyDateLocale friendlyLocale) {
		if (LocalDateUtils.isToday(date))
			return friendlyLocale.getTodayFormat();

		if (LocalDateUtils.isYesterday(date))
			return friendlyLocale.getYesterdayFormat();

		if (LocalDateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, date, LocalDate.now()))
			return friendlyLocale.getSameYearFormat();

		return friendlyLocale.getDefaultFormat();
	}
}
