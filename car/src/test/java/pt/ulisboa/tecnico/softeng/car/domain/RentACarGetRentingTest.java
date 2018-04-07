package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RentACarGetRentingTest {
	private static final String NAME = "eartz";
	private static final String PLATE_CAR = "aa-00-11";
	private static final String DRIVING_LICENSE = "br123";
	private static final double CAR_PRICE = 10;
	private static final LocalDate date1 = LocalDate.parse("2018-01-06");
	private static final LocalDate date2 = LocalDate.parse("2018-01-07");
	private static final LocalDate date3 = LocalDate.parse("2018-01-08");
	private static final LocalDate date4 = LocalDate.parse("2018-01-09");
	
	private static final String NIF_SELLER = "987654321";
	private static final String IBAN_SELLER = "IBAN1";

	private static final String NIF_BUYER = "987654323";
	private static final String IBAN_BUYER = "IBAN2";
	
	private Renting renting;

	@Before
	public void setUp() {
		RentACar rentACar1 = new RentACar(NAME, NIF_SELLER, IBAN_SELLER);
		Vehicle car1 = new Car(PLATE_CAR, 10, CAR_PRICE, rentACar1);
		this.renting = car1.rent(DRIVING_LICENSE, date1, date2, NIF_BUYER, IBAN_BUYER);
		car1.rent(DRIVING_LICENSE, date3, date4, NIF_BUYER, IBAN_BUYER);
	}

	@Test
	public void getRenting() {
		assertEquals(this.renting, RentACar.getRenting(this.renting.getReference()));
	}

	public void nonExistent() {
		assertNull(RentACar.getRenting("a"));
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}
}
