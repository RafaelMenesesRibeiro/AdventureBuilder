package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.Assert;

import pt.ulisboa.tecnico.softeng.broker.domain.BulkRoomBooking;

public class BulkRoomBookingConstructorMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Broker broker;
	private String IBAN;

	@Test
	public void success() {
		BulkRoomBooking bulk = new BulkRoomBooking(1, this.begin, this.end);
		
		Assert.assertEquals(1, bulk.getNumber());
		Assert.assertEquals(this.begin, bulk.getArrival());
		Assert.assertEquals(this.end, bulk.getDeparture());	
	}

	/*


	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");

		Bank bank = new Bank("Money", "BK01");
		Client client = new Client(bank, "António");
		Account account = new Account(bank, client);
		this.IBAN = account.getIBAN();
		account.deposit(1000);

		Hotel hotel = new Hotel("XPTO123", "Paris");
		new Room(hotel, "01", Type.SINGLE);

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end);
		new ActivityOffer(activity, this.begin, this.begin);
	}

	@Test
	public void successRoomCancellation() {
		Adventure adventure	= new Adventure(this.broker, this.begin, this.begin, 20, this.IBAN, 300);
		adventure.setRoomCancellation("cancelled");
		assertTrue(adventure.cancelRoom());
	}

	@Test
	public void successActivityCancellation() {
		Adventure adventure	= new Adventure(this.broker, this.begin, this.begin, 20, this.IBAN, 300);
		adventure.setActivityCancellation(null);
		adventure.setActivityCancellation("not null");

		assertEquals(null, adventure.getActivityCancellation());
		assertNotEquals(null, adventure.getActivityConfirmation());
		assertEquals(true, adventure.cancelActivity());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
	}

	*/
}
