package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class Car extends Vehicle {
	public Car(String plate, RentACar dealer) {
		super(plate, dealer);
	}

	public Car(String plate, int kilometers, RentACar dealer) {
		super(plate, kilometers, dealer);
	}
}
