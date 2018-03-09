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
		this.sellerA = new Seller("111111111", "António Lopes", "Rua A");
		this.sellerB = new Seller("111111112", "Luis Neves", "Rua B");
		this.sellerC = new Seller("111111113", "João Pedro", "Rua C");
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
	public void NIFNotExist() {
		IRS.getTaxPayerByNIF("Videojogo");
	}


	@After
	public void tearDown() {
	}


}
