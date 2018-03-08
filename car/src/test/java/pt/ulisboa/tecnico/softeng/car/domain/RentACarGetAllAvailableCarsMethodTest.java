package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.List;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetAllAvailableCarsMethodTest {
	private LocalDate unavailableBegin;
	private LocalDate unavailableEnd;
	private RentACar renter;
	private Car car;

	@Before
	public void setUp() {
		this.unavailableBegin = LocalDate.now();
		this.unavailableEnd = this.unavailableBegin.plusDays(7);
		this.renter = new RentACar("Renter");
		this.car = new Car("ZZ-ZZ-ZZ", 0, this.renter);
		this.car.rent("VC12345", this.unavailableBegin, this.unavailableEnd);
	}

	@Test
	public void success() {
		LocalDate begin = this.unavailableEnd.plusDays(1);
		LocalDate end = begin.plusDays(7);
		List<Car> cars = renter.getAllAvailableCars(begin, end);
		Assert.assertTrue(cars.size() == 1);
		Assert.assertEquals(cars.get(0), this.car);
	}

	@Test
	public void noAvailableCars() {
		List<Car> cars = renter.getAllAvailableCars(this.unavailableBegin, this.unavailableEnd);
		Assert.assertTrue(cars.size() == 0);
	}

	@After
	public void tearDown() {
		
	}

}