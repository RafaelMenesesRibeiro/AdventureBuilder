package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Seller extends Seller_Base {

	public Seller() { super(); }

	public Seller(IRS irs, String NIF, String name, String address) {
		super.checkArguments(irs, NIF, name, address);

		setNIF(NIF);
		setName(name);
		setAddress(address);

		irs.addTaxPayer(this);
	}

	public double toPay(int year) {
		if (year < 1970) {
			throw new TaxException();
		}

		double result = 0;
		for (Invoice invoice : getInvoiceSet()) {
			if (!invoice.isCancelled() && invoice.getDate().getYear() == year) {
				result = result + invoice.getIva();
			}
		}
		return result;
	}
}
