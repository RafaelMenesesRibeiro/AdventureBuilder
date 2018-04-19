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

public class CarPersistenceTest {

	private final String RENT_NAME = "hertz";
	private final String RENT_NIF = "23232301";
	private final String RENT_IBAN = "000001";

	@Test 
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic (mode = TxMode.WRITE)
	public void atomicProcess() {
		RentACar rental = new RentACar(RENT_NAME, RENT_NIF, RENT_IBAN);

	}

	@Atomic (mode = TxMode.READ)
	public void atomicAssert() {
		List<RentACar> rentals = new ArrayList<>(FenixFramework.getDomainRoot().getRentACarSet());
		assertEquals(1, rentals.size());
		RentACar rental = rentals.get(0);
		assertEquals(RENT_NAME, rental.getName());
		assertEquals(RENT_NIF, rental.getNif());
		assertEquals(RENT_IBAN, rental.getIban());
	}

	@After
	@Atomic (mode = TxMode.WRITE)
	public void tearDown() {
		for (RentACar rental : FenixFramework.getDomainRoot().getRentACarSet()) {
			rental.delete();
		}
	}
}