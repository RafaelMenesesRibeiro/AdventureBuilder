package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleIsFreeMethodTest {
	
	private String plate;
	private Vehicle vehicle;
	private RentACar renter;
	private LocalDate unavailableBegin;
	private LocalDate unavailableEnd;
	private String drivingLicense;
	
	@Before
	public void setUp() {
		this.plate = "AA-BB-CC";
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle(this.plate, 0, this.renter);
		this.unavailableBegin = LocalDate.now();
		this.unavailableEnd = this.unavailableBegin.plusDays(7);
		this.drivingLicense = "VC12345";
		this.vehicle.rent(this.drivingLicense, this.unavailableBegin, this.unavailableEnd);
    }

	@Test
	public void freeBefore() {
		LocalDate begin = this.unavailableBegin.minusDays(7);
		LocalDate end = begin.plusDays(6);
		Assert.assertTrue(this.vehicle.isFree(begin, end));
	}

	@Test
	public void freeAfter() {
		LocalDate begin = this.unavailableEnd.plusDays(1);
		LocalDate end = begin.plusDays(6);
		Assert.assertTrue(this.vehicle.isFree(begin, end));
	}

	@Test
	public void sameDays() {
		Assert.assertFalse(this.vehicle.isFree(this.unavailableBegin, this.unavailableEnd));
	}

	@Test
	public void subsetDates() {
		LocalDate begin = this.unavailableBegin.plusDays(1);
		LocalDate end = this.unavailableEnd.minusDays(1);
		Assert.assertFalse(this.vehicle.isFree(begin, end));
	}

	@Test
	public void supersetDates() {
		LocalDate begin = this.unavailableBegin.minusDays(1);
		LocalDate end = this.unavailableEnd.plusDays(1);
		Assert.assertFalse(this.vehicle.isFree(begin, end));
	}

	@Test
	public void leftOverlapOfDates() {
		LocalDate begin = this.unavailableBegin.minusDays(1);
		LocalDate end = this.unavailableBegin.plusDays(1);
		Assert.assertFalse(this.vehicle.isFree(begin, end));
	}

	@Test
	public void rightOverlapOfDates() {
		LocalDate begin = this.unavailableEnd.minusDays(1);
		LocalDate end = this.unavailableEnd.plusDays(1);
		Assert.assertFalse(this.vehicle.isFree(begin, end));
	}

	@Test (expected = CarException.class)
	public void swappedDates() {
		this.vehicle.isFree(this.unavailableEnd, this.unavailableBegin);
	}

	@Test (expected = CarException.class)
	public void nullBegin() {
		this.vehicle.isFree(null, this.unavailableEnd);
	}

	@Test (expected = CarException.class)
	public void nullEnd() {
		this.vehicle.isFree(this.unavailableBegin, null);
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