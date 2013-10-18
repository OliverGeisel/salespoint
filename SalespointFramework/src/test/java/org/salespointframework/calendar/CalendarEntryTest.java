package org.salespointframework.calendar;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import org.junit.Test;
import org.salespointframework.core.calendar.CalendarEntry;

import org.salespointframework.core.user.UserIdentifier;

@SuppressWarnings("javadoc")
public class CalendarEntryTest {

	private DateTime a = new DateTime("2011-08-01T14:01:32.180+02:00");

	private CalendarEntry testEntry1 = new CalendarEntry(new UserIdentifier(
			"owner"), "meeting", a, a.plusHours(5));
	private CalendarEntry testEntry2 = new CalendarEntry(new UserIdentifier(
			"owner"), "meeting", a, a.plusHours(5));
	private CalendarEntry testEntry3 = new CalendarEntry(new UserIdentifier(
			"owner"), "meeting", a.plusHours(2), a.plusHours(5));

	@Test(expected = NullPointerException.class)
	public void testNotNullOwner() {
		@SuppressWarnings("unused")
		CalendarEntry testEntry = new CalendarEntry(null, "meeting",
				new DateTime(), new DateTime());
	}

	@Test(expected = NullPointerException.class)
	public void testNotNullTitle1() {
		@SuppressWarnings("unused")
		CalendarEntry testEntry = new CalendarEntry(
				new UserIdentifier("owner"), null, new DateTime(),
				new DateTime());
	}

	@Test(expected = NullPointerException.class)
	public void testNotNullTitle2() {
		testEntry1.setTitle(null);
	}

	/* Exceptions are bad anyway. */
	@Test(expected = NullPointerException.class)
	public void testNotNullStart() {
		// null == now --> null != now , see javadoc
		@SuppressWarnings("unused")
		CalendarEntry testEntry = new CalendarEntry(
				new UserIdentifier("owner"), "meeting", null, new DateTime());
	}

	@Test(expected = NullPointerException.class)
	public void testNotNullEnd() {
		@SuppressWarnings("unused")
		// null == now --> null != now , see javadoc
		CalendarEntry testEntry = new CalendarEntry(
				new UserIdentifier("owner"), "meeting", new DateTime(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEndBeforeStart1() {
		@SuppressWarnings("unused")
		CalendarEntry testEntry = new CalendarEntry(
				new UserIdentifier("owner"), "meeting", a.plusDays(3), a);
	}

	@Test
	public void testEndBeforeStart2() {
		testEntry1.setStart(a.plusDays(10));
		assertTrue(testEntry1.getEnd().equals(a.plusHours(5).plusDays(10)));
	}

	@Test
	public void testEndBeforeStart3() {
		testEntry1.setEnd(a.minusDays(10));
		assertTrue(testEntry1.getStart().equals(a.minusHours(5).minusDays(10)));
	}

	@Test
	public void testEquals1() {
		CalendarEntry t = testEntry1;

		assertEquals(t, testEntry1);
	}

	@Test
	public void testEquals2() {
		// will never ever be true.
		// assertEquals (testEntry2, testEntry1);
		assertThat(testEntry2, not(equalTo(testEntry1)));
	}

	@Test
	public void testNotEquals() {
		// also not gonna be true.
		// assertEquals (testEntry3, testEntry1);
		assertThat(testEntry3, not(equalTo(testEntry1)));
	}

	@Test
	public void testEquals() {

		CalendarEntry testEntry4 = testEntry1;

		System.out.println("Hashcode1:" + testEntry1.hashCode());
		System.out.println("Hashcode2:" + testEntry2.hashCode());
		System.out.println("Hashcode3:" + testEntry3.hashCode());

		assertEquals(testEntry1, testEntry1);
		assertEquals(testEntry1, testEntry4);
		// also not true.
		// assertTrue(testEntry1.equals(testEntry2));
		assertThat(testEntry1, not(equalTo(testEntry2)));
		// assertFalse(testEntry1.equals(testEntry3));
	}

	@Test
	public void testGetOwner1() {

		assertEquals(new UserIdentifier("owner"), testEntry1.getOwner());
	}

	/*
	 * @Test public void testGetOwner2(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.OWNER); assertEquals(new
	 * UserIdentifier("hermann"), testEntry1.getOwner()); }
	 */

	@Test
	public void testGetID() {

		assertNotSame(null, testEntry1.getIdentifier());
	}

	@Test
	public void testGetTitle() {

		assertEquals("meeting", testEntry1.getTitle());
	}

	@Test
	public void testGetDescription() {

		assertEquals("", testEntry1.getDescription());
	}

	@Test
	public void testGetStart() {
		assertEquals(a, testEntry1.getStart());
	}

	@Test
	public void testGetEnd() {
		assertEquals(a.plusHours(5), testEntry1.getEnd());
	}

	@Test
	public void testSetTitle() {

		testEntry1.setTitle("other");
		assertEquals("other", testEntry1.getTitle());
	}

	@Test
	public void testSetDescription() {

		testEntry1.setDescription("...");
		assertEquals("...", testEntry1.getDescription());
	}

	@Test
	public void testSetStart() {

		testEntry1.setStart(a.minusHours(3));
		assertEquals(a.minusHours(3), testEntry1.getStart());
	}

	@Test
	public void testSetEnd() {

		testEntry1.setEnd(a.plusHours(3));
		assertEquals(a.plusHours(3), testEntry1.getEnd());
	}

	/*
	 * @Test public void testAddCapability1(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.READ); testEntry1.addCapability(new
	 * UserIdentifier("hermann"), CalendarEntryCapability.WRITE);
	 * 
	 * Set<CalendarEntryCapability> l1 = new HashSet<CalendarEntryCapability>();
	 * for (CalendarEntryCapability c : testEntry1.getCapabilities(new
	 * UserIdentifier("hermann"))){ if (c!=null){ l1.add(c); } }
	 * 
	 * Set<CalendarEntryCapability> l2 = new HashSet<CalendarEntryCapability>();
	 * l2.add(CalendarEntryCapability.READ);
	 * l2.add(CalendarEntryCapability.WRITE);
	 * 
	 * assertEquals(l1,l2); }
	 * 
	 * @Test public void testAddCapability2(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.READ); testEntry1.addCapability(new
	 * UserIdentifier("hermann"), CalendarEntryCapability.WRITE);
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.WRITE);
	 * 
	 * Set<CalendarEntryCapability> l1 = new HashSet<CalendarEntryCapability>();
	 * for (CalendarEntryCapability c : testEntry1.getCapabilities(new
	 * UserIdentifier("hermann"))){ if (c!=null){ l1.add(c); } }
	 * 
	 * Set<CalendarEntryCapability> l2 = new HashSet<CalendarEntryCapability>();
	 * l2.add(CalendarEntryCapability.READ);
	 * l2.add(CalendarEntryCapability.WRITE);
	 * 
	 * assertEquals(l1,l2); }
	 * 
	 * @Test public void testRemoveCapability(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.READ); testEntry1.addCapability(new
	 * UserIdentifier("hermann"), CalendarEntryCapability.WRITE);
	 * testEntry1.removeCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.WRITE);
	 * 
	 * Set<CalendarEntryCapability> l1 = new HashSet<CalendarEntryCapability>();
	 * for (CalendarEntryCapability c : testEntry1.getCapabilities(new
	 * UserIdentifier("hermann"))){ if (c!=null){ l1.add(c); } }
	 * 
	 * Set<CalendarEntryCapability> l2 = new HashSet<CalendarEntryCapability>();
	 * l2.add(CalendarEntryCapability.READ);
	 * 
	 * assertEquals(l1,l2); }
	 * 
	 * @Test public void getUsersByCapability1(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.READ); testEntry1.addCapability(new
	 * UserIdentifier("luke"), CalendarEntryCapability.READ);
	 * 
	 * 
	 * Set<UserIdentifier> l1 = new HashSet<UserIdentifier>(); for
	 * (UserIdentifier s :
	 * testEntry1.getUsersByCapability(CalendarEntryCapability.READ)){ if
	 * (s!=null){ l1.add(s); } }
	 * 
	 * Set<UserIdentifier> l2 = new HashSet<UserIdentifier>(); l2.add(new
	 * UserIdentifier("owner")); l2.add(new UserIdentifier("hermann"));
	 * l2.add(new UserIdentifier("luke"));
	 * 
	 * assertEquals(l1,l2); }
	 * 
	 * @Test public void getUsersByCapability2(){
	 * 
	 * testEntry1.addCapability(new UserIdentifier("hermann"),
	 * CalendarEntryCapability.READ); testEntry1.addCapability(new
	 * UserIdentifier("luke"), CalendarEntryCapability.READ);
	 * testEntry1.removeCapability(new UserIdentifier("luke"),
	 * CalendarEntryCapability.READ);
	 * 
	 * Set<UserIdentifier> l1 = new HashSet<UserIdentifier>(); for
	 * (UserIdentifier s :
	 * testEntry1.getUsersByCapability(CalendarEntryCapability.READ)){ if
	 * (s!=null){ l1.add(s); } }
	 * 
	 * Set<UserIdentifier> l2 = new HashSet<UserIdentifier>(); l2.add(new
	 * UserIdentifier("owner")); l2.add(new UserIdentifier("hermann"));
	 * 
	 * assertEquals(l1,l2); }
	 */

	@Test
	public void countingTest1() {
		CalendarEntry e = new CalendarEntry(new UserIdentifier(), "test",
				new DateTime(), new DateTime().plusHours(1), "desc",
				Period.days(2), -1);

		int count = 0;
		for (Interval i : e.getEntryList(11)) {
			System.out.println(i);
			count++;
		}

		assertEquals(11, count);
	}

	@Test
	public void countingTest2() {
		DateTime now = new DateTime();

		CalendarEntry e = new CalendarEntry(new UserIdentifier(), "test", now,
				now.plusHours(1), "desc", Period.hours(2), -1);

		int count = 0;
		for (Interval i : e.getEntryList(new Interval(now.plusHours(12), now
				.plusHours(24)))) {
			System.out.println(i);
			count++;
		}

		assertEquals(6, count);
	}
}