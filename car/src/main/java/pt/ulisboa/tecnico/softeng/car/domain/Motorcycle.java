package pt.ulisboa.tecnico.softeng.car.domain;

public class Motorcycle extends Motorcycle_Base {
	
	public Motorcycle() { super(); }

	public Motorcycle(String plate, int kilometers, double price, RentACar rentACar) {
		super.checkArguments(plate, kilometers, rentACar);

		this.setPlate(plate);
		this.setKilometers(kilometers);
		this.setPrice(price);
		this.setRentACar(rentACar);

		plates.add(plate.toUpperCase());
		rentACar.addVehicle(this);
	}

}
