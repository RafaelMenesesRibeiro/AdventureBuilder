package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.HashMap;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerToPayMethodTest{
	private Seller seller;
	private Buyer buyerA;
	private Buyer buyerB;
	private Invoice invoiceA;
	private Invoice invoiceB;
	private ItemType itemTypeA;	
	private ItemType itemTypeB;


	@Before
	public void setUp() {
		this.seller = new Seller("111111111", "GALP", "Rua Principal");
		this.buyerA = new Buyer("111111112", "Maria Inês", "Rua Secundária");
		this.buyerB = new Buyer("111111113", "Filipa Otacvia", "Rua Secundária");

		this.itemTypeA = new ItemType("Brinquedos", 10); 
		this.itemTypeB = new ItemType("Bolachas", 20); 

		this.invoiceA = new Invoice(100.00f, new LocalDate(2018, 12, 21), "Brinquedos", this.seller, this.buyerA);
		this.invoiceB = new Invoice(100.00f, new LocalDate(2018, 12, 21), "Bolachas", this.seller, this.buyerB);
	}

	@Test
	public void success() {
		Assert.assertEquals(30.0, this.seller.toPay(2018), 0.0f);
		Assert.assertEquals(00.0, this.seller.toPay(2017), 0.0f);
	}

	@Test(expected = TaxException.class)
	public void yearBeforeSeventy() {
		this.seller.toPay(1969);
	}

	@After
	public void tearDown() {
		this.seller.clear();
		this.buyerA.clear();
		this.buyerB.clear();

		this.itemTypeA.clear(); 
		this.itemTypeB.clear();

		this.invoiceA.clear();
		this.invoiceB.clear();
	
		IRS.clear();
	}


}
