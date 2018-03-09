package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class Motorcycle extends Vehicle {
	public Motorcycle(String plate, int kilometers, RentACar dealer) {
		super(plate, kilometers, dealer);
	}
}
