package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class Booking extends Booking_Base {
	private static int counter = 0;

	private static final String SPORT_TYPE = "SPORT";
	private String paymentReference;
	private String invoiceReference;

	private boolean cancelledInvoice = false;
	private String cancelledPaymentReference = null;

	public Booking(ActivityProvider provider, ActivityOffer offer, String buyerNif, String buyerIban) {
		checkArguments(provider, offer, buyerNif, buyerIban);

		setReference(offer.getActivity().getActivityProvider().getCode() + Integer.toString(++Booking.counter));

		setActivityOffer(offer);

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
		setActivityOffer(null);

		deleteDomainObject();
	}

	public String getType() {
		return SPORT_TYPE;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public boolean isCancelledInvoice() {
		return this.cancelledInvoice;
	}

	public void setCancelledInvoice(boolean cancelledInvoice) {
		this.cancelledInvoice = cancelledInvoice;
	}

	public String getCancelledPaymentReference() {
		return this.cancelledPaymentReference;
	}

	public void setCancelledPaymentReference(String cancelledPaymentReference) {
		this.cancelledPaymentReference = cancelledPaymentReference;
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
