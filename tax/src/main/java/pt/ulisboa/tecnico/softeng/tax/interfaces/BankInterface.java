package pt.ulisboa.tecnico.softeng.tax.interfaces;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;

public class BankInterface {
	public static String processPayment(String IBAN, double amount) {
		return Bank.processPayment(IBAN, amount);
	}

	public static String cancelPayment(String reference) {
		return Bank.cancelPayment(reference);
	}
}
