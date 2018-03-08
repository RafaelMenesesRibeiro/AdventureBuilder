package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemType {
	private final String _name;
	private int _tax;
	public static Set<Invoice> invoices = new HashSet<>();
	private Set<Invoice> _invoices = new HashSet<>();
	
	public ItemType(String itemType, int tax) {
		checkArguments(itemType, tax);

		this._name = itemType;
		this._tax = tax;
	}

	public ItemType() {
		this._name = "";
		this._tax = 0;
		//USED WILE IRS NOT FULLY IMPPLEMENTED.
	}

	private void checkArguments(String itemType, int tax) throws TaxException {
		if (tax < TaxException.MIN_TAX) {
			throw new TaxException();
		}
		if (itemType == null || itemType.trim().equals("")) {
			throw new TaxException();	
		}
	}

	public String getName() {
		return this._name;
	}

	public float getTax() {
		return this._tax;
	}

	public int getNumberOfInvoices() {
		return 1;
		//return this._invoices.size();
	}
}
