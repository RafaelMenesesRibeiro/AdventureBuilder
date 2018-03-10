package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.Map;
import java.util.Iterator;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Buyer extends TaxPayer{

	public Buyer(String nif, String name, String address) {
		super(nif, name, address);
	}

	public float taxReturn(int year) throws TaxException {
		Map<String, Invoice> invoices = this.getInvoices();
		float taxReturn = 0.00f;
		for (Iterator iter = invoices.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry pair = (Map.Entry) iter.next();
			Invoice invoice = (Invoice) pair.getValue();
			if (invoice.getDate().getYear() == year) {
				float invoiceValue = invoice.getValue();
				int invoiceTax = invoice.getItemType().getTax();
				taxReturn += (float) invoiceValue * invoiceTax * 0.05;
			}
		}
		return taxReturn;
	}
}