package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.Map;
import java.util.Iterator;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Seller extends TaxPayer{
	
	public Seller(String nif, String name, String address) {
		super(nif, name, address);
	}

	public float toPay(int year)  throws TaxException {
		if (year < TaxException.MIN_YEAR) { throw new TaxException(); }
		float toPay = 0;
		for (Iterator iter = this._invoices.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Invoice inv = (Invoice) pair.getValue();
			if (inv.getDate().getYear() == year) {
				toPay += inv.getIVA();
			}
		}
		return toPay;
	}

	public Invoice getInvoiceByReference(String reference) throws TaxException {
		if (reference == null || reference.trim().equals("")) { throw new TaxException(); }

		for (Iterator iter = this._invoices.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry pair = (Map.Entry) iter.next();
			if (pair.getKey().equals(reference)) {
				return (Invoice) pair.getValue();
			}
		}
		throw new TaxException();
	}
}
