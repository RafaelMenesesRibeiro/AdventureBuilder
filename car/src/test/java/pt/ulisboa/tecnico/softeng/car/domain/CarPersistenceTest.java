package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class CarPersistenceTest {

	@Test 
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic (mode = TxMode.WRITE)
	public void atomicProcess() {}

	@Atomic (mode = TxMode.READ)
	public void atomicAssert() {}

	@After
	@Atomic (mode = TxMode.WRITE)
	public void tearDown() {}
}