package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker extends Broker_Base {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	private final String NIFAsSeller;
	private final String NIFAsBuyer;
	private final String IBAN;
	private final Set<Client> clients = new HashSet<>();

	@Override
	public int getCounter() {
		int counter = super.getCounter() + 1;
		setCounter(counter);
		return counter;
	}

	public Broker(String code, String name, String NIFAsSeller, String NIFAsBuyer, String IBAN) {
		checkArguments(code, name, NIFAsSeller, NIFAsBuyer, IBAN);

		setCode(code);
		setName(name);

		this.NIFAsSeller = NIFAsSeller;
		this.NIFAsBuyer = NIFAsBuyer;
		this.IBAN = IBAN;

		FenixFramework.getDomainRoot().addBroker(this);
	}

	public void delete() {
		setRoot(null);

		for (Adventure adventure : getAdventureSet()) {
			adventure.delete();
		}

		for (BulkRoomBooking bulkRoomBooking : getRoomBulkBookingSet()) {
			bulkRoomBooking.delete();
		}

		deleteDomainObject();
	}

	private void checkArguments(String code, String name, String NIFAsSeller, String NIFAsBuyer, String IBAN) {
		if (code == null || code.trim().length() == 0 || name == null || name.trim().length() == 0
				|| NIFAsSeller == null || NIFAsSeller.trim().length() == 0 || NIFAsBuyer == null
				|| NIFAsBuyer.trim().length() == 0 || IBAN == null || IBAN.trim().length() == 0) {
			throw new BrokerException();
		}

		if (NIFAsSeller.equals(NIFAsBuyer)) {
			throw new BrokerException();
		}

		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}

		for (Broker broker : FenixFramework.getDomainRoot().getBrokerSet()) {
			if (broker.getNIFAsSeller().equals(NIFAsSeller) || broker.getNIFAsSeller().equals(NIFAsBuyer)
					|| broker.getNIFAsBuyer().equals(NIFAsSeller) || broker.getNIFAsBuyer().equals(NIFAsBuyer)) {
				throw new BrokerException();
			}
		}

	}

	public String getNIFAsSeller() {
		return this.NIFAsSeller;
	}

	public String getNIFAsBuyer() {
		return this.NIFAsBuyer;
	}

	public String getIBAN() {
		return this.IBAN;
	}

	public Client getClientByNIF(String NIF) {
		for (Client client : this.clients) {
			if (client.getNIF().equals(NIF)) {
				return client;
			}
		}
		return null;
	}

	public boolean drivingLicenseIsRegistered(String drivingLicense) {
		return this.clients.stream().anyMatch(client -> client.getDrivingLicense().equals(drivingLicense));
	}

	public void addClient(Client client) {
		this.clients.add(client);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(this, number, arrival, departure, this.NIFAsBuyer, this.IBAN);
		bulkBooking.processBooking();
	}
}
