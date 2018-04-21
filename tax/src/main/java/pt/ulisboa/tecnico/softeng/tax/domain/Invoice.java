package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice extends Invoice_Base{
	private static int counter = 0;
	private final ItemType itemType;

	private boolean cancelled = false;

	Invoice(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		checkArguments(value, date, itemType, seller, buyer);

		super.setReference(Integer.toString(++Invoice.counter));
		super.setValue(value);
		super.setDate(date);
		this.itemType = itemType;
		super.setSeller(seller);
		super.setBuyer(buyer);
		super.setIva(value * itemType.getTax() / 100);

		seller.addInvoice(this);
		buyer.addInvoice(this);
	}

	private void checkArguments(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		if (value <= 0.0f) {
			throw new TaxException();
		}

		if (date == null || date.getYear() < 1970) {
			throw new TaxException();
		}

		if (itemType == null) {
			throw new TaxException();
		}

		if (seller == null) {
			throw new TaxException();
		}

		if (buyer == null) {
			throw new TaxException();
		}
	}

	public ItemType getItemType() {
		return this.itemType;
	}



	public void cancel() {
		this.cancelled = true;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}



	public void delete() {
		setSeller(null);
		setBuyer(null);
		deleteDomainObject();
	}
}
