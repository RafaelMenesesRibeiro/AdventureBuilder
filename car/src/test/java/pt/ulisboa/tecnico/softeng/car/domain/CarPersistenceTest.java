package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

import org.joda.time.LocalDate;

public class CarPersistenceTest {

	private final String RENT_NAME = "hertz";
	private final String RENT_NIF = "23232301";
	private final String RENT_IBAN = "000001";
	
	private final String PLATE = "AA-BB-CC";
	private final int KILOMETERS = 10;
	private final double PRICE = 5;

	private static final String DRIVING_LICENSE = "lx1423";
	private static final String BUYER_NIF = "NIF";
	private static final String BUYER_IBAN = "IBAN";
	private static final LocalDate BEGIN = LocalDate.parse("2018-01-06");
	private static final LocalDate END = LocalDate.parse("2018-01-09");

	@Test 
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic (mode = TxMode.WRITE)
	public void atomicProcess() {
		RentACar rental = new RentACar(RENT_NAME, RENT_NIF, RENT_IBAN);
		Car car = new Car(PLATE, KILOMETERS, PRICE, rental);
		RentACar.rent(Car.class, DRIVING_LICENSE, BUYER_NIF, BUYER_IBAN, BEGIN, END);
	}

	@Atomic (mode = TxMode.READ)
	public void atomicAssert() {
		List<RentACar> rentals = new ArrayList<>(FenixFramework.getDomainRoot().getRentACarSet());
		assertEquals(1, rentals.size());
		RentACar rental = rentals.get(0);
		assertEquals(RENT_NAME, rental.getName());
		assertEquals(RENT_NIF, rental.getNIF());
		assertEquals(RENT_IBAN, rental.getIBAN());
		List<Vehicle> vehicles = new ArrayList<>(rental.getVehicleSet());
		assertEquals(1, vehicles.size());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(PLATE, vehicle.getPlate());
		assertEquals(KILOMETERS, vehicle.getKilometers());
		assertEquals(PRICE, vehicle.getPrice(), 0.0f);
		assertEquals(rental, vehicle.getRentACar());
		List<Renting> rentings = new ArrayList<>(vehicle.getRentingSet());
		assertEquals(1, rentings.size());
		Renting renting = rentings.get(0);
		assertEquals(DRIVING_LICENSE, renting.getDrivingLicense());
		assertEquals(BUYER_NIF, renting.getClientNIF());
		assertEquals(BUYER_IBAN, renting.getClientIBAN());
		assertEquals(BEGIN, renting.getBegin());
		assertEquals(END, renting.getEnd());
	}

	@After
	@Atomic (mode = TxMode.WRITE)
	public void tearDown() {
		for (RentACar rental : FenixFramework.getDomainRoot().getRentACarSet()) {
			rental.delete();
		}
	}
}