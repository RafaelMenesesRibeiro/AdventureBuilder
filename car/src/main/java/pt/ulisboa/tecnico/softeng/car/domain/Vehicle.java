package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;

public class Vehicle {
	public static final int CODE_SIZE = 8;
	private final String plate;
	private List<Renting> rentingsList;
	private int kilometers;
	private RentACar dealer;

	public Vehicle(String plate, int kilometers, RentACar dealer) {
		checkArguments(plate, kilometers, dealer);
		this.plate = plate;
		this.rentingsList = new ArrayList<Renting>();
		this.kilometers = kilometers;
		this.dealer = dealer;
		this.dealer.addVehicle(this);
	}

	private void checkArguments(String plate, RentACar dealer) {
		checkDealer(dealer);
		checkPlate(plate, dealer);
	}

	private void checkArguments(String plate, int kilometers, RentACar dealer) {
		checkArguments(plate, dealer);
		checkKilometers(kilometers);
	}

	private void checkPlate(String plate, RentACar dealer) {
		if (plate == null) {
			throw new CarException("Plate cannot be null.");
		}

		checkPlateFormat(plate);

		for (Vehicle vehicle : dealer.getVehicleList()) {
			if (vehicle.getPlate().equals(plate)) {
				throw new CarException("The chosen plate is already in use.");
			}
		}
	}

	/*
	* @param: plate
	* Verifies if plate identifier is conformant with the expected format using
	* matches(String Regex) from String java8 class. Regex pattern is defined
	* using codes @see https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#sum
	*/
	private void checkPlateFormat(String plate) {
		if (!(plate.matches("\\w\\w-\\w\\w-\\w\\w"))) {
			throw new CarException("Plate format is XX-YY-ZZ.");
		}
	}

	private void checkDealer(RentACar dealer) {
		if (dealer == null) {
			throw new CarException("A non null dealer must be associated with this car.");
		}
	}

	private void checkKilometers(int kilometers) {
		if (kilometers < 0) {
			throw new CarException("No car can have less than zero kilometers.");
		}
	}

	public Renting getRenting(String reference) {
        for (Renting renting : this.rentingsList) {
            if (renting.getReference().equals(reference)) {
                return renting;
            }
        }
        return null;
    }

	public String getPlate() {
		return this.plate;
	}

	public int getKilometers() {
		return this.kilometers;
	}

	public void increaseKilometers(int km) {
		if (km < 0) {
			throw new CarException("Car kilometers may only be raised.");
		} else {
			this.kilometers += km;
		}
	}

	public RentACar getDealer() {
		return this.dealer;
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new CarException("At least one of (begin or end dates) is null.");
		}

		if (end.isBefore(begin)) {
			throw new CarException("End of renting period can't happen before the start of the renting period.");
		}
		
		for (Renting renting : this.rentingsList) {
			if (renting.conflict(begin, end)) {
				return false;
			}
		}
		return true;
	}

	public Renting rent(String drivingLicense, LocalDate begin, LocalDate end) {
		if (drivingLicense == null || begin == null || end == null) {
			throw new CarException("At least one of (drivingLicense, begin or end dates) is null.");
		} if (!isFree(begin, end)) {
			throw new CarException("Vehicle is already rented to someone else during the chosen period.");
		}

		Renting renting = new Renting(drivingLicense, begin, end, this);
		this.rentingsList.add(renting);

		return renting;
	}
}
