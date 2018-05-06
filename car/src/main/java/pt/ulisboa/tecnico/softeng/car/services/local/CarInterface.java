package pt.ulisboa.tecnico.softeng.car.services.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import ch.qos.logback.core.joran.conditional.ElseAction;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.Processor;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

public class CarInterface {

	@Atomic(mode = TxMode.READ)
	public static List<RentACarData> getRentACars() {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().map(r -> new RentACarData(r))
				.collect(Collectors.toList());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createRentACar(RentACarData rentaCarData) {
		new RentACar(rentaCarData.getName(), rentaCarData.getNif(), rentaCarData.getIban());
	}

	@Atomic(mode = TxMode.READ)
	public static RentACarData getRentACarDataByCode(String code) {
		RentACar rentACar = getRentACarByCode(code);

		if (rentACar != null) {
			return new RentACarData(rentACar);
		}

		return null;
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createVehicle(String rentACarCode, VehicleData vehicleData) {
		RentACar rentACar = getRentACarByCode(rentACarCode);
		if (vehicleData.getType().equals("Car"))
			new Car(vehicleData.getPlate(), 0, vehicleData.getPrice(), rentACar);
		else if (vehicleData.getType().equals("Motorcycle"))
			new Motorcycle(vehicleData.getPlate(), 0, vehicleData.getPrice(), rentACar);

	}



	@Atomic(mode = TxMode.WRITE)
	public static void createRenting(String code, String plate, RentingData rentingData) {
		Vehicle vehicle = getVehicleByPlate(code, plate);
		if (vehicle == null) {
			System.out.println("Hi, there");
			throw new CarException();
		}
		new Renting(rentingData.getDrivingLicense(), rentingData.getBegin(), rentingData.getEnd(), vehicle, rentingData.getNif(),
			rentingData.getIban());
	}


	@Atomic(mode = TxMode.WRITE)
	public static String rentVehicle(String drivingLicense, String buyerNIF,
			String buyerIBAN, LocalDate begin, LocalDate end) {

		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			Set<Vehicle> vehicles = rentACar.getAllAvailableCars(begin, end);
			List<Vehicle> vehicleList = new ArrayList<Vehicle>(vehicles);
			if (vehicleList.get(0) != null) {
				return vehicleList.get(0).rent(drivingLicense,begin, end, buyerNIF, buyerIBAN).getReference();
			}
		}
		throw new CarException();
	} 

	@Atomic(mode = TxMode.WRITE)
	public static String cancelBooking(String reference) {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			Renting renting = rentACar.getRenting(reference);
			if (renting != null) {
				return renting.cancel();
			}
		}
		throw new CarException();
	}

	@Atomic(mode = TxMode.READ)
	public static RentingData getRentingData(String reference) {
		for (RentACar rentCar : FenixFramework.getDomainRoot().getRentACarSet()) {
			for (Vehicle vehicle : rentCar.getVehicleSet()) {
				Renting renting = vehicle.getRenting(reference);
				if (renting != null) {
					return new RentingData(renting);
				}
			}
		}
		throw new CarException();
	}


	static List<Vehicle> getAvailableVehicles(int number, LocalDate begin, LocalDate end) {
		List<Vehicle> availableVehicles = new ArrayList<>();
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			availableVehicles.addAll(rentACar.getAllAvailableCars(begin, end));
			if (availableVehicles.size() >= number) {
				return availableVehicles;
			}
		}
		return availableVehicles;
	}

	private static RentACar getRentACarByCode(String code) {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().filter(r -> r.getCode().equals(code)).findFirst()
				.orElse(null);
	}

	private static Vehicle getVehicleByPlate(String code, String plate) {
		RentACar rentACar = getRentACarByCode(code);
		if (rentACar == null) {
			return null;
		}

		Vehicle vehicle = rentACar.getVehicleByPlate(plate);
		if (vehicle == null) {
			return null;
		}
		return vehicle;
	}


	@Atomic(mode = TxMode.READ)
	public static VehicleData getVehicleDataByPlate(String code, String palte) {
		Vehicle vehicle = getVehicleByPlate(code, palte);
		if (vehicle == null) {
			return null;
		}

		return new VehicleData(vehicle);
	}

	@Atomic(mode = TxMode.READ)
	public static void checkout(String reference, int kilometers) {
		for (RentACar rentACar : FenixFramework.getDomainRoot().getRentACarSet()) {
			Renting renting = rentACar.getRenting(reference);
			if (renting != null) {
				renting.checkout(kilometers);
			}
		}
		throw new CarException();
	}
}
