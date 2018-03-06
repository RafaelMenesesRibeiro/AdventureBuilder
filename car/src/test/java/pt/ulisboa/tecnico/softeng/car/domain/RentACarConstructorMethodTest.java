package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarConstructorMethodTest {
    private String name;
    private String name2;
    
    @Before
    public void setUp() {
        this.name = "RentACar";
        this.name2 = "RentACar2";
    }

    @Test
    public void success() {
        RentACar renter = new RentACar(this.name);
        Assert.assertEquals(this.name, renter.getName());
        RentACar renter2 = new RentACar(this.name2);
        //Assert.assertTrue(renter.getCode() < renter2.getCode());
        Assert.assertNotNull(renter.getVehicleList());
        Assert.assertTrue(renter.getVehicleList().isEmpty());
        Assert.assertTrue(RentACar.rentingCompanies.contains(renter));
    }
    
    @Test(expected = CarException.class)
    public void notEmptyNameArgument() {
    	new RentACar(null);
    }   
    
}