package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetTaxPayerByNIFMethodTest {
	Map<String, TaxPayer>taxPayers;
	Seller sellerA;
	Seller sellerB;
	Seller sellerC;
	Buyer buyerA;
	Buyer buyerB;
	Buyer buyerC;


	@Before
	public void setUp() {
		this.sellerA = new Seller("111111111", "DELTA", "Rua A");
		this.sellerB = new Seller("111111112", "GAP", "Rua B");
		this.sellerC = new Seller("111111113", "SuperBock", "Rua C");
		this.buyerA = new Buyer("111111114", "Luisa Maria", "Rua A");
		this.buyerB = new Buyer ("111111115", "Lucia Poles", "Rua B");
		this.buyerC = new Buyer("111111116", "Pedro Juna", "Rua C");
	}

	@Test
	public void success() {

		Assert.assertEquals(this.sellerA, IRS.getTaxPayerByNIF("111111111"));
		Assert.assertEquals(this.sellerB, IRS.getTaxPayerByNIF("111111112"));
		Assert.assertEquals(this.sellerC, IRS.getTaxPayerByNIF("111111113"));
		Assert.assertEquals(this.buyerA, IRS.getTaxPayerByNIF("111111114"));
		Assert.assertEquals(this.buyerB, IRS.getTaxPayerByNIF("111111115"));
		Assert.assertEquals(this.buyerC, IRS.getTaxPayerByNIF("111111116"));
	}



	@Test(expected = TaxException.class)
	public void nullNIF() {
		IRS.getTaxPayerByNIF(null);
	}

	@Test(expected = TaxException.class)
	public void emptyNIF() {
		IRS.getTaxPayerByNIF("");
	}

	@Test(expected = TaxException.class)
	public void NIFTenDigits() {
		IRS.getTaxPayerByNIF("1111111111");
	}


	@Test(expected = TaxException.class)
	public void NIFWithChar() {
		IRS.getTaxPayerByNIF("1111111a1");
	}


	@Test(expected = TaxException.class)
	public void NIFNotExists() {
		IRS.getTaxPayerByNIF("000000000");
	}


	@After
	public void tearDown() {
		this.sellerA.clear();
		this.sellerB.clear();
		this.sellerC.clear();
		this.buyerA.clear();
		this.buyerB.clear();
		this.buyerC.clear();
		IRS.clear();
	}


}
