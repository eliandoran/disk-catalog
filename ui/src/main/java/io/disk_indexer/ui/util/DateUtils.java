package io.disk_indexer.ui.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class DateUtils {
	private DateUtils() {
		// Prevent instantiation.
	}

	public static boolean isToday(LocalDate date) {
		return LocalDate.now().compareTo(date) == 0;
	}

	public static boolean isYesterday(LocalDate date) {
		return LocalDate.now().minusDays(1).compareTo(date) == 0;
	}

	public static boolean isThisWeek(LocalDate date) {
		LocalDate today = LocalDate.now();

		return (!date.isAfter(today) && isSameWeek(today, date));
	}

	public static boolean isLastWeek(LocalDate date) {
		LocalDate today = LocalDate.now();
		LocalDate lastWeek = getFirstDateOfWeek(today).minusDays(1);

		return (isSameWeek(date, lastWeek));
	}

	public static boolean isSameWeek(LocalDate firstDate, LocalDate secondDate) {
		LocalDate firstDateOfWeek = getFirstDateOfWeek(firstDate);
		LocalDate lastDateOfWeek = getLastDateOfWeek(firstDate);

		return (!secondDate.isBefore(firstDateOfWeek) && !secondDate.isAfter(lastDateOfWeek));
	}

	public static boolean haveSame(TemporalField field, LocalDate firstDate, LocalDate secondDate) {
		return (firstDate.get(field) == secondDate.get(field));
	}

	public static DayOfWeek getFirstDayOfWeek() {
		return WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
	}

	public static DayOfWeek getLastDayOfWeek() {
		DayOfWeek firstDayOfWeek = getFirstDayOfWeek();

		switch (firstDayOfWeek) {
		case MONDAY:
			return DayOfWeek.SUNDAY;

		case SUNDAY:
			return DayOfWeek.SATURDAY;

		default:
			throw new RuntimeException("Unknown first day of week.");
		}
	}

	public static LocalDate getFirstDateOfWeek(LocalDate date) {
		DayOfWeek firstDayOfWeek = getFirstDayOfWeek();
		LocalDate result = LocalDate.from(date);

		while (result.getDayOfWeek() != firstDayOfWeek) {
			result = result.minusDays(1);
		}

		return result;
	}

	public static LocalDate getLastDateOfWeek(LocalDate date) {
		DayOfWeek lastDayOfWeek = getLastDayOfWeek();
		LocalDate result = LocalDate.from(date);

		while (result.getDayOfWeek() != lastDayOfWeek) {
			result = result.plusDays(1);
		}

		return result;
	}
}
