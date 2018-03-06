package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;

/**
* Represents a company that rents cars.
* Each rental company knows may rent two types of vehicles. Cars or Motorcycles.
* It's identified by a unique code and it has some name.
* All Companies know about the existence of other companies through a static HashSet of RentingCompanies.
*/
public class RentACar {
	public static Set<RentACar> rentingCompanies = new HashSet<>();
	public static final int CODE_SIZE = 4;
	public static AtomicInteger uniqueCode = new AtomicInteger(1000);
	private final String code;
	private final String name;
	private List<Vehicle> vehicleList;

	public RentACar(String name) {
		String code = String.valueOf(uniqueCode.incrementAndGet());
		checkArguments(code, name);
		this.code = code;
		this.name = name;
		this.vehicleList = new ArrayList<Vehicle>();
		RentACar.rentingCompanies.add(this);
	}

	public RentACar(String code, String name) {
		checkArguments(code, name);
		this.code = code;
		this.name = name;
		this.vehicleList = new ArrayList<Vehicle>();
		RentACar.rentingCompanies.add(this);
	}

	private void checkArguments(String name) {
		checkName(name);
	}

	private void checkArguments(String code, String name) {
		checkCode(code);
		checkArguments(name);
	}

	private void checkCode(String code) {
		if (code == null) {
			throw new CarException("Code cannot be null.");
		} if (code.trim().length() != RentACar.CODE_SIZE) {
			throw new CarException("Code length must be composed of exactly four alphanumeric characters.");
		}

		for (RentACar rentingCompany : rentingCompanies) {
			if (String.valueOf(rentingCompany.getCode()).equals(code)) {
				throw new CarException("A company with this code as already been registred.");
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new CarException("Name must have at least more than a character and cannot be null.");
		}
	}

	public void addVehicle(Vehicle vehicle) {
		this.vehicleList.add(vehicle);
	}

	public Renting getRenting(String reference) {
		return null;
		// TODO
	}

	// TODO
  public List<Car> getAllAvailableCars(LocalDate begin, LocalDate end) {
		List<Car> temp = new ArrayList<Car>();
		for(Vehicle vehicle : vehicleList) {
			if (vehicle instanceof Car) {
				temp.add((Car) vehicle);
			}
		}
		return temp;
	}

	// TODO
	public List<Motorcycle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end) {
		List<Motorcycle> temp = new ArrayList<Motorcycle>();
		for(Vehicle vehicle : vehicleList) {
			if (vehicle instanceof Motorcycle) {
				temp.add((Motorcycle) vehicle);
			}
		}
		return temp;
	}

	public RentingData getRentingData(String reference) {
		return null;
		// TODO
	}

	public String getName() {
		return this.name;
	}

	public int getCode() {
		return this.code;
	}

	public List<Vehicle> getVehicleList() {
		return this.vehicleList;
	}
}
