package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleRentMethodTest {
	
	private String plate;
	private Vehicle vehicle;
	private RentACar renter;
	private LocalDate begin;
	private LocalDate end;
	private String drivingLicense;
	
	@Before
	public void setUp() {
		this.plate = "AA-BB-CC";
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle(this.plate, 0, this.renter);
		this.begin = LocalDate.now();
		this.end = this.begin.plusDays(7);
		this.drivingLicense = "VC12345";
	}

	@Test
	public void success() {
		Renting renting = this.vehicle.rent(this.drivingLicense, this.begin, this.end);
		Assert.assertEquals(renting.getDrivingLicense(), this.drivingLicense);
		Assert.assertEquals(renting.getBeginDate(), this.begin);
		Assert.assertEquals(renting.getEndDate(), this.end);
		Assert.assertEquals(renting.getVehicle(), this.vehicle);
	}

	@Test (expected=CarException.class)
	public void nullDrivingLicense() {
		this.vehicle.rent(null, this.begin, this.end);
	}

	@Test (expected=CarException.class)
	public void nullBeginDate() {
		this.vehicle.rent(this.drivingLicense, null, this.end);
	}

	@Test (expected=CarException.class)
	public void nullEndDate() {
		this.vehicle.rent(this.drivingLicense, this.begin, null);
	}

	@Test (expected=CarException.class)
	public void swappedDates() {
		this.vehicle.rent(this.drivingLicense, this.end,this.begin);
	}

	@Test (expected=CarException.class)
	public void notFreeVehicle() {
		this.vehicle.rent(this.drivingLicense, this.begin, this.end);
		this.vehicle.rent(this.drivingLicense, this.begin.plusDays(1), this.end);		
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