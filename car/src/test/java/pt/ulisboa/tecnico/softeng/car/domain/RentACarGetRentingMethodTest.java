package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;
import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingMethodTest {
	private String drivingLicense;
	private LocalDate begin;
	private LocalDate end;
	private RentACar renter;
	private Vehicle vehicle;
	private Renting renting;
	
	@Before
	public void setUp() {
		this.drivingLicense = "VC12345";
		this.begin = LocalDate.now();
		this.end = this.begin.plusDays(1);
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle("ZZ-ZZ-ZZ", 0, this.renter);
		this.renting = this.vehicle.rent(this.drivingLicense, this.begin, this.end);
	}
	
	@Test
	public void success() {
		Renting result = this.renter.getRenting(this.renting.getReference());
		Assert.assertEquals(this.renting, result);
	}
	
	@Test (expected = CarException.class)
	public void notNullReferenceArgument() {
		this.renter.getRenting(null);
	}

	@Test (expected = CarException.class)
	public void notEmptyReferenceArgument() {
		this.renter.getRenting("");
	}

	@Test (expected = CarException.class)
	public void notBlankReferenceArgument() {
		this.renter.getRenting("  ");
	}

	@Test
	public void noMatchReferenceArgument() {
		Assert.assertEquals(null, this.renter.getRenting("XPTO"));
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
