package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;;

public class RentingConflictMethodTest {
	
	private String plate;
	private Vehicle vehicle;
	private RentACar renter;
	private LocalDate begin;
	private LocalDate end;
	private String drivingLicense;
	private Renting renting;
	
	@Before
	public void setUp() {
		this.plate = "AA-BB-CC";
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle(this.plate, 0, this.renter);
		this.begin = LocalDate.now();
		this.end = this.begin.plusDays(7);
		this.drivingLicense = "VC12345";
		this.renting = this.vehicle.rent(this.drivingLicense, this.begin, this.end);
	}

	@Test
	public void beforeDates() {
		LocalDate begin = this.begin.minusDays(7);
		LocalDate end = begin.plusDays(6);
		Assert.assertFalse(this.renting.conflict(begin, end));
	}
	
	@Test
	public void afterDates() {
		LocalDate begin = this.end.plusDays(1);
		LocalDate end = begin.plusDays(6);
		Assert.assertFalse(this.renting.conflict(begin, end));
	}

	@Test
	public void sameDates() {
		Assert.assertTrue(this.renting.conflict(this.begin, this.end));
	}	

	@Test
	public void subsetDates() {
		LocalDate begin = this.begin.plusDays(1);
		LocalDate end = this.end.minusDays(1);
		Assert.assertTrue(this.renting.conflict(begin, end));
	}

	@Test
	public void supersetDates() {
		LocalDate begin = this.begin.minusDays(1);
		LocalDate end = this.end.plusDays(1);
		Assert.assertTrue(this.renting.conflict(begin, end));
	}


	@Test
	public void leftOverlapOfDates() {
		LocalDate begin = this.begin.minusDays(1);
		LocalDate end = this.begin.plusDays(1);
		Assert.assertTrue(this.renting.conflict(begin, end));
	}

	@Test
	public void rightOverlapOfDates() {
		LocalDate begin = this.end.minusDays(1);
		LocalDate end = this.end.plusDays(1);
		Assert.assertTrue(this.renting.conflict(begin, end));
	}

	@Test (expected = CarException.class)
	public void swappedDates() {
		this.renting.conflict(this.end, this.begin);
	}

	@Test (expected = CarException.class)
	public void nullbegin() {
		this.renting.conflict(null, this.end);
	}

	@Test (expected = CarException.class)
	public void nullend() {
		this.renting.conflict(this.begin, null);
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