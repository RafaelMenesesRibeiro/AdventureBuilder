package pt.ulisboa.tecnico.softeng.hotel.interfaces;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class TaxInterface {
    public static String submitInvoice(InvoiceData invoiceData) {
        return IRS.submitInvoice(invoiceData);
    }

	public void cancelInvoice(String referString) {
		IRS.cancelInvoice(referString);
	}
}
