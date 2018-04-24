package pt.ulisboa.tecnico.softeng.hotel.services.remote.exceptions;

public class BankException extends RuntimeException {
	public BankException() {
		super();
	}

	public BankException(String message) {
		super(message);
	}
}
