package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ist.fenixframework.FenixFramework;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemType extends ItemType_Base {

	public ItemType(IRS irs, String name, int tax) {
		checkArguments(irs, name, tax);
		super.setName(name);
		super.setTax(tax);

		irs.addItemType(this);
	}

	private void checkArguments(IRS irs, String name, int tax) {
		if (name == null || name.isEmpty()) {
			throw new TaxException();
		}

		if (irs.getItemTypeByName(name) != null) {
			throw new TaxException();
		}

		if (tax < 0) {
			throw new TaxException();
		}
	}

	@Override
	public void setName(String name) {  }


	public void delete() {
		setIrs(null);

		for (Invoice inv : getInvoiceSet()) {
			inv.delete();
		}
		
		deleteDomainObject();
	}
}
