package io.disk_indexer.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.disk_indexer.ui.util.DateUtils;

public class DateUtilsTest {
	private static Locale defaultLocale;

	@BeforeClass
	public static void setupBeforeClass() {
		defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.FRANCE);
	}

	@AfterClass
	public static void setupAfterClass() {
		Locale.setDefault(defaultLocale);
	}

	@Test
	public void testIsToday() {
		LocalDate today = LocalDate.now();

		assertTrue(DateUtils.isToday(today));
		assertFalse(DateUtils.isToday(today.minusDays(1)));
	}

	@Test
	public void testIsYesterday() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		assertTrue(DateUtils.isYesterday(yesterday));
		assertFalse(DateUtils.isYesterday(today));
	}

	@Test
	public void testIsSameWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		assertFalse(DateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 16)));
		assertTrue(DateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 19)));
		assertFalse(DateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 25)));
	}

	@Test
	public void testHaveSame() {
		LocalDate firstDate = LocalDate.of(2018, 6, 12);
		LocalDate secondDate = LocalDate.of(2018, 6, 19);

		assertTrue(DateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, firstDate, secondDate));
		assertFalse(DateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, firstDate, secondDate.minusYears(1)));
	}

	@Test
	public void testGetFirstDayOfWeek() {
		assertEquals(DayOfWeek.MONDAY, DateUtils.getFirstDayOfWeek());
	}

	@Test
	public void testGetLastDayOfWeek() {
		assertEquals(DayOfWeek.SUNDAY, DateUtils.getLastDayOfWeek());
	}

	@Test
	public void testGetFirstDateOfWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		LocalDate firstDateOfWeek = LocalDate.of(2018, 6, 18);

		assertEquals(firstDateOfWeek, DateUtils.getFirstDateOfWeek(dummyDate));
	}

	@Test
	public void testGetLastDateOfWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		LocalDate lastDateOfWeek = LocalDate.of(2018, 6, 24);

		assertEquals(lastDateOfWeek, DateUtils.getLastDateOfWeek(dummyDate));
	}
}
