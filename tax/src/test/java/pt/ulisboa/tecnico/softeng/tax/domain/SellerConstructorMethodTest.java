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
	private Seller seller;

	@Before
	public void setUp() {
	}

	@Test
	public void success() {
		this.seller = new Seller(SELLER_NIF, SELLER_NAME,SELLER_ADDRESS);

		Assert.assertEquals(SELLER_NIF, seller.getNif());
		Assert.assertEquals(SELLER_NAME, seller.getName());
		Assert.assertEquals(SELLER_ADDRESS, seller.getAddress());
		
		Assert.assertTrue(seller.getNif().length() == TaxException.NIF_SIZE);
	}

	@Test
	public void successCreationWithSameName() {
		Seller sellerWithSameName = new Seller("111111112", "Joao Antonio", "Rua Dos Vendedores Pobres");

		Assert.assertEquals("111111112", sellerWithSameName.getNif());
		Assert.assertEquals(SELLER_NAME, sellerWithSameName.getName());
		Assert.assertEquals("Rua Dos Vendedores Pobres", sellerWithSameName.getAddress());
		
		Assert.assertTrue(sellerWithSameName.getNif().length() == TaxException.NIF_SIZE);
	}
	@Test
	public void successCreationWithSameAddress() {
		Seller sellerWithSameAddress = new Seller("111111113", "Francisco Santos", SELLER_ADDRESS);

		Assert.assertEquals("111111113", sellerWithSameAddress.getNif());
		Assert.assertEquals("Francisco Santos", sellerWithSameAddress.getName());
		Assert.assertEquals(SELLER_ADDRESS, sellerWithSameAddress.getAddress());
		
		Assert.assertTrue(sellerWithSameAddress.getNif().length() == TaxException.NIF_SIZE);
	}


	@Test(expected = TaxException.class)
	public void existingNif() {
		new Seller(SELLER_NIF, "Maria dos Anjos", "Rua dos Ladrões de Identidade");
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
