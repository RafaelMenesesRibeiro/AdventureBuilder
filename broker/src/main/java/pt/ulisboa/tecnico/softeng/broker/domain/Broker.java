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
	private final String nifbuyer;
	private final String nifseller;


	public Broker(String code, String name, String nifbuyer, String nifseller) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;
		checkNIFBuyer(nifbuyer);
		this.nifbuyer = nifbuyer;
		checkNIFSeller(nifseller);
		this.nifseller = nifseller;
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

	private void checkNIFBuyer(String nifbuyer) {
		if (nifbuyer == null || nifbuyer.trim().length() == 0 || nifbuyer.trim().length() != 9) {
			throw new BrokerException();
		}
		for (Broker broker : Broker.brokers) {
			if (broker.getnifbuyer().equals(nifbuyer)) {
				throw new BrokerException();
			}
		}
	}

	private void checkNIFSeller(String nifseller) {
		if (nifseller == null || nifseller.trim().length() == 0 || nifseller.trim().length() != 9) {
			throw new BrokerException();
		}
		if (nifseller.equals(this.nifbuyer)) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getnifseller().equals(nifseller)) {
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

	String getnifbuyer(){
		return this.nifbuyer;
	}

	String getnifseller(){
		return this.nifseller;
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
