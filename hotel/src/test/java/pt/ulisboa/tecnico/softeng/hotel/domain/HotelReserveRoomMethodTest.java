package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

import static junit.framework.TestCase.assertTrue;

public class HotelReserveRoomMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private final double SINGLE_PRICE = 200;
	private final double DOUBLE_PRICE = 300;
	private static final String NIFHotel = "123456788";
	private static final String IBANHotel = "IBAC";
	private static final String NIF = "123456789";
	private static final String IBAN = "IBAN";
	private Room room;
	private Hotel hotel;

	@Before
	public void setUp() {
		hotel = new Hotel("XPTO123", "Lisboa", NIFHotel, IBANHotel, SINGLE_PRICE, DOUBLE_PRICE);
		this.room = new Room(hotel, "01", Room.Type.SINGLE);
	}

	@Test
	public void success() {
		String ref = Hotel.reserveRoom(Room.Type.SINGLE, arrival, departure, NIF, IBAN);
		assertTrue(ref.startsWith("XPTO12"));
	}

	@Test(expected = HotelException.class)
	public void noHotels() {
		Hotel.hotels.clear();
		Hotel.reserveRoom(Room.Type.SINGLE, arrival, departure, NIF, IBAN);
	}

	@Test(expected = HotelException.class)
	public void noVacancy() {
		hotel.removeRooms();
		String ref = Hotel.reserveRoom(Room.Type.SINGLE, arrival, new LocalDate(2016, 12, 25), NIF, IBAN);
		System.out.println(ref);
	}

	@Test(expected = HotelException.class)
	public void noRooms() {
		hotel.removeRooms();
		Hotel.reserveRoom(Room.Type.SINGLE, arrival, new LocalDate(2016, 12, 25), NIF, IBAN);
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}