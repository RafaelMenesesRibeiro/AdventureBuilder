package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemType {
	private final String _name;
	private int _tax;
	
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

	public float getTax() {
		return _tax;
	}
}
