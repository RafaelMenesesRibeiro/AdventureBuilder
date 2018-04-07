package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {
	private static String drivingLicenseFormat = "^[a-zA-Z]+\\d+$";
	private static int counter;

	private final RentACar rentACar;
	private final String reference;
	private final String providerNif;
	private final String nif;
	private final String iban;
	private final String drivingLicense;
	private final LocalDate begin;
	private final LocalDate end;
	private int kilometers = -1;
	private final Vehicle vehicle;
	private String paymentReference;
	private String invoiceReference;
	private String cancel;
	private LocalDate cancellationDate;
	private boolean cancelledInvoice = false;
	private String cancelledPaymentReference = null;

	public Renting(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle, String buyerNif, String buyerIban) {
		checkArguments(drivingLicense, begin, end, vehicle, buyerNif, buyerIban);

		this.reference = Integer.toString(++Renting.counter);
		this.rentACar = vehicle.getRentACar();
		this.providerNif = rentACar.getNif();
		this.nif = buyerNif;
		this.iban = buyerIban;
		this.drivingLicense = drivingLicense;
		this.begin = begin;
		this.end = end;
		this.vehicle = vehicle;
	}

	private void checkArguments(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle, String buyerNif, String buyerIban) {
		if (drivingLicense == null || !drivingLicense.matches(drivingLicenseFormat) || begin == null || end == null || vehicle == null
				|| end.isBefore(begin) || buyerNif == null || buyerNif.trim().length() == 0 || buyerIban == null || buyerIban.trim().length() == 0)
			throw new CarException();
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @return the drivingLicense
	 */
	public String getDrivingLicense() {
		return drivingLicense;
	}

	/**
	 * @return the begin
	 */
	public LocalDate getBegin() {
		return begin;
	}

	/**
	 * @return the end
	 */
	public LocalDate getEnd() {
		return end;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @return the rentACar company nif
	 */
	public String getProviderNif() {
		return this.providerNif;
	}

	/**
	 * @return the nif of the customer who is renting
	 */
	public String getNif() {
		return this.nif;
	}

	/**
	 * @return the iban of the customer who is renting
	 */
	public String getIban() {
		return this.iban;
	}

	/**
	 * @param begin
	 * @param end
	 * @return <code>true</code> if this Renting conflicts with the given date
	 *         range.
	 */
	public boolean conflict(LocalDate begin, LocalDate end) {
		if (end.isBefore(begin)) {
			throw new CarException("Error: end date is before begin date.");
		} else if ((begin.equals(this.getBegin()) || begin.isAfter(this.getBegin()))
				&& (begin.isBefore(this.getEnd()) || begin.equals(this.getEnd()))) {
			return true;
		} else if ((end.equals(this.getEnd()) || end.isBefore(this.getEnd()))
				&& (end.isAfter(this.getBegin()) || end.isEqual(this.getBegin()))) {
			return true;
		} else if ((begin.isBefore(this.getBegin()) && end.isAfter(this.getEnd()))) {
			return true;
		}

		return false;
	}

	/**
	 * Settle this renting and update the kilometers in the vehicle.
	 * 
	 * @param kilometers
	 */
	public void checkout(int kilometers) {
		this.kilometers = kilometers;
		this.vehicle.addKilometers(this.kilometers);
	}

	public boolean isCancelled() {
		return this.cancel != null;
	}

	public double getAmount() {
		//return this.vehicle.getCost();
		return 0;
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

	public String getCancellation() {
		return this.cancel;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
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
		this.cancel = "CANCEL" + this.reference;
		this.cancellationDate = new LocalDate();

		this.rentACar.getProcessor().submitRenting(this);

		return this.cancel;
	}
	
}
