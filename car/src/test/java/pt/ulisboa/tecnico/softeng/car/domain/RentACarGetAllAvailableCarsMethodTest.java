package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;
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
		new Motorcycle("AA-AA-AA", 0, this.renter);
		this.car.rent("VC12345", this.unavailableBegin, this.unavailableEnd);
	}

	@Test
	public void success() {
		LocalDate begin = this.unavailableEnd.plusDays(1);
		LocalDate end = this.unavailableEnd.plusDays(7);
		List<Car> cars = this.renter.getAllAvailableCars(begin, end);

		Assert.assertTrue(cars.size() == 1);
		Assert.assertEquals(cars.get(0), this.car);
	}

	@Test
	public void sameDatesOfRenting() {
		List<Car> cars = this.renter.getAllAvailableCars(this.unavailableBegin, this.unavailableEnd);
		Assert.assertTrue(cars.size() == 0);
	}

	@Test
	public void subsetDates() {
		LocalDate begin = this.unavailableBegin.plusDays(1);
		LocalDate end = this.unavailableEnd.minusDays(1);
		List<Car> cars = this.renter.getAllAvailableCars(begin, end);
		Assert.assertTrue(cars.size() == 0);
	}
	
	@Test
	public void leftOverlapOfDates() {
		LocalDate begin = this.unavailableBegin.minusDays(1);
		LocalDate end = this.unavailableBegin.plusDays(1);
		List<Car> cars = this.renter.getAllAvailableCars(begin, end);
		Assert.assertTrue(cars.size() == 0);
	}

	@Test
	public void rightOverlapOfDates() {
		LocalDate begin = this.unavailableEnd.minusDays(1);
		LocalDate end = this.unavailableEnd.plusDays(1);
		List<Car> cars = this.renter.getAllAvailableCars(begin, end);
		Assert.assertTrue(cars.size() == 0);
	}

	@Test (expected = CarException.class)
	public void swappedDates() {
		renter.getAllAvailableCars(this.unavailableEnd, this.unavailableBegin);
	}
	@Test (expected = CarException.class)
	public void nullBegin() {
		renter.getAllAvailableCars(null, this.unavailableEnd);
	}

	@Test (expected = CarException.class)
	public void nullEnd() {
		renter.getAllAvailableCars(this.unavailableBegin, null);
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