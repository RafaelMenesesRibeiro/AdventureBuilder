package pt.ulisboa.tecnico.softeng.broker.car;

public class CarException extends RuntimeException {
  public BrokerException() {
    super();
  }

  public CarException(String message) {
    super(message);
  }
}
