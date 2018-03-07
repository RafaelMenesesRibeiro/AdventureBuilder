package pt.ulisboa.tecnico.softeng.car.domain;

import java.time.LocalDateTime;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {
	private String reference;
	private String drivingLicense;
	private LocalDate begin;
	private LocalDate end;
	private int kilometers;

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

	public void increaseKilometers(int km) {
		if (km < 0) {
			throw new CarException("Car kilometers may only be raised.");
		} else {
			this.kilometers += km;
		}
	}
}
