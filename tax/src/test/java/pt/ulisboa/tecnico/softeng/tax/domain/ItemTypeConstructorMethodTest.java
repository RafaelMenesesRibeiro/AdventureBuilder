package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorMethodTest {
	private static final int TAX = 23;
	private static final String ITEM_TYPE = "Videojogo";
	


	@Before
	public void setUp() {

	}

	@Test
	public void success() {
		ItemType itemType = new ItemType(ITEM_TYPE, TAX);

		Assert.assertEquals(ITEM_TYPE, itemType.getName());
		Assert.assertEquals(TAX, itemType.getTax(), 0.0f);

		Assert.assertTrue(itemType.getNumberOfInvoices() >= 1);	
		Assert.assertTrue(IRS.getNumberOfItems() >= 1);		
	}


	@After
	public void tearDown() {
		//ItemType.invoices.clear();
	}


}
