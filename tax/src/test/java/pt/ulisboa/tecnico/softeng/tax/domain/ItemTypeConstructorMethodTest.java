package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorMethodTest {
	private static final int TAX = 23;
	private static final String ITEM_TYPE = "Videojogo";
	private ItemType itemType;


	@Before
	public void setUp() {

	}

	@Test
	public void success() {
		this.itemType = new ItemType(ITEM_TYPE, TAX);

		Assert.assertTrue(this.itemType.getTax() >= 0);

		Assert.assertEquals(ITEM_TYPE, this.itemType.getName());
		Assert.assertEquals(TAX, this.itemType.getTax(), 0.0f);
		Assert.assertEquals(0, this.itemType.getNumberOfInvoices());

		Assert.assertNotNull(IRS.getItemTypes());
		Assert.assertNotNull(this.itemType.getInvoices());
	}

	@Test(expected = TaxException.class)
	public void existingItemType() {
		new ItemType(ITEM_TYPE, 24);
		new ItemType(ITEM_TYPE, 12);
	}

	@Test(expected = TaxException.class)
	public void nullItemType() {
		new ItemType(null, TAX);
	}

	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new ItemType("    ", TAX);
	}

	@Test(expected = TaxException.class)
	public void negativeTax() {
		new ItemType(ITEM_TYPE, -1);
	}

	@After
	public void tearDown() {
		IRS.clear();
	}


}
