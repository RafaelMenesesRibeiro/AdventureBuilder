package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class ClientConstructorMethodTest {

	@Test
	public void success() {
		Client client = new Client("123", 20, "123456789", "br123");

		Assert.assertEquals("123", client.getIBAN());
		Assert.assertEquals(20, client.getAge());
		Assert.assertEquals("123456789", client.getNIF());
		Assert.assertEquals("br123", client.getDrivingLicense());
	}


	@Test
	public void emptyIBAN() {
		try {
			new Client("", 20, "123456789", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void nullIBAN() {
		try {
			new Client(null, 20, "123456789", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void nullNIF() {
		try {
			new Client("123", 20, null, "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void uniqueNIF() {
		new Client("123", 20, "123456789", "br123");
		try {
			new Client("123", 20, "123456789", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Client.clients.size());
		}
	}

	@Test
	public void emptyNIF() {
		try {
			new Client("123", 20, "", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void wrongNIF() {
		try {
			new Client("123", 20, "12345678", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	public void nullDrivingLicense() {
		try {
			new Client("123", 20, "123456789", null);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void uniqueDrivingLicense() {
		new Client("123", 20, "123456789", "br123");
		try {
			new Client("123", 20, "123456789", "br123");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Client.clients.size());
		}
	}

	@Test
	public void emptyDrivingLicense() {
		try {
			new Client("123", 20, "123456789", "");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}

	@Test
	public void wrongDrivingLicense() {
		try {
			new Client("123", 20, "123456789", "****");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Client.clients.size());
		}
	}


	@After
	public void tearDown() {
		Client.clients.clear();
	}

}