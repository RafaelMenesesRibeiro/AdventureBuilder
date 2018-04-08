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
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Car;

@RunWith(JMockit.class)
public class ProcessPaymentStateProcessMethodTest {
	private static final String NIF = "123456789";
	private static final String NIF_CLIENT = "123456781";
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final String PAYMENT_CONFIRMATION = "PaymentConfirmation";
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Adventure adventure;

	@Injectable
	private Broker broker;

	@Injectable
	private Client client;

	@Before
	public void setUp() {
		this.adventure = new Adventure(this.client, this.broker, this.begin, this.end, 20, IBAN, AMOUNT);
		this.adventure.setState(State.PROCESS_PAYMENT);
	}

	@Test
	public void success(@Mocked final BankInterface bankInterface, @Mocked TaxInterface taxInterface) {

		RentACar rentACar = new RentACar("John's cars", "123123123",  "BK01987654301");
		Vehicle v = new Car("12-34-AA", 30, 2.4, rentACar);
		
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = PAYMENT_CONFIRMATION;

				TaxInterface.submitInvoice(anyString, anyString, anyString, AMOUNT, new LocalDate());
				this.result = "invoice ref";

				adventure.getIBAN();
				this.result = IBAN;

				broker.getNIFSeller();
				this.result = NIF;


				client.getNIF();
				this.result = NIF_CLIENT;

				adventure.getAmount();
				this.result = AMOUNT;
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void bankException(@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new BankException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void singleRemoteAccessException(@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void maxRemoteAccessException(@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

	@Test
	public void maxMinusOneRemoteAccessException(@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
	}

	@Test
	public void twoRemoteAccessExceptionOneSuccess(@Mocked final BankInterface bankInterface, 
	@Mocked final TaxInterface taxInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 2) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return PAYMENT_CONFIRMATION;
						}
					}
				};
				this.times = 3;

				TaxInterface.submitInvoice(anyString, anyString, anyString, AMOUNT, new LocalDate());
				this.result = "invoice ref";

				adventure.getIBAN();
				this.result = IBAN;

				broker.getNIFSeller();
				this.result = NIF;


				client.getNIF();
				this.result = NIF_CLIENT;

				adventure.getAmount();
				this.result = AMOUNT;
			}
		};

		

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CONFIRMED, this.adventure.getState());
	}

	@Test
	public void oneRemoteAccessExceptionOneBankException(@Mocked final BankInterface bankInterface) {
		new Expectations() {
			{
				BankInterface.processPayment(IBAN, AMOUNT);

				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new BankException();
						}
					}
				};
				this.times = 2;

			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}

}