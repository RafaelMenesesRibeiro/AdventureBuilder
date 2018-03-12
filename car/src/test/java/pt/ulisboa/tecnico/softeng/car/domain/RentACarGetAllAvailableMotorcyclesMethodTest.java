package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;
import java.util.List;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetAllAvailableMotorcyclesMethodTest {
	private LocalDate unavailableBegin;
	private LocalDate unavailableEnd;
	private RentACar renter;
	private Motorcycle motorcycle;

	@Before
	public void setUp() {
		this.unavailableBegin = LocalDate.now();
		this.unavailableEnd = this.unavailableBegin.plusDays(7);
		this.renter = new RentACar("Renter");
		this.motorcycle = new Motorcycle("ZZ-ZZ-ZZ", 0, this.renter);
		new Car("AA-AA-AA", 0, this.renter);
		this.motorcycle.rent("VC12345", this.unavailableBegin, this.unavailableEnd);
	}

	@Test
	public void success() {
		LocalDate begin = this.unavailableEnd.plusDays(1);
		LocalDate end = begin.plusDays(7);
		List<Motorcycle> motorcycles = renter.getAllAvailableMotorcycles(begin, end);
		Assert.assertTrue(motorcycles.size() == 1);
		Assert.assertEquals(motorcycles.get(0), this.motorcycle);
	}

	@Test
	public void sameDatesOfRenting() {
		List<Motorcycle> motorcycles = renter.getAllAvailableMotorcycles(this.unavailableBegin, this.unavailableEnd);
		Assert.assertTrue(motorcycles.size() == 0);
	}

	@Test
	public void subsetDates() {
		LocalDate begin = this.unavailableBegin.plusDays(1);
		LocalDate end = this.unavailableEnd.minusDays(1);
		List<Motorcycle> motorcycles = renter.getAllAvailableMotorcycles(begin, end);
		Assert.assertTrue(motorcycles.size() == 0);
	}
	
	@Test
	public void leftOverlapOfDates() {
		LocalDate begin = this.unavailableBegin.minusDays(1);
		LocalDate end = this.unavailableBegin.plusDays(1);
		List<Motorcycle> motorcycles = renter.getAllAvailableMotorcycles(begin, end);
		Assert.assertTrue(motorcycles.size() == 0);
	}

	@Test
	public void rightOverlapOfDates() {
		LocalDate begin = this.unavailableEnd.minusDays(1);
		LocalDate end = this.unavailableEnd.plusDays(1);
		List<Motorcycle> motorcycles = renter.getAllAvailableMotorcycles(begin, end);
		Assert.assertTrue(motorcycles.size() == 0);
	}

	@Test (expected = CarException.class)
	public void swappedDates() {
		renter.getAllAvailableMotorcycles(this.unavailableEnd, this.unavailableBegin);
	}

	@Ignore
	@Test (expected = CarException.class)
	public void nullBegin() {
		renter.getAllAvailableMotorcycles(null, this.unavailableEnd);
	}

	@Ignore
	@Test (expected = CarException.class)
	public void nullEnd() {
		renter.getAllAvailableMotorcycles(this.unavailableBegin, null);
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