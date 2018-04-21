package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.DomainRoot;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRS extends IRS_Base{
	private final Set<TaxPayer> taxPayers = new HashSet<>();

	public static IRS getIRS() {
		IRS instance = FenixFramework.getDomainRoot().getUniqueIRS();
		if (instance != null) { return instance; }
		new IRS();
		return FenixFramework.getDomainRoot().getUniqueIRS();

	}

	private IRS() { FenixFramework.getDomainRoot().setUniqueIRS(this); } 

	public void delete() {
		setRoot(null);
		deleteDomainObject();
	}

	void addTaxPayer(TaxPayer taxPayer) {
		this.taxPayers.add(taxPayer);
	}

	public TaxPayer getTaxPayerByNIF(String NIF) {
		for (TaxPayer taxPayer : this.taxPayers) {
			if (taxPayer.getNIF().equals(NIF)) {
				return taxPayer;
			}
		}
		return null;
	}

	public ItemType getItemTypeByName(String name) {
		for (ItemType itemType : getItemTypeSet()) {
			if (itemType.getName().equals(name)) {
				return itemType;
			}
		}
		return null;
	}

	public static String submitInvoice(InvoiceData invoiceData) {
		IRS irs = IRS.getIRS();
		Seller seller = (Seller) irs.getTaxPayerByNIF(invoiceData.getSellerNIF());
		Buyer buyer = (Buyer) irs.getTaxPayerByNIF(invoiceData.getBuyerNIF());
		ItemType itemType = irs.getItemTypeByName(invoiceData.getItemType());
		Invoice invoice = new Invoice(invoiceData.getValue(), invoiceData.getDate(), itemType, seller, buyer);

		return invoice.getReference();
	}

	public void removeTaxPayers() {
		this.taxPayers.clear();
	}

	public void clearAll() {
		removeTaxPayers();
	}

	public static void cancelInvoice(String reference) {
		if (reference == null || reference.isEmpty()) {
			throw new TaxException();
		}

		Invoice invoice = IRS.getIRS().getInvoiceByReference(reference);

		if (invoice == null) {
			throw new TaxException();
		}

		invoice.cancel();
	}

	private Invoice getInvoiceByReference(String reference) {
		for (TaxPayer taxPayer : this.taxPayers) {
			Invoice invoice = taxPayer.getInvoiceByReference(reference);
			if (invoice != null) {
				return invoice;
			}
		}
		return null;
	}
}
