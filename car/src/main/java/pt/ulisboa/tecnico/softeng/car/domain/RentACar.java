package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;

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
	private final String code;
	private final String name;

	public rentACar(String name) {
		checkArguments(name);
		this.name = name;
	}

	public RentACar(String code, String name) {
		checkArguments(code, name);
		this.code = code;
		this.name = name;
		RentACar.rentingCompanies.add(this);
	}

	private void checkArguments(String name) {
		checkName(name);
	}

	private void checkArguments(String code, String name) {
		checkArguments(name);
		checkCode(code);
	}

	private void checkCode(String code) {
		if (code == null) {
			throw new CarException("Code cannot be null.");
		} if (code.trim().length() != RentACar.CODE_SIZE) {
			throw new CarException("Code length must be composed of exactly four alphanumeric characters.");
		}

		for (RentACar rentingCompany : rentingCompanies) {
			if (rentingCompany.getCode().equals(code)) {
				throw new CarException("A company with this code as already been registred.");
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new CarException("Name must have at least more than a character and cannot be null.");
		}
	}

	public Renting getRenting(String reference) {
		// TODO
	}

  public List<Car> getAllAvailableCars(LocalDate begin, LocalDate end) {
		// TODO
	}

	public List<Motorcycle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end) {
		// TODO
	}

	public RentingData getRentingData(String reference) {
		// TODO
	}
}
