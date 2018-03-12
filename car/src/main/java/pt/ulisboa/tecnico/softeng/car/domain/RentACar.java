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
* @member: vehicleList is a list of Vehicle objects belonging to this rental enterprise.
* Do not confuse vehicleList with vehicles in {@link package.Vehicle#vehicles} which is
* a list of all existing vehicles
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
		checkName(name);
		this.code = code;
		this.name = name;
		this.vehicleList = new ArrayList<Vehicle>();
		RentACar.rentingCompanies.add(this);
	}


	private void checkName(String str) {
		if (str == null || str.trim().length() == 0) {
			throw new CarException("Name or reference must have at least more than a character and cannot be null.");
		}
	}

	public void addVehicle(Vehicle vehicle) {
		this.vehicleList.add(vehicle);
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public List<Vehicle> getVehicleList() {
		return this.vehicleList;
	}

	public Renting getRenting(String reference) {
		checkName(reference);
		for (Vehicle vehicle : this.vehicleList) {
			Renting renting = vehicle.getRenting(reference);
			if (renting != null) {
				return renting;
			}
		}
		return null;
	}

	public RentingData getRentingData(String reference) {
		for (RentACar rentingCompany : rentingCompanies) {
			for (Vehicle vehicle : rentingCompany.getVehicleList()) {
				Renting renting = vehicle.getRenting(reference);
				if (renting != null) {
					return newRentingData(reference, vehicle, renting);
				}
			}
		}
		throw new CarException("No renting with given reference was found.");
	}

	private RentingData newRentingData(String reference, Vehicle vehicle, Renting renting) {
		String plate = vehicle.getPlate();
		String drivingLicense = renting.getDrivingLicense();
		String rentACarCode = renting.getVehicle().getDealer().getCode();
		LocalDate begin = renting.getBeginDate();
		LocalDate end = renting.getEndDate();
		return new RentingData(reference, plate, drivingLicense, rentACarCode, begin, end);
	}

  public List<Car> getAllAvailableCars(LocalDate begin, LocalDate end) {
		List<Car> availableCars = new ArrayList<Car>();
		for (RentACar  rentingCompany : RentACar.rentingCompanies){
              for (Vehicle vehicle : rentingCompany.getVehicleList()) {
                  if (vehicle instanceof Car && isAvailable(vehicle, begin, end)) {
                    availableCars.add((Car) vehicle);
                  }
              }
        }
		return availableCars;
	}

	public List<Motorcycle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end) {
		List<Motorcycle> availableMotorcycles = new ArrayList<Motorcycle>();
        for (RentACar  rentingCompany : RentACar.rentingCompanies) {
            for (Vehicle vehicle : rentingCompany.getVehicleList()) {
                if (vehicle instanceof Motorcycle && isAvailable(vehicle, begin, end)) {
                    availableMotorcycles.add((Motorcycle) vehicle);
                }
            }
        }
		return availableMotorcycles;
	}

	private boolean isAvailable(Vehicle vehicle, LocalDate begin, LocalDate end) {
		return vehicle.isFree(begin, end);
	}

}
