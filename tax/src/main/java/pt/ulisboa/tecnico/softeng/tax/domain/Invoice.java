package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
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
		this._reference = createNewReference(date, seller, buyer, value);
		this._itemType = IRS.getItemTypeByName(type);
		this._IVA = calculateIVA(value);
		this._value = calculateTotal(value);
		this._date = date;
		
		InvoiceData data = new InvoiceData(seller.getNif(), buyer.getNif(), type, value, date);
		IRS.submitInvoice(data);
		seller.addInvoice(this);
		buyer.addInvoice(this);
		ItemType item = IRS.getItemTypeByName(type);
		item.submitInvoice(this);
		//TODO: ADD TO IRS LIST.
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

	private String createNewReference(LocalDate date, TaxPayer seller, TaxPayer buyer, float value) {
		String sellerNIF = seller.getNif();
		String buyerNIF = buyer.getNif();
		return date.toString("MM/dd/yyyy") + sellerNIF + buyerNIF + String.valueOf(value);
	}

	private float calculateIVA(float value) {
		return 0;
	}

	private float calculateTotal(float value) {
		return value;
	}

	public float getValue() { return this._value; }
	public float getIVA() { return this._IVA; }
	public LocalDate getDate() { return this._date;	}
	public int getYear() { return this._date.getYear(); }
	public String getReference() { return this._reference; }
	public ItemType getItemType() { return this._itemType; }
	public TaxPayer getSeller() { return this._seller; }
	public TaxPayer getBuyer() { return this._buyer; }
}
