package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleConstructorTest {
	private static final String PLATE_CAR = "22-33-HZ";
	private static final String PLATE_MOTORCYCLE = "44-33-HZ";
	private static final String RENT_A_CAR_NAME = "Eartz";
	private static final double STANDART_CAR_PRICE = 10.0;
	private static final double STANDART_MOTORCYCLE_PRICE = 5.0;

	private static final String NIF_SELLER_1 = "987654321";
	private static final String IBAN_SELLER_1 = "IBAN1";

	private static final String NIF_SELLER_2 = "987654322";
	private static final String IBAN_SELLER_2 = "IBAN2";

	private RentACar rentACar;

	@Before
	public void setUp() {
		this.rentACar = new RentACar(RENT_A_CAR_NAME, NIF_SELLER_1, IBAN_SELLER_1);
	}

	@Test
	public void success() {
		Vehicle car = new Car(PLATE_CAR, 10, STANDART_CAR_PRICE, this.rentACar);
		Vehicle motorcycle = new Motorcycle(PLATE_MOTORCYCLE, 10, STANDART_MOTORCYCLE_PRICE, this.rentACar);

		assertEquals(PLATE_CAR, car.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_CAR));
		assertEquals(PLATE_MOTORCYCLE, motorcycle.getPlate());
		assertTrue(this.rentACar.hasVehicle(PLATE_MOTORCYCLE));
	}


	@Test(expected = CarException.class)
	public void emptyLicensePlate() {
		new Car("", 10, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void nullLicensePlate() {
		new Car(null, 10, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate() {
		new Car("AA-XX-a", 10, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void invalidLicensePlate2() {
		new Car("AA-XX-aaa", 10, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlate() {
		new Car(PLATE_CAR, 0, STANDART_CAR_PRICE, this.rentACar);
		new Car(PLATE_CAR, 0, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void duplicatedPlateDifferentRentACar() {
		new Car(PLATE_CAR, 0, STANDART_CAR_PRICE, rentACar);
		RentACar rentACar2 = new RentACar(RENT_A_CAR_NAME + "2", NIF_SELLER_2, IBAN_SELLER_2);
		new Car(PLATE_CAR, 2, STANDART_CAR_PRICE, rentACar2);
	}

	@Test (expected = CarException.class)
	public void negatePrice() {
		new Car(PLATE_CAR, 10, -1, rentACar);
	}


	@Test (expected = CarException.class)
	public void priceZero() {
		new Car(PLATE_CAR, 10, 0, rentACar);
	}

	@Test(expected = CarException.class)
	public void negativeKilometers() {
		new Car(PLATE_CAR, -1, STANDART_CAR_PRICE, this.rentACar);
	}

	@Test(expected = CarException.class)
	public void noRentACar() {
		new Car(PLATE_CAR, 0, STANDART_CAR_PRICE, null);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
		Vehicle.plates.clear();
	}

}
