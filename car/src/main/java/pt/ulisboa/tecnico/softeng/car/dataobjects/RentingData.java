package pt.ulisboa.tecnico.softeng.car.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;


public class RentingData {
	private String reference;
	private String plate;
	private String drivingLicense;
	private String rentACarCode;
	private LocalDate begin;
	private LocalDate end;

	public RentingData() {}

	public RentingData(String reference, String plate, String drivingLicense, String rentACarCode, LocalDate begin, LocalDate end) {
		this.reference = reference;
		this.plate = plate;
		this.drivingLicense = drivingLicense;
		this.rentACarCode = rentACarCode;
		this.begin = begin;
		this.end = end;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getReference() {
		return this.reference;
	}

	public String getPlate() {
		return this.plate;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public String getRentACarCode() {
		return this.rentACarCode;
	}

	public LocalDate getBeginDate() {
		return this.begin;
	}

	public LocalDate getEndDate() {
		return this.end;
	}
}
