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
	private static final double CAR_PRICE = 10;
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-09");
	private Car car;

	private static final String NIF_SELLER_1 = "987654321";
	private static final String IBAN_SELLER_1 = "IBAN1";

	private static final String NIF_SELLER_2 = "987654322";
	private static final String IBAN_SELLER_2 = "IBAN2";

	private static final String NIF_SELLER_3 = "987654322";
	private static final String IBAN_SELLER_3 = "IBAN3";

	private static final String NIF_BUYER_1 = "987654323";
	private static final String IBAN_BUYER_1 = "IBAN4";

	private static final String NIF_BUYER_2 = "387654324";
	private static final String IBAN_BUYER_2 = "IBAN5";

	private static final String NIF_BUYER_3 = "387654325";
	private static final String IBAN_BUYER_3 = "IBAN6";

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER_1, IBAN_SELLER_1);
		this.car = new Car(PLATE_CAR, 10, CAR_PRICE, rentACar);
	}

	@Test(expected = CarException.class)
	public void doubleRent() {
		car.rent(DRIVING_LICENSE, date1, date2, NIF_BUYER_1, IBAN_BUYER_1);
		car.rent(DRIVING_LICENSE, date1, date2, NIF_BUYER_2, IBAN_BUYER_2);
	}

	@Test(expected = CarException.class)
	public void beginIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER_2, IBAN_SELLER_2);
		Vehicle car = new Car(PLATE_CAR, 10, CAR_PRICE, rentACar);
		car.rent(DRIVING_LICENSE, null, date2, NIF_BUYER_3, IBAN_BUYER_3);
	}

	@Test(expected = CarException.class)
	public void endIsNull() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER_3, IBAN_SELLER_3);
		Vehicle car = new Car(PLATE_CAR, 10, CAR_PRICE, rentACar);
		car.rent(DRIVING_LICENSE, date1, null, NIF_BUYER_3, IBAN_BUYER_3);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
