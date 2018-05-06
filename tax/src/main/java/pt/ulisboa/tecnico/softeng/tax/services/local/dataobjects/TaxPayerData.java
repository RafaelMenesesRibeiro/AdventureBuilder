package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;


import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;

public class TaxPayerData {
	private String nif;
	private String name;
	private String address;
	private String type;

	public TaxPayerData() {
	}

	public TaxPayerData(TaxPayer taxPayer) {
		this.nif = taxPayer.getNif();
		this.name = taxPayer.getName();
		this.address = taxPayer.getAddress();
		if (taxPayer.getClass().equals(Buyer.class)) {
			this.type = "Buyer";
		}
		else if (taxPayer.getClass().equals(Seller.class)) {
			this.type = "Seller";
		}
	}

	public String getType() { return this.type; }
	public void setType(String type) { this.type = type; }

	public String getNif() { return this.nif; }
	public void setNif(String nif) { this.nif = nif; }
	
	public String getName() {return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }
}
