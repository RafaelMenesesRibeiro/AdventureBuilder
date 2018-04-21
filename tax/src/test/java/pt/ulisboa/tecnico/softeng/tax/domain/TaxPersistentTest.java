package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class TaxPersistentTest {
	private static final String TAX_PAYER_NAME = "Jason";
	private static final String ADDRESS = "1600 Pennsylvania Ave";
	private static final LocalDate DATE = new LocalDate(2017, 04, 14);
	private static final String ITEM_TYPE_NAME = "Bananas";
	private static final int ITEM_TYPE_TAX = 20;
	private static ItemType ITEM_TYPE;
	private static final String SELLER_NIF = "123441223";
	private static Seller SELLER;
	private static final String BUYER_NIF = "123551223";
	private static Buyer BUYER;
	private static final double INVOICE_VALUE = 22;
	private static Invoice INVOICE;
	private static IRS irs;

	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() { 
		this.irs = IRS.getIRS(); //Creates the IRS (Singleton design pattern.)
		this.ITEM_TYPE = new ItemType(this.irs, ITEM_TYPE_NAME, ITEM_TYPE_TAX);
		this.SELLER = new Seller(this.irs, SELLER_NIF, TAX_PAYER_NAME, ADDRESS);
		this.BUYER = new Buyer(this.irs, BUYER_NIF, TAX_PAYER_NAME, ADDRESS);
		this.INVOICE = new Invoice(INVOICE_VALUE, DATE, ITEM_TYPE, SELLER, BUYER);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() { 
		//assertNotNullFenixFramework.getDomainRoot().getIRS());
		IRS irsDB = IRS.getIRS();
	
		List<ItemType> itemTypes = new ArrayList<>(irs.getItemTypeSet());
		ItemType item = itemTypes.get(0);
		assertEquals(ITEM_TYPE_NAME, item.getName());
		assertEquals(ITEM_TYPE_TAX, item.getTax());

		List<Invoice> invoices = new ArrayList<>(item.getInvoiceSet());
		Invoice invoice = invoices.get(0);
		assertEquals(INVOICE_VALUE, invoice.getValue(), 0.0f);
		assertEquals(DATE, invoice.getDate());
		assertEquals(ITEM_TYPE, invoice.getItemType());
		assertEquals(SELLER, invoice.getSeller());
		assertEquals(BUYER, invoice.getBuyer());
		
		//List<TaxPayer> payers = new ArrayList<>(irs.getTaxPayerSet());
		//assertEquals(2, payers.size());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		IRS.getIRS().delete();
	}
}
