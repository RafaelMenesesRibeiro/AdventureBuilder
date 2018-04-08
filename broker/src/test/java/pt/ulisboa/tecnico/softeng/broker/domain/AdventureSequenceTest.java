package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;

@RunWith(JMockit.class)
public class AdventureSequenceTest {
	private static final String NIF = "123456789";
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
	private static final boolean CAR_NEEDED = true;
	private static final boolean NO_CAR_NEEDED = false;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private static final String PAYMENT_CANCELLATION = "PaymentCancellation";
	private static final String ACTIVITY_CONFIRMATION = "ActivityConfirmation";
	private static final String ACTIVITY_CANCELLATION = "ActivityCancellation";
	private static final String ROOM_CONFIRMATION = "RoomConfirmation";
	private static final String ROOM_CANCELLATION = "RoomCancellation";
	private static final String VEHICLE_CONFIRMATION = "VehileConfirmation";
	private static final String VEHICLE_CANCELLATION = "VehicleCancellation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);

	@Injectable
	private Broker broker;

	@Injectable
	private Client client;


	@Test
	public void successSequenceOne(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = VEHICLE_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);

				ActivityInterface.getActivityReservationData(ACTIVITY_CONFIRMATION);

				HotelInterface.getRoomBookingData(ROOM_CONFIRMATION);

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceTwo(@Mocked final HotelInterface roomInterface,
			@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);

				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();

		Assert.assertEquals(State.RENT_VEHICLE, adventure.getState());
	}

	@Test
	public void successSequenceThree(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, NO_CAR_NEEDED);

		
		adventure.process();
		adventure.process();
		adventure.process();
		
		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceFour(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, arrival, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				broker.getNIFBuyer();
				this.result = NIF;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, arrival, AGE, IBAN, AMOUNT, NO_CAR_NEEDED);

		
		adventure.process();
		adventure.process();
		
		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void successSequenceFive(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, arrival, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				CarInterface.reserveCar(arrival, arrival, NIF, IBAN);
				this.result = VEHICLE_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				broker.getNIFBuyer();
				this.result = NIF;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, arrival, AGE, IBAN, AMOUNT, CAR_NEEDED);

		
		adventure.process();
		adventure.process();
		adventure.process();
		
		Assert.assertEquals(State.CONFIRMED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceOne(@Mocked final ActivityInterface activityInterface) {
		new Expectations() {
			{
				ActivityInterface.reserveActivity(arrival, arrival, AGE, NIF, IBAN);
				this.result = new ActivityException();

				broker.getNIFBuyer();
				this.result = NIF;

			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, arrival, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceTwo(@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = new HotelException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;



			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceThree(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, NIF, IBAN);


				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new CarException();	

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;


				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		Assert.assertEquals(State.CANCELLED, adventure.getState());

	}

	@Test
	public void unsuccessSequenceFour(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, anyString, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = VEHICLE_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;	

				CarInterface.cancelRenting(VEHICLE_CONFIRMATION);
				this.result = VEHICLE_CANCELLATION;

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@Test
	public void unsuccessSequenceFive(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface, 
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, anyString, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = VEHICLE_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				BankInterface.getOperationData(PAYMENT_CONFIRMATION);
				this.result = new BankException();
				this.times = ConfirmedState.MAX_BANK_EXCEPTIONS;

				BankInterface.cancelPayment(PAYMENT_CONFIRMATION);
				this.result = PAYMENT_CANCELLATION;

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;

				CarInterface.cancelRenting(VEHICLE_CONFIRMATION);
				this.result = VEHICLE_CANCELLATION;

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();

		for (int i = 0; i < ConfirmedState.MAX_BANK_EXCEPTIONS; i++) {
			adventure.process();
		}

		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}	

	@Test
	public void unsuccessSequenceSix(@Mocked final BankInterface bankInterface,
			@Mocked final ActivityInterface activityInterface, @Mocked final HotelInterface roomInterface,
			@Mocked final CarInterface carInterface) {
		new Expectations() {
			{

				ActivityInterface.reserveActivity(arrival, departure, AGE, anyString, IBAN);
				this.result = ACTIVITY_CONFIRMATION;

				HotelInterface.reserveRoom(Type.SINGLE, arrival, departure, NIF, IBAN);
				this.result = ROOM_CONFIRMATION;

				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();

				ActivityInterface.cancelReservation(ACTIVITY_CONFIRMATION);
				this.result = ACTIVITY_CANCELLATION;

				HotelInterface.cancelBooking(ROOM_CONFIRMATION);
				this.result = ROOM_CANCELLATION;	

				broker.getNIFBuyer();
				this.result = NIF;

				broker.getIBAN();
				this.result = IBAN;
			}
		};

		Adventure adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT, NO_CAR_NEEDED);

		adventure.process();
		adventure.process();
		adventure.process();
		adventure.process();
		Assert.assertEquals(State.CANCELLED, adventure.getState());
	}

	@After
	public void tearDown() {

		RentACar.clear();
		Client.clients.clear();
		Broker.brokers.clear();

	}
}