package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerConstructorMethodTest {
	private static final String BUYER_NIF = "177777777";
	private static final String BUYER_NAME = "DELTA";
	private static final String BUYER_ADDRESS = "Estrela da lapa";
	private Buyer buyer;

	@Before
	public void setUp() {
	}

	@Test
	public void success() {
		this.buyer = new Buyer(BUYER_NIF, BUYER_NAME,BUYER_ADDRESS);

		Assert.assertEquals(BUYER_NIF, buyer.getNIF());
		Assert.assertEquals(BUYER_NAME, buyer.getName());
		Assert.assertEquals(BUYER_ADDRESS, buyer.getAddress());
		
		Assert.assertTrue(this.buyer.getNIF().length() == TaxPayer.NIF_SIZE);
	}

	@Test
	public void successCreationWithSameName() {
		Buyer buyerWithSameName = new Buyer("177777778",  BUYER_NAME, "Rua Dos Compradores Pobres");

		Assert.assertEquals("177777778", buyerWithSameName.getNIF());
		Assert.assertEquals(BUYER_NAME, buyerWithSameName.getName());
		Assert.assertEquals("Rua Dos Compradores Pobres", buyerWithSameName.getAddress());
		
		Assert.assertTrue(buyerWithSameName.getNIF().length() == TaxException.NIF_SIZE);
	}

	@Test
	public void successCreationWithSameAddress() {
		Buyer buyerWithSameAddress = new Buyer("177777779", "Francisco Santos", BUYER_ADDRESS);

		Assert.assertEquals("177777779", buyerWithSameAddress.getNIF());
		Assert.assertEquals("Francisco Santos", buyerWithSameAddress.getName());
		Assert.assertEquals(BUYER_ADDRESS, buyerWithSameAddress.getAddress());
		
		Assert.assertTrue(buyerWithSameAddress.getNIF().length() == TaxException.NIF_SIZE);
	}

	
	@Test(expected = TaxException.class)
	public void existingNIF() {
		new Buyer(BUYER_NIF, "Luis Almeida", "Rua dos Ladrões de Cebolas");
		new Buyer(BUYER_NIF, "Luis Putin", "Rua dos Ladrões de Identidade");
	}
	

	@Test(expected = TaxException.class)
	public void nullBuyerNIF() {
		new Buyer(null, BUYER_NAME, BUYER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptyBuyerNIF() {
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
		IRS.clear();
	}


}
