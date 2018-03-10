package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceConstructorMethodTest {
	private static final float INVOICE_VALUE = 1.00f;
	private static final String ITEM_TYPE = "Roupa";
	private LocalDate date;
	private Buyer buyer;
	private Seller seller;
	private ItemType itemType;
	private Invoice invoice;



	@Before
	public void setUp() {
		this.buyer = new Buyer ("177777777", "DELTA", "Estrela da lapa");
		this.seller = new Seller("111111111", "Joao Antonio", "Rua dos Vendedores");
		this.itemType = IRS.getItemTypeByName(ITEM_TYPE);
		this.date = new LocalDate(2018, 12, 21);
	}

	@Test
	public void success() {
		this.invoice = new Invoice(INVOICE_VALUE, this.date, ITEM_TYPE, this.seller, this.buyer);

		Assert.assertEquals(INVOICE_VALUE, invoice.getValue(), 0.0f);
		Assert.assertEquals(this.date, invoice.getDate());
		Assert.assertEquals(ITEM_TYPE, invoice.getItemType());
		Assert.assertEquals(this.seller, invoice.getSeller());
		Assert.assertEquals(this.buyer, invoice.getBuyer());

		Assert.assertTrue(invoice.getValue() > 0);
		Assert.assertTrue(invoice.getDate().getYear() > 1970);

		Assert.assertNotNull(this.buyer.getInvoices());
		Assert.assertNotNull(this.seller.getInvoices());
		Assert.assertNotNull(this.itemType.getInvoices());
	}

	@Test(expected = TaxException.class)
	public void sameBuyerAndSeller() {
		Seller  sameSeller = new Seller("177777777", "DELTA", "Estrela da lapa");
		new Invoice(INVOICE_VALUE, this.date, ITEM_TYPE, sameSeller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void copyOfInvoice() {
		new Invoice(INVOICE_VALUE, this.date, ITEM_TYPE, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void beforeSeventyDate() {
		new Invoice(INVOICE_VALUE, new LocalDate(1970, 12, 31), ITEM_TYPE, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullDate() {
		new Invoice(INVOICE_VALUE, null, ITEM_TYPE, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullItemType() {
		new Invoice(INVOICE_VALUE, this.date, null, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new Invoice(INVOICE_VALUE, this.date, "    ", this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullSeller() {
		new Invoice(INVOICE_VALUE, this.date, ITEM_TYPE, null, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullBuyer() {
		new Invoice(INVOICE_VALUE, this.date, ITEM_TYPE, this.seller, null);
	}

	@After
	public void tearDown() {

	}


}
