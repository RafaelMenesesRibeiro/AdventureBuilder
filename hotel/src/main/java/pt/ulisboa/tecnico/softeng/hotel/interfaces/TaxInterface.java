package pt.ulisboa.tecnico.softeng.hotel.interfaces;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class TaxInterface {
    public static String submitInvoice(String sellerNIF, String buyerNIF, String itemType, double value, LocalDate date) {
        return IRS.submitInvoice(new InvoiceData(sellerNIF, buyerNIF, itemType, value, date));
    }

    public static String cancelInvoice(String referString) {
        return IRS.cancelInvoice(referString);
    }
}
