package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice extends Invoice_Base{
	private static int counter = 0;
	private boolean cancelled = false;

	Invoice(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		checkArguments(value, date, itemType, seller, buyer);

		super.setReference(Integer.toString(++Invoice.counter));
		super.setValue(value);
		super.setDate(date);
		super.setItemType(itemType);
		super.addTaxPayer(seller);
		super.addTaxPayer(buyer);
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

	public TaxPayer getSeller() {
		for (TaxPayer tp : getTaxPayerSet()) {
			if (tp.getClass() == Seller.class) {
				return tp;
			}
		}
		return null;
	}

	public TaxPayer getBuyer() {
		for (TaxPayer tp : getTaxPayerSet()) {
			if (tp.getClass() == Buyer.class) {
				return tp;
			}
		}
		return null;
	}

	public void cancel() {
		this.cancelled = true;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void delete() {
		deleteDomainObject();
	}
}
