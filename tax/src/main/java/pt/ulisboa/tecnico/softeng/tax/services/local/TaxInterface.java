package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.util.List;
import java.util.stream.Collectors;


import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;

import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class TaxInterface {
    @Atomic(mode = TxMode.READ)
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
    
}