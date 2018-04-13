package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class HotelPersistenceTest {
	private static final String HOTEL_NAME = "Berlin Plaza";
	private static final String HOTEL_CODE = "H123456";
	private static final String ROOM_NUMBER = "01";

	private final LocalDate arrival = new LocalDate(2017, 12, 15);
	private final LocalDate departure = new LocalDate(2017, 12, 19);

	@Test
	public void successOne() {
		atomicProcess();
		atomicAssertOne();
	}

	@Test
	public void successTwo() {
		atomicProcess();
		atomicAssertTwo();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, "123456789", "IBAN", 10.0, 20.0);
		Room room = new Room(hotel, ROOM_NUMBER, Type.DOUBLE);
		room.reserve(Type.DOUBLE, this.arrival, this.departure, "123456789", "IBAN");
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssertOne() {
		Hotel hotel = Hotel.getHotelByCode(HOTEL_CODE);
		assertEquals(HOTEL_NAME, hotel.getName());
		assertEquals(HOTEL_CODE, hotel.getCode());
		assertEquals(1, hotel.getRoomSet().size());
		assertNotNull(hotel.getProcessor());

		Processor processor = hotel.getProcessor();
		assertNotNull(processor.getHotel());
		assertEquals(HOTEL_NAME, processor.getHotel().getName());
		assertEquals(HOTEL_CODE, processor.getHotel().getCode());
		assertTrue(processor.getBookingToProcessSet().isEmpty());

		List<Room> hotels = new ArrayList<>(hotel.getRoomSet());
		Room room = hotels.get(0);
		assertEquals(ROOM_NUMBER, room.getNumber());
		assertEquals(Type.DOUBLE, room.getType());
		assertEquals(1, room.getBookingSet().size());

		List<Booking> bookings = new ArrayList<>(room.getBookingSet());
		Booking booking = bookings.get(0);
		assertNull(booking.getProcessor());
		assertEquals(this.arrival, booking.getArrival());
		assertEquals(this.departure, booking.getDeparture());
		assertNotNull(booking.getReference());
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicAssertTwo() {
		Hotel hotel = Hotel.getHotelByCode(HOTEL_CODE);
		Room room = new ArrayList<>(hotel.getRoomSet()).get(0);
		Booking booking = new ArrayList<>(room.getBookingSet()).get(0);
		Processor processor = hotel.getProcessor();
		processor.getBookingToProcessSet().add(booking);
		assertNotNull(booking.getProcessor());
		assertEquals(1, processor.getBookingToProcessSet().size());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (Hotel hotel : FenixFramework.getDomainRoot().getHotelSet()) {
			hotel.delete();
		}
	}

}
