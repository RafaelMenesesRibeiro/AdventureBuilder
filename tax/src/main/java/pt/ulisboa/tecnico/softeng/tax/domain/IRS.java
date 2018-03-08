package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public final class IRS {
	public static Map<String, TaxPayer> _taxPayers = new HashMap<>();
	public static Set<ItemType> _itemTypes = new HashSet<ItemType>();
	public static Set<InvoiceData> _invoices = new HashSet<>();

	private IRS() {  }

	public static ItemType getItemTypeByName(String type) { 
		return new ItemType();
	}

	public static int getNumberOfInvoices() { return 1; }
	public static int getNumberOfItems() { return 1; }

	public static TaxPayer getTaxPayerByNIF(String buyerNIF) throws TaxException {
		if (buyerNIF.length() != TaxException.NIF_SIZE) {
			throw new TaxException();
		}

		return null;
	}

	public static void addTaxPayer(TaxPayer payer) {
		_taxPayers.put(payer.getNif(), payer);
	}

	public static void submitInvoice(InvoiceData data) {
		_invoices.add(data);
	}
}
