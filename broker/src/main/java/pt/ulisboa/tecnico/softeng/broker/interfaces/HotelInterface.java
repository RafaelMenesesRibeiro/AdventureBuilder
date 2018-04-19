package pt.ulisboa.tecnico.softeng.broker.interfaces;

import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class HotelInterface {
	public static String reserveRoom(Room.Type type, LocalDate arrival, LocalDate departure, String buyerNIF,
			String buyerIBAN) {
		return Hotel.reserveRoom(type, arrival, departure, buyerNIF, buyerIBAN);
	}

	public static String cancelBooking(String roomConfirmation) {
		return Hotel.cancelBooking(roomConfirmation);
	}

	public static RoomBookingData getRoomBookingData(String reference) {
		return Hotel.getRoomBookingData(reference);
	}

	public static Set<String> bulkBooking(int number, LocalDate arrival, LocalDate departure, String buyerNIF,
			String buyerIBAN) {
		return Hotel.bulkBooking(number, arrival, departure, buyerNIF, buyerIBAN);
	}

}
