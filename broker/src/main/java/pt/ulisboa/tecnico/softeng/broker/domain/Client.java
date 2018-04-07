package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Client {
	public final String NIF;
	public int age;
	public String IBAN;

	public static Set<Client> clients = new HashSet<>();

	public Client(String IBAN, int age, String nifA) {
		this.checkIBAN(IBAN);
		this.IBAN = IBAN;
		this.checkAge(age);
		this.age = age;
		this.checkNIF(nifA);
		this.NIF = nifA;
		clients.add(this);
	}

	private void checkIBAN(String IBAN) {
		if (IBAN == null || IBAN.trim().length() == 0) {
			throw new BrokerException();
		}
	}

	private void checkAge(int age) {
		if (age < 0) {
			throw new BrokerException();
		}
	}

	private void checkNIF(String nifA) {
		if (nifA == null || nifA.trim().length() != 9) {
			throw new BrokerException();
		}
		for (Client client : clients) {
			if (client.getNIF().equals(nifA)) {
				throw new BrokerException();
			}
		}
	}

	public String getNIF(){
		return this.NIF;
	}

	public int getAge(){
		return this.age;
	}

	public String getIBAN(){
		return this.IBAN;
	}
}
