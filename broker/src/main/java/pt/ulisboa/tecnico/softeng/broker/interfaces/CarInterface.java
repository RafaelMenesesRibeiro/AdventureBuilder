package pt.ulisboa.tecnico.softeng.broker.interfaces;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public class CarInterface {

	public static String reserveCar(LocalDate begin, LocalDate end, String NIF, String IBAN) {
		return RentACar.rent(begin, end, "br112233", NIF, IBAN); //FIX-ME: GET THE DRIVING LICENSE FROM SOMEWHERE.
	}

	public static String cancelRenting(String reference) {
		Vehicle vehicle = RentACar.getRenting(reference).getVehicle();
		return vehicle.cancelRenting(reference);
	}

	public static Renting getRentingData(String reference) {
		return RentACar.getRenting(reference);
	}

}