package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting extends Renting_Base {
	private static String drivingLicenseFormat = "^[a-zA-Z]+\\d+$";
	private static final String type = "RENTAL";

	public Renting(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle, String buyerNIF,
			String buyerIBAN) {
		checkArguments(drivingLicense, begin, end, vehicle);
		this.setReference(Integer.toString(vehicle.getRentACar().getNextCounter()));
		this.setDrivingLicense(drivingLicense);
		this.setBegin(begin);
		this.setEnd(end);
		this.setVehicle(vehicle);
		this.setClientNIF(buyerNIF);
		this.setClientIBAN(buyerIBAN);
		this.setPrice(vehicle.getPrice() * (end.getDayOfYear() - begin.getDayOfYear()));
		this.setKilometers(-1);
		this.setCancelledInvoice(false);
		this.setCancelledPaymentReference(null);
	}

	private void checkArguments(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle) {
		if (drivingLicense == null || !drivingLicense.matches(drivingLicenseFormat) || begin == null || end == null
				|| vehicle == null || end.isBefore(begin)) {
			throw new CarException();
		}
	}

	public boolean isCancelled() {
		return this.getCancellationReference() != null && this.getCancellationDate() != null;
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
		} else if (begin.isBefore(this.getBegin()) && end.isAfter(this.getEnd())) {
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
		this.setKilometers(kilometers);
		this.getVehicle().addKilometers(this.getKilometers());
	}

	public String cancel() {
		this.setCancellationReference(this.getReference() + "CANCEL");
		this.setCancellationDate(LocalDate.now());

		this.getVehicle().getRentACar().getProcessor().submitRenting(this);

		return this.getCancellationReference();
	}

	public boolean isCancelledInvoice() {
		return this.getCancelledInvoice();
	}

	public String getCancellation() {
		return this.getCancel();
	}

	public String getType() {
		return this.type;
	}

}
