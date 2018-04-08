package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.broker.domain.Client;

public class AdventureProcessMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private static final String NIF_SELLER = "987654321";
	private static final boolean CAR_NEEDED = true;
	private static final boolean NO_CAR_NEEDED = false;
	private Broker broker;
	private Client client;
	private String IBAN;

	@Before
	public void setUp() {
		this.client = new Client("123", 20, "123444661");
		this.broker = new Broker("BR01", "eXtremeADVENTURE", "123456789", "987654321", "IBAN");

		Bank bank = new Bank("Money", "BK01");
		
		Account account = new Account(bank, new pt.ulisboa.tecnico.softeng.bank.domain.Client(bank, "António"));
		this.IBAN = account.getIBAN();
		account.deposit(1000);

		Hotel hotel = new Hotel("XPTO123", "Paris", "123456788", "IBAC", 200, 300);
		new Room(hotel, "01", Type.SINGLE);

		Vehicle vehicle = new Car("22-33-HZ", 10, 10.0, new RentACar("John's Cars", NIF_SELLER, this.IBAN));

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 30);
		new ActivityOffer(activity, this.begin, this.begin, 30);
	}

	@Test
	public void success() {
		Adventure adventure = new Adventure(this.client, this.broker, this.begin, this.end, 20, this.IBAN, 300, CAR_NEEDED);
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
		assertNotNull(adventure.getVehicleConfirmation());
		assertNotNull(adventure.getVehicleConfirmation());
	}

	@Test
	public void successNoHotelBooking() {
		Adventure adventure = new Adventure(this.client, this.broker, this.begin, this.begin, 20, this.IBAN, 300, NO_CAR_NEEDED);

		adventure.process();

		assertEquals(Adventure.State.PROCESS_PAYMENT, adventure.getState());
		//assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
		RentACar.clear();
		Vehicle.clear();
		Client.clients.clear();
	}
}
