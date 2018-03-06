package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;

public class Buyer extends TaxPayer{
	
	public Buyer(String nif, String name, String address) {
		super(nif, name, address);
		//TODO: ADD TO IRS LIST.
	}

	public float taxReturn(int year) {
		return 0;
	}
}
