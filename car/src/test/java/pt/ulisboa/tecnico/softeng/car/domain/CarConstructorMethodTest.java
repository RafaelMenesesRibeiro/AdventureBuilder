package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class CarConstructorMethodTest {

	private int NO_KILOMETERS;
	private int KILOMETERS;
	private String COMPANY;
	private String PLATE;

	private RentACar renter;

	@Before
	public void setUp() {
		this.NO_KILOMETERS = 0;
		this.KILOMETERS = 50;
		this.COMPANY = "Renter";
		this.PLATE = "ZZ-ZZ-ZZ";
		this.renter = new RentACar(COMPANY);
    }

	@Test
	public void success() {
		Car car = new Car(PLATE, NO_KILOMETERS, this.renter);
		assertEquals(PLATE, car.getPlate());
		assertEquals(0, car.getKilometers());
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

	@Test(expected = CarException.class)
	public void wrongFormatPlate() {
		new Car("abcdef", NO_KILOMETERS, this.renter);
	}

	@Test(expected = CarException.class)
	public void usedPlate() {
		new Car(PLATE, NO_KILOMETERS, this.renter);
		new Car(PLATE, NO_KILOMETERS, this.renter);
	}

	@Test
	public void moreThanZeroKms() {
		Car car = new Car(PLATE, KILOMETERS, this.renter);
		assertEquals(KILOMETERS, car.getKilometers());
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