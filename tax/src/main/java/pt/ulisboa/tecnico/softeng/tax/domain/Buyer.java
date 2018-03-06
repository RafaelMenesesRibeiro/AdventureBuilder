package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Buyer extends TaxPayer{

	public Buyer(String nif, String name, String address) {
		super(nif, name, address);
		//TODO: ADD TO IRS LIST.
	}

	public float taxReturn(int year) throws TaxException {
		if (year <= TaxException.MIN_YEAR) {
			throw new TaxException();
		}

		return 0;
	}
}
