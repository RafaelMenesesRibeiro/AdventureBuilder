package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.Map;
import java.util.HashMap;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class TaxPayer {
	public static final int NIF_SIZE = 9;

	private final String _NIF;
	private String _name;
	private String _address;
	protected Map<String, Invoice> _invoices = new HashMap<>();

	public TaxPayer(String nif, String name, String address) {
		checkArgumetns(nif, name, address);

		this._NIF = nif;
		this._name = name;
		this._address = address;
		IRS.addTaxPayer(this);
		//TODO: ADD TO IRS LIST.
	}

	public TaxPayer() {
		this._NIF = "";
		this._name = "";
		this._address = "";
		//USED WILE IRS NOT FULLY IMPPLEMENTED.
	}

	private void checkArgumetns(String nif, String name, String address)  throws TaxException {
		if (nif == null || nif.length() != TaxException.NIF_SIZE) {
			throw new TaxException();
		}
		if (name == null || name.trim().equals("") || address == null || address.trim().equals("")) {
			throw new TaxException();	
		}
	}

	public String getNif() {
		return this._NIF;
	}

	public String getName() {
		return this._name;
	}

	public String getAddress() {
		return this._address;
	}

	public Map<String, Invoice> getInvoices() {
		return this._invoices;
	}

	public void addInvoice(Invoice invoice) {
		String ref = invoice.getReference();
		_invoices.put(ref, invoice);
	}
}
