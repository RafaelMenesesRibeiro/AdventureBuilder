package pt.ulisboa.tecnico.softeng.tax.domain;

import java.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;

public class Invoice {
	private final String _sellerNIF;
	private final String _buyerNIF;
	private final String _reference;
	private final ItemType _itemType;
	private final float _value;
	private final float _IVA;
	private final LocalDate _date;

	public Invoice(float value, LocalDate date, ItemType type, String sellerNIF, String buyerNIF) {
		this._sellerNIF = sellerNIF;
		this._buyerNIF = buyerNIF;
		this._reference = createNewReference();
		this._itemType = type;
		this._IVA = calculateIVA(value);
		this._value = calculateTotal(value);
		this._date = LocalDate.now();
		//TODO: ADD TO IRS LIST.
	}

	public Invoice() {
		this._sellerNIF = "";
		this._buyerNIF = "";
		this._reference = "";
		this._itemType = new ItemType();
		this._IVA = calculateIVA(0);
		this._value = calculateTotal(0);
		this._date = LocalDate.now();
		//USED WILE SELLER NOT FULLY IMPPLEMENTED.
	}

	private String createNewReference() { 
		return "";
	}

	private float calculateIVA(float value) {
		return 0;
	}

	private float calculateTotal(float value) {
		return 0;
	}
}
