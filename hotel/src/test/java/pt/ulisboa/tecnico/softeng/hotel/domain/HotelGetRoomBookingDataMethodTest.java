package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelGetRoomBookingDataMethodTest {
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
		RoomBookingData data = Hotel.getRoomBookingData(this.booking.getReference());

		assertEquals(this.booking.getReference(), data.getReference());
		assertNull(data.getCancellation());
		assertNull(data.getCancellationDate());
		assertEquals(this.hotel.getName(), data.getHotelName());
		assertEquals(this.hotel.getCode(), data.getHotelCode());
		assertEquals(this.room.getNumber(), data.getRoomNumber());
		assertEquals(this.room.getType().name(), data.getRoomType());
		assertEquals(this.booking.getArrival(), data.getArrival());
		assertEquals(this.booking.getDeparture(), data.getDeparture());
	}

	@Test
	public void successCancellation() {
		this.booking.cancel();
		RoomBookingData data = Hotel.getRoomBookingData(this.booking.getCancellation());

		assertEquals(this.booking.getReference(), data.getReference());
		assertEquals(this.booking.getCancellation(), data.getCancellation());
		assertEquals(this.booking.getCancellationDate(), data.getCancellationDate());
		assertEquals(this.hotel.getName(), data.getHotelName());
		assertEquals(this.hotel.getCode(), data.getHotelCode());
		assertEquals(this.room.getNumber(), data.getRoomNumber());
		assertEquals(this.room.getType().name(), data.getRoomType());
		assertEquals(this.booking.getArrival(), data.getArrival());
		assertEquals(this.booking.getDeparture(), data.getDeparture());
	}

	@Test(expected = HotelException.class)
	public void nullReference() {
		Hotel.getRoomBookingData(null);
	}

	@Test(expected = HotelException.class)
	public void emptyReference() {
		Hotel.getRoomBookingData("");
	}

	@Test(expected = HotelException.class)
	public void referenceDoesNotExist() {
		Hotel.getRoomBookingData("XPTO");
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
