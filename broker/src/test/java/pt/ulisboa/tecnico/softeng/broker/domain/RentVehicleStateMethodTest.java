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
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
	private static final String VEHICLE_CONFIRMATION = "VehileConfirmation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.broker, arrival, departure, AGE, IBAN, AMOUNT);
		this.adventure.setState(State.RENT_VEHICLE);
	}

	@Test
	public void successRentVehicle(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.reserveCar();
				this.result = VEHICLE_CONFIRMATION;
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
				CarInterface.reserveCar();
				this.result = new CarException();
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
				CarInterface.reserveCar();
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}
}