package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerConstructorMethodTest {
	private static final String SELLER_NIF = "111111111";
	private static final String SELLER_NAME = "Joao Antonio";
	private static final String SELLER_ADDRESS = "Rua dos Vendedores";


	@Before
	public void setUp() {
	}

	@Test
	public void success() {
		Seller seller = new Seller(SELLER_NIF, SELLER_NAME,SELLER_ADDRESS);

		Assert.assertEquals(SELLER_NIF, seller.getNif());
		Assert.assertEquals(SELLER_NAME, seller.getName());
		Assert.assertEquals(SELLER_ADDRESS, seller.getAddress());
		
		Assert.assertTrue(seller.getNif().length() == TaxException.NIF_SIZE);
	}

	@Test(expected = TaxException.class)
	public void nullSellerNif() {
		new Seller(null, SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptySellerNif() {
		new Seller("    ", SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullSellerName() {
		new Seller(SELLER_NIF, null, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptySellerName() {
		new Seller(SELLER_NIF, "    ", SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void nullSellerAddress() {
		new Seller(SELLER_NIF, SELLER_NAME, null);
	}

	@Test(expected = TaxException.class)
	public void emptySellerAddress() {
		new Seller(SELLER_NIF, SELLER_NAME, "    ");
	}

	@After
	public void tearDown() {
	}


}
