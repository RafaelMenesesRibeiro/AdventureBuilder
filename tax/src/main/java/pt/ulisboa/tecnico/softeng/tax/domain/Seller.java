package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Seller extends TaxPayer{
	
	public Seller(String nif, String name, String address) {
		super(nif, name, address);
		//TODO: ADD TO IRS LIST.
	}

	public float toPay(int year)  throws TaxException {
		if (year <= TaxException.MIN_YEAR) {
			throw new TaxException();
		}

		return 0;
	}

	public Invoice getInvoiceByReference(String reference) throws TaxException {
		if (reference == null || reference.trim().equals("")) {
			throw new TaxException();
		}

		return new Invoice();
	}
}
