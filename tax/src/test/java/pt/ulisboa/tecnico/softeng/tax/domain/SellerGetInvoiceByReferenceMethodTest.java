package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerGetInvoiceByReferenceMethodTest{
	private Seller seller;
	private Buyer buyer;
	private Invoice invoice;
	private String reference;
	private String ITEM_TYPE = "Brinquedos";
	private ItemType itemType;

	@Before
	public void setUp() {
		this.seller = new Seller("111111111", "GALP", "Rua Principal");
		this.buyer = new Buyer("111111112", "Maria Inês", "Rua Secundária");
		this.itemType = new ItemType(ITEM_TYPE, 34);
		this.invoice = new Invoice(100.00f, new LocalDate(2018, 12, 21), ITEM_TYPE, this.seller, this.buyer);
		this.reference = this.invoice.getReference();
	}

	@Test
	public void success() {
		Invoice inv = this.seller.getInvoiceByReference(this.reference);

		Assert.assertEquals(this.invoice, inv);
		Assert.assertEquals(this.reference, inv.getReference());
	}

	@Test(expected = TaxException.class)
	public void nullReference() {
		Invoice inv = this.seller.getInvoiceByReference(null);
	}

	@Test(expected = TaxException.class)
	public void emptyReference() {
		Invoice inv = this.seller.getInvoiceByReference("");
	}

	@Test(expected = TaxException.class)
	public void NonExistingReference() {
		String newRef = this.reference.replaceAll(this.seller.getNIF(), Integer.toString(Integer.parseInt(this.seller.getNIF()) + 1));
		Invoice inv = this.seller.getInvoiceByReference(newRef);
	}

	@After
	public void tearDown() {
		IRS.clear();
	}
}
