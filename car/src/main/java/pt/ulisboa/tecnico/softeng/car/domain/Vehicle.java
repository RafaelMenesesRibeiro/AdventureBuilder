package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public abstract class Vehicle extends Vehicle_Base{
	private static Logger logger = LoggerFactory.getLogger(Vehicle.class);

	private static String plateFormat = "..-..-..";
	static Set<String> plates = new HashSet<>();

	private RentACar rentACar;

	public Vehicle(String plate, int kilometers, double price, RentACar rentACar) {
		logger.debug("Vehicle plate: {}", plate);
		checkArguments(plate, kilometers, rentACar);

		this.setPlate(plate);
		this.setKilometers(kilometers);
		this.setPrice(price);
		this.setRentACar(rentACar);

		plates.add(plate.toUpperCase());
		rentACar.addVehicle(this);
	}

	public Vehicle() {}

	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}

	public RentACar getRentACar() {
		return rentACar;
	}

	public void checkArguments(String plate, int kilometers, RentACar rentACar) {
		if (plate == null || !plate.matches(plateFormat) || plates.contains(plate.toUpperCase())) {
			throw new CarException();
		} else if (kilometers < 0) {
			throw new CarException();
		} else if (rentACar == null) {
			throw new CarException();
		}
	}

	/**
	 * @param kilometers
	 *            the kilometers to set
	 */
	public void addKilometers(int kilometers) {
		if (kilometers < 0) {
			throw new CarException();
		}
		this.setKilometers(this.getKilometers() + kilometers);
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new CarException();
		}
		for (Renting renting : this.getRentingSet()) {
			if (renting.conflict(begin, end)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Lookup for a <code>Renting</code> with the given reference.
	 *
	 * @param reference
	 * @return Renting with the given reference
	 */
	public Renting getRenting(String reference) {
		return this.getRentingSet()
				.stream()
				.filter(renting -> renting.getReference().equals(reference)
                        || renting.isCancelled() && renting.getCancellationReference().equals(reference))
				.findFirst()
				.orElse(null);
	}

	/**
	 * @param drivingLicense
	 * @param begin
	 * @param end
	 * @return
	 */
	public Renting rent(String drivingLicense, LocalDate begin, LocalDate end, String buyerNIF, String buyerIBAN) {
		if (!isFree(begin, end)) {
			throw new CarException();
		}

		Renting renting = new Renting(drivingLicense, begin, end, this, buyerNIF, buyerIBAN);
		this.addRenting(renting);

        this.getRentACar().getProcessor().submitRenting(renting);


        return renting;
	}
}
