package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerConstructorMethodTest {
	private static final String SELLER_NIF = "111111111";
	private static final String SELLER_NAME = "DELTA";
	private static final String SELLER_ADDRESS = "Rua dos Vendedores";
	private Seller seller;

	@Before
	public void setUp() {
		this.seller = new Seller(SELLER_NIF, SELLER_NAME,SELLER_ADDRESS);
	}

	@Test
	public void success() {
		Assert.assertEquals(SELLER_NIF, seller.getNIF());
		Assert.assertEquals(SELLER_NAME, seller.getName());
		Assert.assertEquals(SELLER_ADDRESS, seller.getAddress());
		
		Assert.assertTrue(seller.getNIF().length() == TaxException.NIF_SIZE);
	}

	@Test
	public void successCreationWithSameName() {
		Seller sellerWithSameName = new Seller("111111112", "DELTA", "Rua Dos Vendedores Pobres");

		Assert.assertEquals("111111112", sellerWithSameName.getNIF());
		Assert.assertEquals(SELLER_NAME, sellerWithSameName.getName());
		Assert.assertEquals("Rua Dos Vendedores Pobres", sellerWithSameName.getAddress());
		
		Assert.assertTrue(sellerWithSameName.getNIF().length() == TaxException.NIF_SIZE);
	}
	@Test
	public void successCreationWithSameAddress() {
		Seller sellerWithSameAddress = new Seller("111111113", "GAP", SELLER_ADDRESS);

		Assert.assertEquals("111111113", sellerWithSameAddress.getNIF());
		Assert.assertEquals("GAP", sellerWithSameAddress.getName());
		Assert.assertEquals(SELLER_ADDRESS, sellerWithSameAddress.getAddress());
		
		Assert.assertTrue(sellerWithSameAddress.getNIF().length() == TaxException.NIF_SIZE);
	}


	@Test(expected = TaxException.class)
	public void existingNIF() {
		new Seller(SELLER_NIF, "SuperBock", "Rua dos Ladrões de Identidade");
	}


	@Test(expected = TaxException.class)
	public void nullSellerNIF() {
		new Seller(null, SELLER_NAME, SELLER_ADDRESS);
	}

	@Test(expected = TaxException.class)
	public void emptySellerNIF() {
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
		IRS.clear();
	}


}
