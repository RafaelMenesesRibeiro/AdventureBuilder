package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Client {
	public final String NIF;
	public int age;
	public String IBAN;
	public String drivingLicense;

	public static Set<Client> clients = new HashSet<>();

	public Client(String IBAN, int age, String NIF, String drivingLicense) {
		this.checkIBAN(IBAN);
		this.IBAN = IBAN;
		this.checkAge(age);
		this.age = age;
		this.checkNIF(NIF);
		this.NIF = NIF;
		this.checkDrivingLicense(drivingLicense);
		this.drivingLicense = drivingLicense;
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
		if (NIF == null || NIF.trim().length() != 9) {
			throw new BrokerException();
		}
		for (Client client : clients) {
			if (client.getNIF().equals(NIF)) {
				throw new BrokerException();
			}
		}
	}

	public void checkDrivingLicense(String drivingLicense) {
		if (drivingLicense == null || !drivingLicense.matches("^[a-zA-Z]+\\d+$")) {
			throw new BrokerException();
		}
		for (Client client : clients) {
			if (client.getDrivingLicense().equals(drivingLicense)) {
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

	public String getDrivingLicense() {
		return this.drivingLicense;
	}
}
