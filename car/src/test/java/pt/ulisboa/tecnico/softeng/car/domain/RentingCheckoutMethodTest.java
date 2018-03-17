package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingCheckoutMethodTest {
	
	private String plate;
	private int vehicleKilometers;
	private Vehicle vehicle;
	private RentACar renter;
	private LocalDate begin;
	private LocalDate end;
	private String drivingLicense;
	private Renting renting;
	
	@Before
	public void setUp() {
		this.plate = "AA-BB-CC";
		this.vehicleKilometers = 0;
		this.renter = new RentACar("Renter");
		this.vehicle = new Vehicle(this.plate, this.vehicleKilometers, this.renter);
		this.begin = LocalDate.now();
		this.end = this.begin.plusDays(7);
		this.drivingLicense = "VC12345";
		this.renting = this.vehicle.rent(this.drivingLicense, this.begin, this.end);
	}

	@Test
	public void success() {
		int initialRentKilometers = this.renting.getKilometers();
		int addedKilometers = 10;
		this.renting.checkout(addedKilometers);
		Assert.assertEquals(this.renting.getKilometers(), initialRentKilometers + addedKilometers);
		Assert.assertEquals(this.vehicle.getKilometers(), this.vehicleKilometers + addedKilometers);
	}

	@Test
	public void noKilometers() {
		int initialRentKilometers = this.renting.getKilometers();
		int addedKilometers = 0;
		this.renting.checkout(addedKilometers);
		Assert.assertEquals(this.renting.getKilometers(), initialRentKilometers);
		Assert.assertEquals(this.vehicle.getKilometers(), this.vehicleKilometers);
	}

	@Test (expected = CarException.class)
	public void negativeKilometers() {
		this.renting.checkout(-1);
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