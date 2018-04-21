package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice extends Invoice_Base{
	@Override
	public int getCounter() {
		int counter = super.getCounter() + 1;
		setCounter(counter);
		return counter;
	}

	Invoice(double value, LocalDate date, ItemType itemType, Seller seller, Buyer buyer) {
		checkArguments(value, date, itemType, seller, buyer);

		super.setReference(Integer.toString(getCounter()));
		super.setValue(value);
		super.setDate(date);
		super.setItemType(itemType);
		super.addTaxPayer(seller);
		super.addTaxPayer(buyer);
		super.setIva(value * itemType.getTax() / 100);
		super.setCancelled(false);

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
		setCancelled(true);
	}

	public boolean isCancelled() {
		return getCancelled();
	}

	public void delete() {
		deleteDomainObject();
	}
}
