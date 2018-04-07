package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConstructorTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private final double SINGLE_PRICE = 200;
	private final double DOUBLE_PRICE = 300;
	private static final String NIF = "123456789";
	private static final String IBAN = "IBAN";
	private Hotel hotel;
	private Room room;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", SINGLE_PRICE, DOUBLE_PRICE);
		this.room = new Room(hotel, "01", Room.Type.SINGLE);
	}

	@Test
	public void success() {
		Booking booking = new Booking(this.hotel, this.room, this.arrival, this.departure, NIF, IBAN);

		Assert.assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(this.room.getNumber(), booking.getRoom().getNumber());
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(NIF, booking.getNif());
		Assert.assertEquals(IBAN, booking.getIban());
		Assert.assertEquals(this.departure, booking.getDeparture());
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(null, this.room, this.arrival, this.departure, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullRoom() {
		new Booking(this.hotel, null, this.arrival, this.departure, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(this.hotel, this.room, null, this.departure, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(this.hotel, this.room, this.arrival, null, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void departureBeforeArrival() {
		new Booking(this.hotel, this.room, this.arrival, this.arrival.minusDays(1), NIF, IBAN);
	}

	@Test
	public void arrivalEqualDeparture() {
		new Booking(this.hotel, this.room, this.arrival, this.arrival, NIF, IBAN);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
