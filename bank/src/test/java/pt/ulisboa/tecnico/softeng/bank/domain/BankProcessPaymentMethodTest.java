package pt.ulisboa.tecnico.softeng.bank.domain;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class BankProcessPaymentMethodTest {
	private Bank bank;
	private Account account;
	private String reference;

	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.reference = this.account.deposit(100);
	}

	@Test
	public void success() {
		String iban = this.account.getIBAN();
		String t = Bank.processPayment(iban, 52);

		assertEquals(48, this.account.getBalance());

	}

	@After
	public void tearDown() {
		Bank.banks.clear();
	}

	@Test(expected = BankException.class)
	public void emptyIBAN() {
		Bank.processPayment("", 52);

	}

	@Test(expected = BankException.class)
	public void nullIBAN() {
		Bank.processPayment(null, 52);
	}

	@Test(expected = BankException.class)
	public void negativeAmmount() {
		String iban = this.account.getIBAN();
		Bank.processPayment(iban, -32);
	}

	@Test(expected = BankException.class)
	public void zeroAmmount() {
		String iban = this.account.getIBAN();
		Bank.processPayment(iban, 0);
	}

	@Test(expected = BankException.class)
	public void morethanAmmount() {
		String iban = this.account.getIBAN();
		Bank.processPayment(iban, this.account.getBalance() + 1);
	}


	@Test(expected = BankException.class)
	public void wrongIBAN() {
		Bank.processPayment("1234", 52);
	}

}

