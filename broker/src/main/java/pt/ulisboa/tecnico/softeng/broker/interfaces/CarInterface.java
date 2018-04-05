package pt.ulisboa.tecnico.softeng.broker.interfaces;

import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class CarInterface {
	public static String reserveCar(LocalDate begin, LocalDate end, String NIF, String IBAN) {
		Set<Vehicle> availableVehicles = RentACar.getAllAvailableCars(begin, end);
		Vehicle toRent = (Vehicle) (availableVehicles.toArray())[0];
		Renting renting = toRent.rent("br112233", begin, end, NIF, IBAN); //TODO: GET THE DRIVING LICENSE FROM SOMEWHERE.
		return renting.getReference();
	}

	public static String cancelRenting(String vehicleConfirmation) {
		return "Car Cancelled.";
	}

	public static Renting getRentingData(String reference) {
		return RentACar.getRenting(reference);
	}

}
