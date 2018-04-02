package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-09");
	private static final String NIF = "123456789";
	private static final String NIF2 = "123456789";
	private static final String IBAN = "IBAN";
	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF2);
		this.car = new Car(PLATE_CAR, 10, rentACar);
	}

	@Test(expected = CarException.class)
	public void doubleRent() {
		car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
		car.rent(DRIVING_LICENSE, date1, date2, NIF, IBAN);
	}

	@Test(expected = CarException.class)
	public void beginIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar);
		car.rent(DRIVING_LICENSE, null, date2, NIF, IBAN);
	}

	@Test(expected = CarException.class)
	public void endIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF);
		Vehicle car = new Car(PLATE_CAR, 10, rentACar);
		car.rent(DRIVING_LICENSE, date1, null, NIF, IBAN);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
