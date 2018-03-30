package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentVehicleState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 10;

	@Override
	public State getState() {
		return State.RENT_VEHICLE;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			adventure.setVehicleConfirmation(CarInterface.reserveCar());
		}
		catch (CarException ce) {
			adventure.setState(State.UNDO);
			return;
		}
		catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) { adventure.setState(State.UNDO); }
			return;
		}

		adventure.setState(State.CONFIRMED);
	}

}
