package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorTest {
	private static final String NAME = "eartz";
	private static final String NIF = "123456789";

	@Test
	public void success() {
		RentACar rentACar = new RentACar(NAME, NIF);
		assertEquals(NAME, rentACar.getName());
	}

	@Test(expected = CarException.class)
	public void nullName() {
		new RentACar(null, NIF);
	}

	@Test(expected = CarException.class)
	public void emptyName() {
		new RentACar("", NIF);
	}

	@Test(expected = CarException.class)
	public void nullNif() {
		new RentACar(NAME, null);
	}

	@Test(expected = CarException.class)
	public void emptyNif() {
		new RentACar(NAME, "");
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
