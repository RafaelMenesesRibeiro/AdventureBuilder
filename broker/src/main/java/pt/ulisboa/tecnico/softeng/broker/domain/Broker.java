package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	public static Set<Broker> brokers = new HashSet<>();

	private final String code;
	private final String name;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();
	private final String NIFBuyer;
	private final String NIFSeller;


	public Broker(String code, String name, String NIFBuyer, String NIFSeller) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;
		checkNIFBuyer(NIFBuyer);
		this.NIFBuyer = NIFBuyer;
		checkNIFSeller(NIFSeller);
		this.NIFSeller = NIFSeller;
		Broker.brokers.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new BrokerException();
		}
	}

	private void checkNIFBuyer(String NIFBuyer) {
		if (NIFBuyer == null || NIFBuyer.trim().length() == 0 || NIFBuyer.trim().length() != 9) {
			throw new BrokerException();
		}
		for (Broker broker : Broker.brokers) {
			if (broker.getNIFBuyer().equals(NIFBuyer)) {
				throw new BrokerException();
			}
		}
	}

	private void checkNIFSeller(String NIFSeller) {
		if (NIFSeller == null || NIFSeller.trim().length() == 0 || NIFSeller.trim().length() != 9) {
			throw new BrokerException();
		}
		if (NIFSeller.equals(this.NIFBuyer)) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getNIFSeller().equals(NIFSeller)) {
				throw new BrokerException();
			}
		}
	}
	String getCode() {
		return this.code;
	}

	String getName() {
		return this.name;
	}

	String getNIFBuyer(){
		return this.NIFBuyer;
	}

	String getNIFSeller(){
		return this.NIFSeller;
	}

	public int getNumberOfAdventures() {
		return this.adventures.size();
	}

	public void addAdventure(Adventure adventure) {
		this.adventures.add(adventure);
	}

	public boolean hasAdventure(Adventure adventure) {
		return this.adventures.contains(adventure);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(number, arrival, departure);
		this.bulkBookings.add(bulkBooking);
		bulkBooking.processBooking();
	}

}
