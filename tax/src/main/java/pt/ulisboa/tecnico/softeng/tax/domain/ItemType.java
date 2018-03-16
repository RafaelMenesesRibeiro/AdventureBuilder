package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemType {
	private final String _name;
	private int _tax;
	private Map<String, Invoice> _invoices = new HashMap<>();
	
	public ItemType(String itemType, int tax) {
		checkArguments(itemType, tax);

		try {
			ItemType t = IRS.getItemTypeByName(itemType);
		}
		catch (TaxException e) {
			this._name = itemType;
			this._tax = tax;
			IRS.addItemType(this);
			return;
		}
		throw new TaxException();
	}

	private void checkArguments(String itemType, int tax) throws TaxException {
		if (tax < TaxException.MIN_TAX) { throw new TaxException(); }
		if (itemType == null || itemType.trim().equals("")) { throw new TaxException();	}
	}

	public String getName() { return this._name; }
	public int getTax() { return this._tax; }
	public Map<String, Invoice> getInvoices() { return this._invoices; }

	public void submitInvoice(Invoice data) { this._invoices.put(data.getReference(), data); }

	public void clear() { _invoices.clear(); }
}
