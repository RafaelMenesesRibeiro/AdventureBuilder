package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class RoomGetBookingMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private final double SINGLE_PRICE = 200;
	private final double DOUBLE_PRICE = 300;
	private static final String NIFHotel = "123456788";
	private static final String IBANHotel = "IBAC";
	private static final String NIF = "123456789";
	private static final String IBAN = "IBAN";
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Lisboa", NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
		this.room = new Room(this.hotel, "01", Type.SINGLE);
		this.booking = this.room.reserve(Type.SINGLE, this.arrival, this.departure, NIF, IBAN);
	}

	@Test
	public void success() {
		assertEquals(this.booking, this.room.getBooking(this.booking.getReference()));
	}

	@Test
	public void successCancelled() {
		this.booking.cancel();

		assertEquals(this.booking, this.room.getBooking(this.booking.getCancellation()));
	}

	@Test
	public void doesNotExist() {
		assertNull(this.room.getBooking("XPTO"));
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
