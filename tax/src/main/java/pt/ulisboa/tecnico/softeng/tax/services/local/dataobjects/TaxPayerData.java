package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;


import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;

public class TaxPayerData {
	private String nif;
	private String name;
	private String address;

	public TaxPayerData() {
	}

	public TaxPayerData(TaxPayer taxPayer) {
		this.nif = taxPayer.getNif();
		this.name = taxPayer.getName();
		this.address = taxPayer.getAddress();
	}

	public String getNif() { return this.nif; }
	public void setNif(String nif) { this.nif = nif; }
	
	public String getName() {return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }
}
