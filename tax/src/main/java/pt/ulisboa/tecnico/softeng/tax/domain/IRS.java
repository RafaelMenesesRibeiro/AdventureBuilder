package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;

public class IRS {
	public static Set<TaxPayer> _taxPayers = new HashSet<TaxPayer>();
	public static Set<ItemType> _itemTypes = new HashSet<ItemType>();

	public ItemType getItemTypeByName(String type) { 
		return new ItemType();
	}

	public TaxPayer getTaxPayerByNIF(String buyerNIF) {
		return null;
	}

	public void submitInvoice() {  }
}
