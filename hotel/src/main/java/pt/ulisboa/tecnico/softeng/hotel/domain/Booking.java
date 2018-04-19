package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking extends Booking_Base {
	private static final String HOUSING_TYPE = "HOUSING";
	private final double price;
	private final String NIF;
	private final String providerNIF;
	private String paymentReference;
	private String invoiceReference;
	private boolean cancelledInvoice = false;
	private String cancelledPaymentReference = null;
	private final String buyerIBAN;

	public Booking(Room room, LocalDate arrival, LocalDate departure, String buyerNIF, String buyerIBAN) {
		checkArguments(room, arrival, departure, buyerNIF, buyerIBAN);

		setReference(room.getHotel().getCode() + Integer.toString(room.getHotel().getCounter()));
		setArrival(arrival);
		setDeparture(departure);

		this.price = room.getHotel().getPrice(room.getType()) * Days.daysBetween(arrival, departure).getDays();
		this.NIF = buyerNIF;
		this.buyerIBAN = buyerIBAN;
		this.providerNIF = room.getHotel().getNIF();

		setRoom(room);
	}

	public void delete() {
		setRoom(null);

		deleteDomainObject();
	}

	private void checkArguments(Room room, LocalDate arrival, LocalDate departure, String buyerNIF, String buyerIBAN) {
		if (room == null || arrival == null || departure == null || buyerNIF == null || buyerNIF.trim().length() == 0
				|| buyerIBAN == null || buyerIBAN.trim().length() == 0) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}
	}

	public double getPrice() {
		return this.price;
	}

	public String getNIF() {
		return this.NIF;
	}

	public static String getType() {
		return HOUSING_TYPE;
	}

	public String getProviderNIF() {
		return this.providerNIF;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(departure)) {
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
		return this.cancelledInvoice;
	}

	public void setCancelledInvoice(boolean cancelledInvoice) {
		this.cancelledInvoice = cancelledInvoice;
	}

	public String getCancelledPaymentReference() {
		return this.cancelledPaymentReference;
	}

	public void setCancelledPaymentReference(String cancelledPaymentReference) {
		this.cancelledPaymentReference = cancelledPaymentReference;
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

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public String getIBAN() {
		return this.buyerIBAN;
	}
}
