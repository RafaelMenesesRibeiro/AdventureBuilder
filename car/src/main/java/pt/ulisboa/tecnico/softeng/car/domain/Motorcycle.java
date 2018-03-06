package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class Motorcycle extends Vehicle {

	public Motorcycle(String plate, RentACar dealer) {
		super(plate, dealer);
		// TODO Auto-generated constructor stub
	}
	
	public Motorcycle(String plate, RentACar dealer, int kilometers) {
		super(plate, dealer, kilometers);
		// TODO Auto-generated constructor stub
	}

}