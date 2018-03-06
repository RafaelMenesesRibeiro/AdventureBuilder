package pt.ulisboa.tecnico.softeng.car.domain;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentACarGetRentingMethodTest {
	
	private String drivingLicense;
	private LocalDateTime begin;
	private LocalDateTime end;
	private RentACar renter;
	private Vehicle vehicle;
	
	@Before
	public void setUp() {
		this.drivingLicense = "VC12345";
		this.begin = LocalDateTime.now();
		this.end = this.begin.plusDays(1);
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle("ZZ-ZZ-ZZ", this.renter);
	}
	
	@Test
	public void success() {
		this.vehicle.rent(this.drivingLicense, this.begin, this.end);
		Renting renting = this.vehicle.getRent(this.begin, this.end);
		Renting result = this.renter.getRenting(renting.getReferece());
		Assert.assertEquals(renting, result);
	}
	
	//@Test (expected = )
	
	@After
	public void tearDown() {
		
	}
}
