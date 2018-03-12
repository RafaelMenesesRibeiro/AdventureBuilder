package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class CarConstructorMethodTest {

	private static final int NO_KILOMETERS = 0;
	private static final int KILOMETERS = 50;

	private static final String COMPANY = "Renter";
	private static final String PLATE = "ZZ-ZZ-ZZ";

	private RentACar renter;
	private Car car;

	@Before
	public void setUp() {
		this.renter = new RentACar(COMPANY);
		this.car = new Car(PLATE, NO_KILOMETERS, this.renter);
    }

	@Test
	public void success() {
		assertEquals(PLATE, this.car.getPlate());
		assertEquals(0, this.car.getKilometers());
	}

	@Test(expected = CarException.class)
	public void nullPlate() {
		new Car(null, NO_KILOMETERS, this.renter);
	}

	@Test(expected = CarException.class)
	public void positiveKilometers() {
		new Car(PLATE, -10, this.renter);
	}

	@Test(expected = CarException.class)
	public void nullRenter() {
		new Car(PLATE, NO_KILOMETERS, null);
	}

	@Test
	public void moreThanZeroKms() {
		Car car_b = new Car(PLATE, KILOMETERS, this.renter);
		assertEquals(KILOMETERS, car_b.getKilometers());
	}

	@After
	public void tearDown() {
		for (Iterator<RentACar> iterator = RentACar.rentingCompanies.iterator(); iterator.hasNext();) {
			RentACar renter = iterator.next();
			if (renter.equals(this.renter)) {
				iterator.remove();
			}
		}
	}
	
}