package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.Iterator;
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
		for (ItemType t : _itemTypes) {
			if (t.getName().equals(type)) {
				return t;
			}
		}
		throw new TaxException();
	}

	public static Set<InvoiceData> getInvoices() { return _invoices; }
	public static Set<ItemType> getItemTypes() { return _itemTypes; }
	public static TaxPayer getTaxPayerByNIF(String nif) throws TaxException {
		if (nif == null || nif.length() != TaxException.NIF_SIZE) { throw new TaxException(); }

		for (Iterator iter = _taxPayers.entrySet().iterator(); iter.hasNext(); ) {
			Map.Entry pair = (Map.Entry) iter.next();
			if (pair.getKey().equals(nif)) {
				return (TaxPayer) pair.getValue();				
			}
		}
		throw new TaxException();
	}

	public static void addTaxPayer(TaxPayer payer) { _taxPayers.put(payer.getNIF(), payer); }
	public static void addItemType(ItemType type) { _itemTypes.add(type); }

	public static void submitInvoice(InvoiceData data) { 
		if (data == null ) { throw new TaxException(); }
		_invoices.add(data); }

	public static void clear() {
		_taxPayers.clear();
		_itemTypes.clear();
		_invoices.clear();
	}
}
