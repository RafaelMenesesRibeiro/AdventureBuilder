package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

@RunWith(JMockit.class)
public class RentVehicleStateMethodTest {
	private static final String NIF = "123456789";
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
	private static final String VEHICLE_CONFIRMATION = "VehileConfirmation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Injectable
	private Client client;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.client, this.broker, arrival, departure, AGE, IBAN, AMOUNT);
		this.adventure.setState(State.RENT_VEHICLE);
	}

	@Test
	public void successRentVehicle(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = VEHICLE_CONFIRMATION;

				broker.getNIFBuyer();
				this.result = NIF;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
		Assert.assertNotNull(this.adventure.getVehicleConfirmation());
		Assert.assertNull(this.adventure.getVehicleCancellation());
	}

	@Test
	public void carException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new CarException();

				broker.getNIFBuyer();
				this.result = NIF;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
		Assert.assertNull(this.adventure.getVehicleConfirmation());
		Assert.assertNull(this.adventure.getVehicleCancellation());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new RemoteAccessException();

				broker.getNIFBuyer();
				this.result = NIF;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new RemoteAccessException();
				this.times = RentVehicleState.MAX_REMOTE_ERRORS;

				broker.getNIFBuyer();
				this.result = NIF;
				this.times = RentVehicleState.MAX_REMOTE_ERRORS;
			}
		};

		for (short i = 0; i < RentVehicleState.MAX_REMOTE_ERRORS; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new RemoteAccessException();
				this.times = RentVehicleState.MAX_REMOTE_ERRORS - 1;

				broker.getNIFBuyer();
				this.result = NIF;
				this.times = RentVehicleState.MAX_REMOTE_ERRORS - 1;
			}
		};

		for (short i = 0; i < BookRoomState.MAX_REMOTE_ERRORS - 1; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}

	@Test
	public void fiveRemoteAccessExceptionOneSuccess(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new Delegate() {
					short i = 0;
					public String delegate() {
						if (this.i++ < 5) { throw new RemoteAccessException(); } 
						else { return VEHICLE_CONFIRMATION; }
					}
				};
				this.times = 6;

				broker.getNIFBuyer();
				this.result = NIF;
				this.times = 6;
			}
		};

		for (short i = 0; i < 5 + 1; i++) { this.adventure.process(); }
		
		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneCarException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar(arrival, departure, NIF, IBAN);
				this.result = new Delegate() {
					short i = 0;
					public String delegate() {
						if (this.i++ < 1) { throw new RemoteAccessException(); }
						else { throw new CarException(); }
					}
				};
				this.times = 2;

				broker.getNIFBuyer();
				this.result = NIF;
				this.times = 2;
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
}
