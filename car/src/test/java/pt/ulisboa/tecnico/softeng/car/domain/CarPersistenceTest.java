package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class CarPersistenceTest {
	// TODO

	@Test
	public void success() {
		// TODO
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		// TODO
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		// TODO
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		// TODO
		}
	}
