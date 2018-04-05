package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Client {
	public final String NIF;
	public int age;
	public String IBAN;

	public static Set<Client> clients = new HashSet<>();

	public Client(String IBAN, int age, String NIF) {
		checkIBAN(IBAN);
		this.IBAN = IBAN;
		checkAge(age);
		this.age = age;
		checkNIF(NIF);
		this.NIF = NIF;
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

	private void checkNIF(String NIF) {
		if (NIF == null || NIF.trim().length() == 0 || NIF.trim().length() != 9) {
			throw new BrokerException();
		}
		for (Client client : clients) {
			if (client.getNIF().equals(NIF)) {
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
