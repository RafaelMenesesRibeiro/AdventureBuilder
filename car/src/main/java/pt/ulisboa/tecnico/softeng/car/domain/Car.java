package pt.ulisboa.tecnico.softeng.car.domain;

public class Car extends Car_Base {

	public Car() { super(); }

	public Car(String plate, int kilometers, double price, RentACar rentACar) {
		super.checkArguments(plate, kilometers, rentACar);

		this.setPlate(plate);
		this.setKilometers(kilometers);
		this.setPrice(price);
		this.setRentACar(rentACar);

		plates.add(plate.toUpperCase());
		rentACar.addVehicle(this);
	}
}
