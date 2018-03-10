package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetItemTypeByNameMethodTest {
	private List<ItemType> itemTypes;
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

	
		int index = 0;
		for (ItemType type: this.itemTypes) {
			if (type.getName().equals("Brinquedo")) {
				Assert.assertEquals(type, IRS.getItemTypeByName("Brinquedo"));
			}
		}
	}

	@Test(expected = TaxException.class)
	public void nullName() {
		IRS.getItemTypeByName(null);
	}

	@Test(expected = TaxException.class)
	public void emptyName() {
		IRS.getItemTypeByName("");
	}

	@Test(expected = TaxException.class)
	public void nameNotExists() {
		IRS.getItemTypeByName("Videojogo");
	}


	@After
	public void tearDown() {
	}


}
