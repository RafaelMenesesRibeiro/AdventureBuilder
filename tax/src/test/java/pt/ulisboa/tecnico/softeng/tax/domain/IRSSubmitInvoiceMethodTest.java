package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.HashSet;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class IRSSubmitInvoiceMethodTest {
	private Seller seller;
	private Buyer buyerA;
	private Buyer buyerB;
	private InvoiceData invoiceA;
	private InvoiceData invoiceB;
	private ItemType itemTypeA;	
	private ItemType itemTypeB;

	@Before
	public void setUp() {
		this.seller = new Seller("111111111", "GALP", "Rua Principal");

		this.buyerA = new Buyer("111111112", "Maria Inês", "Rua Secundária");
		this.buyerB = new Buyer("111111113", "Filipa Otacvia", "Rua Secundária");

		this.itemTypeA = new ItemType("Brinquedos", 10); 
		this.itemTypeB = new ItemType("Bolachas", 20); 

		this.invoiceA = new InvoiceData(this.seller.getNIF(), this.buyerA.getNIF(), "Brinquedos", 100.0f, new LocalDate(2018, 12, 21));
		this.invoiceB = new InvoiceData(this.seller.getNIF(), this.buyerB.getNIF(), "Bolachas" , 100.0f, new LocalDate(2018, 12, 21));
	}

	@Test
	public void success() {

		IRS.submitInvoice(this.invoiceA);
		IRS.submitInvoice(this.invoiceB);

		Assert.assertTrue(IRS.getInvoices().contains(this.invoiceA));
		Assert.assertTrue(IRS.getInvoices().contains(this.invoiceB));
		Assert.assertTrue(IRS.getInvoices().size() == 2);
	}

	@Test(expected = TaxException.class)
	public void nullInvoiceData() {
		IRS.submitInvoice(null);
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
