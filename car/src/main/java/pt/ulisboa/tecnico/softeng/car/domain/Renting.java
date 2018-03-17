package pt.ulisboa.tecnico.softeng.car.domain;

import java.lang.Character;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class Renting {
	private static int counter = 0;
	private String reference;
	private String drivingLicense;
	private LocalDate begin;
	private LocalDate end;
	private Vehicle vehicle;
	private int kilometers;

	public Renting(String drivingLicense, LocalDate begin, LocalDate end, Vehicle vehicle) {
		this.reference = vehicle.getDealer().getCode() + Integer.toString(++Renting.counter);
		this.drivingLicense = drivingLicense;
		this.begin = begin;
		this.end = end;
		this.vehicle = vehicle;
		this.kilometers = 0;
	}

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

	public void increaseKilometers(int kilometers) {
		if (kilometers < 0) {
			throw new CarException("Car kilometers may only be raised.");
		} else {
			this.kilometers = kilometers;
			this.vehicle.increaseKilometers(kilometers);
		}
	}

	public boolean conflict(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new CarException("At least one of the arguments is null.");
		}

		if (begin.isAfter(end)) {
			throw new CarException("When it comes to renting periods, the start date must happen before end date.");
		}

		if ((begin.equals(this.begin) || begin.isAfter(this.begin)) && begin.isBefore(this.end)) {
			return true;
		}

		if ((end.equals(this.end) || end.isBefore(this.end)) && end.isAfter(this.begin)) {
			return true;
		}

		if ((begin.isBefore(this.begin) && end.isAfter(this.end))) {
			return true;
		}

		return false;
	}

	public void checkout(int kilometers) {
		this.increaseKilometers(kilometers);
	}
}
