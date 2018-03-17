package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetRentingDataMethodTest {
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
		RentingData rentingData = this.renter.getRentingData(this.renting.getReference());
		Assert.assertEquals(rentingData.getReference(), this.renting.getReference());
		Assert.assertEquals(rentingData.getPlate(), this.vehicle.getPlate());
		Assert.assertEquals(rentingData.getDrivingLicense(), this.drivingLicense);
		Assert.assertEquals(rentingData.getRentACarCode(), this.renter.getCode());
		Assert.assertEquals(rentingData.getBeginDate(), this.begin);
		Assert.assertEquals(rentingData.getEndDate(), this.end);
	}

	@Test (expected = CarException.class)
	public void noFoundReference() {
		this.renter.getRentingData("XPTO");
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