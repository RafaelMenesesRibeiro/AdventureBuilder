package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class ClientConstructorMethodTest {

	@Test
	public void success() {
		Client client = new Client("123", 20, "123456789");

		Assert.assertEquals("123", client.getIBAN());
		Assert.assertEquals(20, client.getAge());
		Assert.assertEquals("123456789", client.getNIF());

	}


	@Test
	public void emptyIBAN() {
		try {
			new Client("", 20, "123456789");
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals("", Client.brokers.size());
		}
	}

	@Test
	public void nullIBAN() {
		try {
			new Client(null, 20, "123456789");
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals(0, Client.brokers.size());
		}
	}

	@Test
	public void nullNIF() {
		try {
			new Client("123", 20, null);
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals(0, Client.brokers.size());
		}
	}

	@Test
	public void uniqueNIF() {
		new Client("123", 20, "123456789");
		try {
			new Client("123", 20, "123456789");
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals(0, Client.brokers.size());
		}
	}

	@Test
	public void emptyNIF() {
		try {
			new Client("123", 20, "");
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals(0, Client.brokers.size());
		}
	}

	@Test
	public void wrongNIF() {
		try {
			new Client("123", 20, "12345678");
			Assert.fail();
		} catch (BrokerException be) {
			//Assert.assertEquals(0, Client.brokers.size());
		}
	}


	@After
	public void tearDown() {
		Client.clients.clear();
	}

}