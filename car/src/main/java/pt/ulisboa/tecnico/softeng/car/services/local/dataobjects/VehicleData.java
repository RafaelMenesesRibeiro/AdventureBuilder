package pt.ulisboa.tecnico.softeng.car.services.local.dataobjects;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class VehicleData {
	private String plate;
	private int kilometers;
	private double price;
	private List<RentingData> rentings;

	public VehicleData() {
	}

	public VehicleData(Vehicle vehicle) {
		this.plate = vehicle.getPlate();
		this.kilometers = vehicle.getKilometers();
		this.price = vehicle.getPrice();
		this.rentings = vehicle.getRentingSet().stream().map(r -> new RentingData(r)).collect(Collectors.toList());
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getKilometers() {
		return this.kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<RentingData> getRentings() {
		return this.rentings;
	}

	public void setRenting (List<RentingData> rentings) {
		this.rentings = rentings;
	}

}
