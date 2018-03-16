package pt.ulisboa.tecnico.softeng.tax.exception;

public final class TaxException extends RuntimeException {
	public static final int NIF_SIZE = 9;
	public static final int MIN_YEAR = 1970;
	public static final int MIN_VALUE = 0;
	public static final int MIN_TAX = 0;
	
	public TaxException() {
		super();
	}

	public TaxException(String message) {
		super(message);
	}
}
