package pt.ulisboa.tecnico.softeng.broker.interfaces;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;

public class CarInterface {
	//TODO: NEEDS TO RESERVE CAR FROM CAR MODULE.
	public static String reserveCar() {
		return "Car Reserved.";
	}

	public static String cancelRenting(String vehicleConfirmation) {
		return "Car Cancelled.";
	}

	public static Renting getRentingData(String reference) {
		return RentACar.getRenting(reference);
	}

}
