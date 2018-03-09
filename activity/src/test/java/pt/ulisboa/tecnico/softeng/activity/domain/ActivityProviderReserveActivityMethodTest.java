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

	@Before
	public void setUp() {
		this.provider = new ActivityProvider(CODE, NAME);
	}

	@Test
	public void success() {

		Activity activity = new Activity(this.provider, "Bush Walking", MIN_AGE, MAX_AGE, CAPACITY);
		ActivityOffer offer = new ActivityOffer(activity, this.begin, this.end);

		String reference = this.provider.reserveActivity(this.begin, this.end, AGE);
		Booking booking = offer.getBooking(reference);

		assertEquals(reference, booking.getReference());
	}

	@Test(expected = ActivityException.class)
	public void noCapacity() {
		new Activity(this.provider, "Bush Walking", MIN_AGE, MAX_AGE, 0);
		this.provider.reserveActivity(this.begin, this.end, AGE);
	}

	@Test(expected = ActivityException.class)
	public void noOffers() {
		this.provider.reserveActivity(this.begin, this.end, AGE);
	}

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
