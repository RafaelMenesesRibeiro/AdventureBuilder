package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.Set;
import java.util.HashSet;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Ignore;

import pt.ulisboa.tecnico.softeng.broker.domain.BulkRoomBooking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

@Ignore public class BulkRoomBookingProcessBookingMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Test
	public void success() {
		
		String hotelCode = "GRCMeAt";
		Hotel hotel1 = new Hotel(hotelCode, "Melia Athens");
		Room.Type roomType1 = Room.Type.DOUBLE;
		Room room11 = new Room(hotel1, "1", roomType1);
		Room room12 = new Room(hotel1, "2", roomType1);

		int roomsNumber = 2;
		BulkRoomBooking bulk = new BulkRoomBooking(roomsNumber, this.begin, this.end);
		bulk.processBooking();

		Set<String> testRefs = bulk.getReferences();
		Assert.assertEquals(roomsNumber, testRefs.size());
		
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
}
