package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class Renting {
	private String reference;
	private String drivingLicense;
	private LocalDate begin;
	private LocalDate end;
	private int kilometers;
	private Vehicle vehicle = null;

	public String getReference() {
		return this.reference;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public LocalDate getBeginDate() {
		return this.begin;
	}

	public LocalDate getEndDate() {
		return this.end;
	}

	public int getKilometers() {
		return this.kilometers;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public void setKilometers(int km) {
		if (km < this.kilometers) {
			throw new CarException("Car kilometers may only be raised.");
		} else {
			this.kilometers = km;
		}
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void increaseKilometers(int km) {
		if (km < 0) {
			throw new CarException("Car kilometers may only be raised.");
		} else {
			this.kilometers += km;
		}
	}

	public boolean conflict(LocalDate begin, LocalDate end) {
		return false;
	}

	public void checkout(int kilometers) {
	
	}
}
