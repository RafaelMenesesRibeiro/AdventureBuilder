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
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;

public class AdventureProcessMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private static final String NIF_SELLER = "987654321";
	private Broker broker;
	private Client client;
	private String IBAN;
	private Car car;

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

		new ItemType(IRS.getIRS(), "Item Type", 23);
		new Seller(IRS.getIRS(), broker.getNIFSeller(), "joana", "asdfasdasd");
		new Buyer(IRS.getIRS(), "123444661", "antonio", "asdasd");
	
		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 30);
		new ActivityOffer(activity, this.begin, this.begin, 30);

		RentACar rentACar = new RentACar("John's cars", "123123123",  "BK01987654301");
		new Car("12-34-AA", 30, 2.4, rentACar);


	}

	@Test
	public void success() {
		Adventure adventure = new Adventure(this.client, this.broker, this.begin, this.end, 20, this.IBAN, 300);
		

		
		adventure.process();

		adventure.process();


		adventure.process();


		adventure.process();

		assertEquals(Adventure.State.CONFIRMED, adventure.getState());
		assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
		assertNotNull(adventure.getVehicleConfirmation());
	}

	@Test
	public void successNoHotelBooking() {
		Adventure adventure = new Adventure(this.client, this.broker, this.begin, this.begin, 20, this.IBAN, 300);

		adventure.process();

		assertEquals(Adventure.State.PROCESS_PAYMENT, adventure.getState());
		//assertNotNull(adventure.getPaymentConfirmation());
		//assertNotNull(adventure.getActivityConfirmation());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
		RentACar.clear();
		Vehicle.clear();
		IRS.getIRS().clearAll();
		Client.clients.clear();
	}
}
