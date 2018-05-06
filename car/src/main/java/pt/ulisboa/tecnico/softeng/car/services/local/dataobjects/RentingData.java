package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.car.domain.Renting;

public class RentingData {
	private String reference;
	private String plate;
	private String drivingLicense;
	private String rentACarCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	private String paymentReference;
	private String invoiceReference;
	private double price;
	private String clientNif;
	private String clientIban;
	private int kilometers;	

	public RentingData() {}

	public RentingData(Renting renting) {
		this.reference = renting.getReference();
		this.plate = renting.getVehicle().getPlate();
		this.drivingLicense = renting.getDrivingLicense();
		this.rentACarCode = renting.getVehicle().getRentACar().getCode();
		this.begin = renting.getBegin();
		this.end = renting.getEnd();
		this.paymentReference = renting.getPaymentReference();
		this.invoiceReference = renting.getInvoiceReference();
		this.price = renting.getPrice();
		this.clientNif = renting.getClientNif();
		this.clientIban = renting.getClientIban();
		this.kilometers = renting.getKilometers();
	}

	/**
	 * @return the renting reference
	 */
	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the vehicle plate
	 */
	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	/**
	 * @return the drivingLicense
	 */
	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public void setDrivingLicense(String drivingLicenseString) {
		this.drivingLicense = drivingLicenseString;
	}

	/**
	 * @return the rentACarCode
	 */
	public String getRentACarCode() {
		return this.rentACarCode;
	}

	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}

	/**
	 * @return the begin
	 */
	public LocalDate getBegin() {
		return this.begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNif() {
		return this.clientNif;
	}

	public void setNif(String nif) {
		this.clientNif = nif;
	}

	public String getIban() {
		return this.clientIban;
	}


	public void setIban(String iban) {
		this.clientIban = iban;
	}


	public int getKilometers() {
		return kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}
}
