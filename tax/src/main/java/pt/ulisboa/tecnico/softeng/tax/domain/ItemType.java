package pt.ulisboa.tecnico.softeng.tax.domain;

public class ItemType {
	private final String _name;
	private int _tax;
	
	public ItemType(String name, int tax) {
		this._name = name;
		this._tax = tax;
	}

	public ItemType() {
		this._name = "";
		this._tax = 0;
		//USED WILE IRS NOT FULLY IMPPLEMENTED.
	}

	public float getTax() {
		return _tax;
	}
}
