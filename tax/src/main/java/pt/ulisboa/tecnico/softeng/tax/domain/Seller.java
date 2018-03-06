package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;

public class Seller extends TaxPayer{
	
	public Seller(String nif, String name, String address) {
		super(nif, name, address);
		//TODO: ADD TO IRS LIST.
	}

	public float toPay(int year) {
		return 0;
	}

	public Invoice getInvoiceByReference(String reference) {
		return new Invoice();
	}
}
