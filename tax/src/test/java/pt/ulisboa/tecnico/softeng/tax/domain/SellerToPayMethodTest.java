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
	private HashMap<String, Invoice> invoices;
	private Seller seller;
	private Buyer buyerA;
	private Buyer buyerB;
	private Invoice invoiceA;
	private Invoice invoiceB;
	private ItemType itemTypeA;	
	private ItemType itemTypeB;


	@Before
	public void setUp() {
		this.seller = new Seller("11111111", "GALP", "Rua Principal");
		this.buyerA = new Buyer("11111112", "Maria Inês", "Rua Secundária");
		this.buyerB = new Buyer("11111112", "Filipa Otacvia", "Rua Secundária");

		this.itemTypeA = new ItemType("Brinquedos", 10); 
		this.itemTypeB = new ItemType("Bolachas", 20); 

		this.invoiceA = new Invoice(100.00f, new LocalDate(2018, 12, 21), "Brinquedos", this.seller, this.buyerA);
		this.invoiceA = new Invoice(100.00f, new LocalDate(2018, 12, 21), "Bolachas", this.seller, this.buyerB);
		
		Assert.assertEquals(30.0, this.seller.toPay(2018), 0.0f);
	}

	@Test
	public void success() {

	}


	@After
	public void tearDown() {
	}


}
