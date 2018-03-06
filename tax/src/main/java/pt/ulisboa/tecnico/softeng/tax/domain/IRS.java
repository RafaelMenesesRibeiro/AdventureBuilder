package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public final class IRS {
	public static Set<TaxPayer> _taxPayers = new HashSet<TaxPayer>();
	public static Set<ItemType> _itemTypes = new HashSet<ItemType>();

	private IRS() {  }

	public static ItemType getItemTypeByName(String type) { 
		return new ItemType();
	}

	public static TaxPayer getTaxPayerByNIF(String buyerNIF) throws TaxException {
		if (buyerNIF.length() != TaxException.NIF_SIZE) {
			throw new TaxException();
		}

		return null;
	}

	public static void submitInvoice(InvoiceData invoice) {  }
}
