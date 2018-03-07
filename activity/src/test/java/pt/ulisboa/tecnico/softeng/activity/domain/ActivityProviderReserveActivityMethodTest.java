package pt.ulisboa.tecnico.softeng.activity.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityProviderReserveActivityMethodTest {
	private static final int MIN_AGE = 18;
	private static final int MAX_AGE = 80;
	private static final int AGE = 50;
	private static final int CAPACITY = 3;

	private static final String NAME = "ExtremeAdventure";
	private static final String CODE = "XtremX";

	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private ActivityProvider provider;
	private ActivityOffer offer;
	private String reference;

	@Before
	public void setUp() {
		this.provider = new ActivityProvider(CODE, NAME);

		Activity activity = new Activity(this.provider, "Bush Walking", MIN_AGE, MAX_AGE, CAPACITY);
		this.offer = new ActivityOffer(activity, this.begin, this.end);
	}

	@Test
	public void success() {
		reference = this.provider.reserveActivity(this.begin, this.end, AGE);
		Booking booking = this.offer.getBooking(reference);

		assertEquals(reference, booking.getReference());
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
