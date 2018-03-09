package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Iterator;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerTaxReturnMethodTest {

	private Buyer buyer;
	private Seller seller;



	@Before
	public void setUp() {
		this.buyer = new Buyer("177777777", "João Neves", "Estrela da lapa");
		this.seller = new Seller("111111111", "Antonio Sarmente", "Rua dos Vendedores");

		ItemType bmaria = new ItemType("Bolacha Maria", 50);
		ItemType bjoana = new ItemType("Bolacha Joana", 50);
		ItemType bantonia = new ItemType("Bolacha Antónia", 50);
		ItemType blaura = new ItemType("Bolacha Laura", 50);
		ItemType bfatima = new ItemType("Bolacha Fátima", 50);
		ItemType bmarcia = new ItemType("Bolacha Márcia", 50);


		new Invoice(10.00f, new LocalDate(2011, 1, 1), "Bolacha Maria", this.seller, this.buyer);
		new Invoice(10.00f, new LocalDate(2012, 1, 1), "Bolacha Joana", this.seller, this.buyer);
		new Invoice(10.00f, new LocalDate(2013, 1, 1), "Bolacha Antónia", this.seller, this.buyer);
		new Invoice(10.00f, new LocalDate(2014, 1, 1), "Bolacha Laura", this.seller, this.buyer);
		new Invoice(10.00f, new LocalDate(2014, 1, 1), "Bolacha Fátima", this.seller, this.buyer);
		new Invoice(10.00f, new LocalDate(2014, 1, 1), "Bolacha Márcia", this.seller, this.buyer);

	}

	@Test
	public void success() {


		float expectedReturn = 0.0f;

		for (Iterator iter = buyer.getInvoices().entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Invoice selectedInvoice = (Invoice) pair.getValue();

			float selectedInvoiceValue = selectedInvoice.getValue();
			int selectedItemTax = selectedInvoice.getItemType().getTax();
			int year = selectedInvoice.getYear();

			if (year == 2014) {
				expectedReturn += (float)selectedInvoiceValue * selectedItemTax * 0.05;
			}
		}
		Assert.assertEquals(expectedReturn, this.buyer.taxReturn(2014), 0.0f);
	}



	@After
	public void tearDown() {
	}


}
