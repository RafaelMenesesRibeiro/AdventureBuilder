package pt.ulisboa.tecnico.softeng.tax.domain;

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
	private static TaxPayer SELLER;
	private static final String BUYER_NIF = "123551223";
	private static TaxPayer BUYER;
	private static IRS irs;

	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() { 
		//TODO: Uncoment when needed.
		/*
		this.ITEM_TYPE = new ItemType(this.irs, ITEM_TYPE_NAME, ITEM_TYPE_TAX);
		this.SELLER = new Seller(this.irs, SELLER_NIF, TAX_PAYER_NAME, ADDRESS);
		this.BUYER = new Buyer(this.irs, BUYER_NIF, TAX_PAYER_NAME, ADDRESS);
		*/
		this.irs = IRS.getIRS(); //Creates the IRS (Singleton design pattern.)
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() { 
		//assertNotNullFenixFramework.getDomainRoot().getIRS());
		IRS irsDB = IRS.getIRS();

		//List<ItemTyper> itemTypes = new ArrayList<>(irs.getItemTypeSet());
		//ItemType item = itemTypes.get(0);
		//assertEquals(ITEM_TYPE_NAME, item.getName());
		//assertEquals(ITEM_TYPE_TAX, item.getTax());

		//List<TaxPayer> payers = new ArrayList<>(irs.getTaxPayerSet());
		//assertEquals(2, payers.size());

		


	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		IRS.getIRS().delete();
	}
}
