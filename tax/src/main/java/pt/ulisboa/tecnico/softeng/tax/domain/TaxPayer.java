package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;

public class TaxPayer {
	private final String _NIF;
	private String _name;
	private String _address;

	public TaxPayer(String nif, String name, String address) {
		this._NIF = nif;
		this._name = name;
		this._address = address;
		//TODO: ADD TO IRS LIST.
	}

	public TaxPayer() {
		this._NIF = "";
		this._name = "";
		this._address = "";
		//USED WILE IRS NOT FULLY IMPPLEMENTED.
	}
}
