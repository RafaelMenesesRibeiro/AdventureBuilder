package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice {
	private final TaxPayer _seller;
	private final TaxPayer _buyer;
	private final String _reference;
	private final ItemType _itemType;
	private final float _value;
	private final float _IVA;
	private final LocalDate _date;

	public Invoice(float value, LocalDate date, String type, TaxPayer seller, TaxPayer buyer) {
		checkArguments(value, date, type, seller, buyer);

		this._seller = seller;
		this._buyer = buyer;
		this._reference = createNewReference();
		this._itemType = IRS.getItemTypeByName(type);
		this._IVA = calculateIVA(value);
		this._value = calculateTotal(value);
		this._date = date;
		//TODO: ADD TO IRS LIST.
	}

	public Invoice() {
		this._seller = null;
		this._buyer = null;
		this._reference = "";
		this._itemType = new ItemType();
		this._IVA = calculateIVA(0);
		this._value = calculateTotal(0);
		this._date = LocalDate.now();
		//USED WILE SELLER NOT FULLY IMPPLEMENTED.
	}

	private void checkArguments(float value, LocalDate date, String type, TaxPayer seller, TaxPayer buyer) throws TaxException {
		if (date == null) {
			throw new TaxException();
		}
		if (value < 0 || date.getYear() < TaxException.MIN_YEAR) {
			throw new TaxException();
		}
		if (type == null || type.trim().equals("")) {
			throw new TaxException();
		}
		if (seller == null || seller.getNif().length() != TaxException.NIF_SIZE || buyer == null || buyer.getNif().length() != TaxException.NIF_SIZE) {
			throw new TaxException();
		}
	}

	private String createNewReference() { 
		return "";
	}

	private float calculateIVA(float value) {
		return 0;
	}

	private float calculateTotal(float value) {
		return value;
	}

	public float getValue() {
		return this._value;
	}

	public LocalDate getDate() {
		return this._date;
	}
}
