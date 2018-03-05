package pt.ulisboa.tecnico.softeng.car.exception;

public class CarException extends RuntimeException {
  public BrokerException() {
    super();
  }

  public CarException(String message) {
    super(message);
  }
}
