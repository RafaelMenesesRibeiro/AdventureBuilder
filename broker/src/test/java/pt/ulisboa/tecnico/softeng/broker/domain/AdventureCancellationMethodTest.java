package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureCancellationMethodTest {
	private static final int AGE = 20;
	private static final int AMOUNT = 300;
	private static final String IBAN = "BK011234567";
	private Broker broker;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");
	}

	@Test
	public void successRoomCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setRoomConfirmation("test success");

		Assert.assertTrue(adventure.cancelRoom());
	}

	@Test
	public void failureRoomCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		Assert.assertFalse(adventure.cancelRoom());
	}

	@Test
	public void failureRoomCancellationDueToCancelled() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setRoomCancellation("test fail");
		adventure.setRoomConfirmation("test fail.");

		Assert.assertFalse(adventure.cancelRoom());
	}

	@Test
	public void successActivityCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setActivityConfirmation("test success");

		Assert.assertTrue(adventure.cancelActivity());

	}

	@Test
	public void failureActivityCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		Assert.assertFalse(adventure.cancelActivity());
	}


	@Test
	public void failureActivityCancellationDueToCancelled() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setActivityConfirmation("test fail..");
		adventure.setActivityCancellation("test fail....");

		Assert.assertFalse(adventure.cancelActivity());
	}

	@Test
	public void successPaymentCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setPaymentConfirmation("test success.....");

		Assert.assertTrue(adventure.cancelPayment());

	}

	@Test
	public void failurePaymentCancellation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		Assert.assertFalse(adventure.cancelPayment());

	}


	@Test
	public void failurePaymentCancellationDueToConfirmation() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);

		adventure.setPaymentConfirmation("...test fail");
		adventure.setPaymentCancellation("test fail...");

		Assert.assertFalse(adventure.cancelPayment());
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}


}
