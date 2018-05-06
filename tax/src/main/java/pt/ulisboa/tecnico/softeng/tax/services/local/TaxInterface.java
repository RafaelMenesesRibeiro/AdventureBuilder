package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.util.List;
import java.util.stream.Collectors;


import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;


public class TaxInterface {
	@Atomic(mode = TxMode.WRITE)
	public static List<TaxPayerData> getTaxPayers() {
		return IRS.getIRSInstance().getTaxPayerSet().stream().map(t -> new TaxPayerData(t))
				.collect(Collectors.toList()); 
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createTaxPayer(TaxPayerData taxPayerData) {
		String type = taxPayerData.getType();
		if (type.equals("Buyer")) {
			new Buyer(IRS.getIRSInstance(), taxPayerData.getNif(), taxPayerData.getName(), taxPayerData.getAddress());
		}
		else if (type.equals("Seller")) {
			new Seller(IRS.getIRSInstance(), taxPayerData.getNif(), taxPayerData.getName(), taxPayerData.getAddress());
		}
	}

	@Atomic(mode = TxMode.READ)
	public static List<ItemTypeData> getItemTypes() {
		return IRS.getIRSInstance().getItemTypeSet().stream().map(t -> new ItemTypeData(t))
				.collect(Collectors.toList()); 
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createItemType(ItemTypeData itemTypeData) {
		new ItemType(IRS.getIRSInstance(), itemTypeData.getName(), itemTypeData.getTax());
	}

	@Atomic(mode = TxMode.WRITE)
	public static void createInvoice(InvoiceData invoiceData) {
		IRS.getIRSInstance().submitInvoice(invoiceData);
	}

	@Atomic(mode = TxMode.WRITE)
	public static List<InvoiceData> getInvoicesByTaxPayerNIF(String reference) {

		TaxPayer tp = IRS.getIRSInstance().getTaxPayerByNIF(reference);

		if (tp.getClass().equals(Buyer.class)) {
			Buyer newBuyer = (Buyer) tp;
			return newBuyer.getInvoiceSet().stream().map(i -> new InvoiceData(i))
				.collect(Collectors.toList());
		} else if (tp.getClass().equals(Seller.class)) {
			Seller newSeller = (Seller) tp;
			return newSeller.getInvoiceSet().stream().map(i -> new InvoiceData(i))
				.collect(Collectors.toList());
		} else {
			return null;
		}

		
	}
	@Atomic(mode = TxMode.WRITE)
	public static boolean isBuyer(String reference) {
		TaxPayer tp = IRS.getIRSInstance().getTaxPayerByNIF(reference);
		if (tp.getClass().equals(Buyer.class)) {
			return true;
		} else { 
			return false; 
		}
	}
	
}