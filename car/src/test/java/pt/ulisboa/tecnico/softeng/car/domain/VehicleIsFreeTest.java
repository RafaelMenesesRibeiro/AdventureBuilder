package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VehicleIsFreeTest {

	private static final String PLATE_CAR = "22-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final String DRIVING_LICENSE = "lx1423";
	private static final double CAR_PRICE = 10;
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");

	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_SELLER = "IBAN1";

	private static final String NIF_BUYER_1 = "987654321";
	private static final String IBAN_BUYER_1 = "IBAN2";

	private static final String NIF_BUYER_2 = "387654323";
	private static final String IBAN_BUYER_2 = "IBAN3";

	private Car car;

	@Before
	public void setUp() {
		RentACar rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER, IBAN_SELLER);
		this.car = new Car(PLATE_CAR, 10, CAR_PRICE, rentACar);
	}

	@Test
	public void noBookingWasMade() {
		assertTrue(car.isFree(date1, date2));
		assertTrue(car.isFree(date1, date3));
		assertTrue(car.isFree(date3, date4));
		assertTrue(car.isFree(date4, date4));
	}

	@Test
	public void bookingsWereMade() {
		car.rent(DRIVING_LICENSE, date2, date2, NIF_BUYER_1, IBAN_BUYER_1);
		car.rent(DRIVING_LICENSE, date3, date4, NIF_BUYER_2, IBAN_BUYER_2);

		assertFalse(car.isFree(date1, date2));
		assertFalse(car.isFree(date1, date3));
		assertFalse(car.isFree(date3, date4));
		assertFalse(car.isFree(date4, date4));
		assertTrue(car.isFree(date1, date1));
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
