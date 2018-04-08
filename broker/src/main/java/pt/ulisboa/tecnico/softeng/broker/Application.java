package pt.ulisboa.tecnico.softeng.broker;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;
import pt.ulisboa.tecnico.softeng.broker.domain.Broker;

public class Application {

	public static void main(String[] args) {
		System.out.println("Adventures!");

		Bank bank = new Bank("MoneyPlus", "BK01");
		pt.ulisboa.tecnico.softeng.broker.domain.Client client = new pt.ulisboa.tecnico.softeng.broker.domain.Client("123", 20, "123456789", "br123");
		Account account = new Account(bank, new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "Mafalda Gaspar"));
		account.deposit(1000);

		Broker broker = new Broker("BR01", "Fun", "123456789", "98765432", "IBAN");
		Adventure adventure = new Adventure(client, broker, new LocalDate(), new LocalDate(), 33, account.getIBAN(), 50, false);

		adventure.process();

		System.out.println("Your payment reference is " + adventure.getPaymentConfirmation() + " and you have "
				+ account.getBalance() + " euros left in your account");
	}

}
