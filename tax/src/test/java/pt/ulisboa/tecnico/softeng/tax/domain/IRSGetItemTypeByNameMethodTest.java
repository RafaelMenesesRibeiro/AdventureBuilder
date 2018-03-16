package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetItemTypeByNameMethodTest {
	private Set<ItemType> itemTypes;
	private ItemType bolacha;
	private ItemType brinquedo;
	private ItemType cerveja;

	@Before
	public void setUp() {
		this.bolacha = new ItemType ("Bolacha", 10);
		this.brinquedo = new ItemType ("Brinquedo", 40);
		this.cerveja = new ItemType ("Cerveja", 10);
		this.itemTypes = IRS.getItemTypes();
	}

	@Test
	public void success() {
		Assert.assertEquals(this.brinquedo, IRS.getItemTypeByName("Brinquedo"));
		Assert.assertEquals(this.bolacha, IRS.getItemTypeByName("Bolacha"));
		Assert.assertEquals(this.cerveja, IRS.getItemTypeByName("Cerveja"));
		Assert.assertNull(IRS.getItemTypeByName("Videojogo"));
	}

	@Test(expected = TaxException.class)
	public void nullName() {
		IRS.getItemTypeByName(null);
	}

	@Test(expected = TaxException.class)
	public void emptyName() {
		IRS.getItemTypeByName("");
	}

	@After
	public void tearDown() {
		this.bolacha.clear();
		this.brinquedo.clear();
		this.cerveja.clear();
		this.itemTypes.clear();	
		IRS.clear();
	}


}
