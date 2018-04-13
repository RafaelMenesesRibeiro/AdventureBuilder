package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking extends Booking_Base {
	private static final String HOUSING_TYPE = "HOUSING";

	public Booking(Room room, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		checkArguments(room, arrival, departure, buyerNif, buyerIban);

		setRoom(room);
		setArrival(arrival);
		setDeparture(departure);

		setReference(room.getHotel().getCode() + Integer.toString(room.getHotel().getCounter()));

		super.setProviderNif(room.getHotel().getNif());
		super.setBuyerNif(buyerNif);
		super.setBuyerIban(buyerIban);
		super.setPrice(room.getHotel().getPrice(room.getType()) * Days.daysBetween(arrival, departure).getDays());

		setCancelledInvoice(false);
	}

	@Override
	public void setBuyerIban(String buyerIban) { /* do nothing - buyer iban is final */	}

	@Override
	public void setBuyerNif(String buyerNif) { /* do nothing - buyer nif is final */ }

	@Override
	public void setPrice(double price) { /* do nothing - booking price for a room is final */	}

	@Override
	public void setProviderNif(String providerNif) { /* do nothing - provider nif is final	*/ }

	public void delete() {
		setProcessor(null);
		setRoom(null);

		deleteDomainObject();
	}

	private void checkArguments(Room room, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		if (room == null || arrival == null || departure == null || buyerNif == null || buyerNif.trim().length() == 0
				|| buyerIban == null || buyerIban.trim().length() == 0) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}
	}

	public static String getType() {
		return HOUSING_TYPE;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(getDeparture())) {
			return true;
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if ((arrival.equals(getArrival()) || arrival.isAfter(getArrival())) && arrival.isBefore(getDeparture())) {
			return true;
		}

		if ((departure.equals(getDeparture()) || departure.isBefore(getDeparture()))
				&& departure.isAfter(getArrival())) {
			return true;
		}

		if (arrival.isBefore(getArrival()) && departure.isAfter(getDeparture())) {
			return true;
		}

		return false;
	}

	public boolean isCancelledInvoice() {
		return getCancelledInvoice();
	}

	public String cancel() {
		setCancellation(getReference() + "CANCEL");
		setCancellationDate(new LocalDate());

		getRoom().getHotel().getProcessor().submitBooking(this);

		return getCancellation();
	}

	public boolean isCancelled() {
		return getCancellation() != null;
	}
}
