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

import io.disk_indexer.ui.util.time.LocalDateUtils;

public class LocalDateUtilsTest {
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

		assertTrue(LocalDateUtils.isToday(today));
		assertFalse(LocalDateUtils.isToday(today.minusDays(1)));
	}

	@Test
	public void testIsYesterday() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);

		assertTrue(LocalDateUtils.isYesterday(yesterday));
		assertFalse(LocalDateUtils.isYesterday(today));
	}

	@Test
	public void testIsSameWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		assertFalse(LocalDateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 16)));
		assertTrue(LocalDateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 19)));
		assertFalse(LocalDateUtils.isSameWeek(dummyDate, LocalDate.of(2018, 6, 25)));
	}

	@Test
	public void testHaveSame() {
		LocalDate firstDate = LocalDate.of(2018, 6, 12);
		LocalDate secondDate = LocalDate.of(2018, 6, 19);

		assertTrue(LocalDateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, firstDate, secondDate));
		assertFalse(LocalDateUtils.haveSame(IsoFields.WEEK_BASED_YEAR, firstDate, secondDate.minusYears(1)));
	}

	@Test
	public void testGetFirstDayOfWeek() {
		assertEquals(DayOfWeek.MONDAY, LocalDateUtils.getFirstDayOfWeek());
	}

	@Test
	public void testGetLastDayOfWeek() {
		assertEquals(DayOfWeek.SUNDAY, LocalDateUtils.getLastDayOfWeek());
	}

	@Test
	public void testGetFirstDateOfWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		LocalDate firstDateOfWeek = LocalDate.of(2018, 6, 18);

		assertEquals(firstDateOfWeek, LocalDateUtils.getFirstDateOfWeek(dummyDate));
	}

	@Test
	public void testGetLastDateOfWeek() {
		LocalDate dummyDate = LocalDate.of(2018, 6, 20);
		LocalDate lastDateOfWeek = LocalDate.of(2018, 6, 24);

		assertEquals(lastDateOfWeek, LocalDateUtils.getLastDateOfWeek(dummyDate));
	}
}
