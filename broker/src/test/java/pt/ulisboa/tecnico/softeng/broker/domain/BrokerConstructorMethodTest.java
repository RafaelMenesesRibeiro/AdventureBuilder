package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerConstructorMethodTest {

	@Test
	public void success() {
		Broker broker = new Broker("BR01", "WeExplore", "123456789", "987654321");

		Assert.assertEquals("BR01", broker.getCode());
		Assert.assertEquals("WeExplore", broker.getName());
		Assert.assertEquals(0, broker.getNumberOfAdventures());
		Assert.assertTrue(Broker.brokers.contains(broker));
		Assert.assertEquals("123456789", broker.getnifbuyer());
		Assert.assertEquals("987654321", broker.getnifseller());

	}

	@Test
	public void nullCode() {
		try {
			new Broker(null, "WeExplore", "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new Broker("", "WeExplore", "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new Broker("  ", "WeExplore", "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueCode() {
		Broker broker = new Broker("BR01", "WeExplore", "123456789", "987654321");

		try {
			new Broker("BR01", "WeExplore", "123456788", "987654322");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void nullName() {
		try {
			new Broker("BR01", null, "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyName() {
		try {
			new Broker("BR01", "", "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankName() {
		try {
			new Broker("BR01", "    ", "123456789", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueNIFBuyer() {
		Broker broker = new Broker("BR01", "WeExplore", "123456789", "987654321");

		try {
			new Broker("BR02", "WeExploree", "123456789", "987654322");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void uniqueNIFSeller() {
		Broker broker = new Broker("BR01", "WeExplore", "123456789", "987654321");

		try {
			new Broker("BR02", "WeExploree", "123456788", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void nullNIFBuyer() {
		try {
			new Broker("BR01", "WeExplore", null, "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyNIFBuyer() {
		try {
			new Broker("BR01", "WeExplore", "", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void wrongNIFBuyer() {
		try {
			new Broker("BR01", "WeExplore", "12345678", "987654321");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}


	@Test
	public void nullNIFSeller() {
		try {
			new Broker("BR01", "WeExplore", "123456789", null);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyNIFSeller() {
		try {
			new Broker("BR01", "WeExplore", "123456789", "");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void wrongNIFSeller() {
		try {
			new Broker("BR01", "WeExplore", "123456789", "98765432");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void sameNIF() {
		try {
			new Broker("BR01", "WeExplore", "123456789", "123456789");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}
