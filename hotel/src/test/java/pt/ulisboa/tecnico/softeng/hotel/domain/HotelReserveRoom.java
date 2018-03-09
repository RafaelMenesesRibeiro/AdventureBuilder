package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelReserveRoom {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris");
	}

	@Test
	public void success() {
	
		Room room = new Room(this.hotel, "01", Type.SINGLE);
		String reference = this.hotel.reserveRoom(Type.SINGLE, this.arrival, this.departure);
		Booking booking = new Booking(this.hotel, this.arrival, this.departure);

		assertEquals(reference, booking.getReference());
	}

	@Test(expected = HotelException.class)
	public void noHotelAvailable() {
		Hotel.hotels.clear();
		this.hotel.reserveRoom(Type.SINGLE, this.arrival, this.departure);
	}

	@Test(expected = HotelException.class)
	public void noRoomAvailable() {
		Room room = new Room(this.hotel, "01", Type.SINGLE);
		this.hotel.reserveRoom(Type.DOUBLE, this.arrival, this.departure);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
