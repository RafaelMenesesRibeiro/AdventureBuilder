package pt.ulisboa.tecnico.softeng.broker.interfaces;

public class CarInterface {
	//TODO: NEEDS TO RESERVE CAR FROM CAR MODULE.
	public static String reserveCar() {
		return "Car Reserved.";
	}

	public static String cancelRenting(String vehicleConfirmation) {
		return "Car Cancelled.";
	}

}
