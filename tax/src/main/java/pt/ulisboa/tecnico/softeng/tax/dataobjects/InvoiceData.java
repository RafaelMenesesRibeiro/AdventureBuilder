package pt.ulisboa.tecnico.softeng.tax.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceData {
	private final String sellerNIF;
	private final String buyerNIF;
	private final String itemType;
	private final float value;
	private final LocalDate date;

	public InvoiceData(String sellerNIF, String buyerNIF, String itemType, float value, LocalDate date) {
		checkArguments(sellerNIF, buyerNIF, itemType, value, date);

		this.sellerNIF = sellerNIF;
		this.buyerNIF = buyerNIF;
		this.itemType = itemType;
		this.value = value;
		this.date = date;
	}

	private void checkArguments(String sellerNIF, String buyerNIF, String itemType, float value, LocalDate date) {
		if (sellerNIF.length() != TaxException.NIF_SIZE || buyerNIF.length() != TaxException.NIF_SIZE) {
			throw new TaxException();
		}
		if (itemType == null || itemType.trim().equals("")) {
			throw new TaxException();	
		}
		if (value < TaxException.MIN_VALUE || date.getYear() < TaxException.MIN_YEAR) {
			throw new TaxException();	
		}
	}

	public String getSellerNIF() {
		return this.sellerNIF;
	}

	public String getBuyerNIF() {
		return this.buyerNIF;
	}

	public String getItemType() {
		return this.itemType;
	}

	public float getValue() {
		return this.value;
	}

	public LocalDate getDate() {
		return this.date;
	}
}
