package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerConstructorMethodTest {
	private static final String BUYER_NIF = "177777777";
	private static final String BUYER_NAME = "Antonio Sarmento";
	private static final String BUYER_ADDRESS = "Estrela da lapa";


	@Before
	public void setUp() {
	}

	@Test
	public void success() {
		Buyer buyer = new Buyer(BUYER_NIF, BUYER_NAME,BUYER_ADDRESS);

		Assert.assertEquals(BUYER_NIF, buyer.getNif());
		Assert.assertEquals(BUYER_NAME, buyer.getName());
		Assert.assertEquals(BUYER_ADDRESS, buyer.getAddress());
		
		Assert.assertTrue(buyer.getNif().length() == TaxPayer.NIF_SIZE);
	}

	@Test(expected = TaxException.class)
	public void nullBuyerNif() {
		new Buyer(null, BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptyBuyerNif() {
		new Buyer("    ", BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullBuyerName() {
		new Buyer(BUYER_NIF, null, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptyBuyerName() {
		new Buyer(BUYER_NIF, "    ", BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullBuyerAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, null);
	}

	@Test(expected = TaxException.class)
	public void emptyBuyerAddress() {
		new Buyer(BUYER_NIF, BUYER_NAME, "    ");
	}

	@After
	public void tearDown() {
	}


}
