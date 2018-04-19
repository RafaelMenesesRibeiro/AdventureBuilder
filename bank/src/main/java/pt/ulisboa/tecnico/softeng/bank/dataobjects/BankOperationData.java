package pt.ulisboa.tecnico.softeng.bank.dataobjects;

import org.joda.time.DateTime;

import pt.ulisboa.tecnico.softeng.bank.domain.Operation;

public class BankOperationData {
	private String reference;
	private String type;
	private String IBAN;
	private double value;
	private DateTime time;

	public BankOperationData() {
	}

	public BankOperationData(Operation operation) {
		this.reference = operation.getReference();
		this.type = operation.getType().name();
		this.IBAN = operation.getAccount().getIBAN();
		this.value = operation.getValue();
		this.time = operation.getTime();
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIBAN() {
		return this.IBAN;
	}

	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public DateTime getTime() {
		return this.time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}
}
