package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorMethodTest {
    private String name;
    private RentACar renter;
    
    @Before
    public void setUp() {
        this.name = "RentACar";
    }

    @Test
    public void success() {
        RentACar renter = new RentACar(this.name);
        Assert.assertEquals(this.name, renter.getName());
        Assert.assertNotNull(renter.getVehicleList());
        Assert.assertNotNull(renter.getCode());
        Assert.assertTrue(renter.getVehicleList().isEmpty());
        Assert.assertTrue(RentACar.rentingCompanies.contains(renter));
        
        this.renter = renter;
    }
    
    @Test(expected = CarException.class)
    public void notNullNameArgument() {
    	new RentACar(null);
    }
    
    @Test(expected = CarException.class)
    public void notEmptyNameArgument() {
    	new RentACar(new String());
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