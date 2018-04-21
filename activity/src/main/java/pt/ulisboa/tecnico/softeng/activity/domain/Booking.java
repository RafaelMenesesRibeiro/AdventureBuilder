package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class Booking extends Booking_Base {

	private static int counter = 0;
	private static final String SPORT_TYPE = "SPORT";

	public Booking(ActivityProvider provider, ActivityOffer offer, String buyerNif, String buyerIban) {
		checkArguments(provider, offer, buyerNif, buyerIban);

		setReference(offer.getActivity().getActivityProvider().getCode() + Integer.toString(++Booking.counter));
		setActivityOffer(offer);

		setCancelledInvoice(false);
		setCancelledPaymentReference(null);

		super.setProviderNIF(provider.getNIF());
		super.setNIF(buyerNif);
		super.setIBAN(buyerIban);
		super.setAmount(offer.getPrice());
		super.setDate(offer.getBegin());

		offer.addBooking(this);
	}

	@Override
	public void setProviderNIF(String NIF) {
		// Nif is final and can't be changed - Do nothing;
	}

	@Override
	public void setNIF(String NIF) {
		// Nif is final and can't be changed - Do nothing;
	}

	@Override
	public void setIBAN(String IBAN) {
		// Iban is final and can't be changed - Do nothing;
	}

	@Override
	public void setAmount(double IBAN) {
		// Amount is final and can't be changed - Do nothing;
	}

	@Override
	public void setDate(LocalDate date) {
		// Date is final and can't be changed - Do nothing;
	}

	public String getType() {
		return SPORT_TYPE;
	}

	public boolean isCancelledInvoice() {
		return getCancelledInvoice();
	}

	private void checkArguments(ActivityProvider provider, ActivityOffer offer, String buyerNIF, String buyerIban) {
		if (provider == null || offer == null || buyerNIF == null || buyerNIF.trim().length() == 0 || buyerIban == null
				|| buyerIban.trim().length() == 0) {
			throw new ActivityException();
		}

		if (offer.getCapacity() == offer.getNumberActiveOfBookings()) {
			throw new ActivityException();
		}
	}

	public void delete() {
		setProcessor(null);
		setActivityOffer(null);

		deleteDomainObject();
	}

	public String cancel() {
		setCancel("CANCEL" + getReference());
		setCancellationDate(new LocalDate());

		getActivityOffer().getActivity().getActivityProvider().getProcessor().submitBooking(this);

		return getCancel();
	}

	public boolean isCancelled() {
		return getCancel() != null;
	}

}
