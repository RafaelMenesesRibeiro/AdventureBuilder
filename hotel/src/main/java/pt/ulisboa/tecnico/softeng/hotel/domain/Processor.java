package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.hotel.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Processor {

	// important to use a set to avoid double submission of the same booking when it
	// is cancelled while trying to pay or send invoice
	private final Set<Booking> bookingToProcess = new HashSet<>();

	public void submitHotelBooking(Booking booking) {
		this.bookingToProcess.add(booking);
		processInvoices();
	}

	private void processInvoices() {
		Set<Booking> failedToProcess = new HashSet<>();
		for (Booking booking : this.bookingToProcess) {
			if (!booking.isCancelled()) {
				if (booking.getPaymentReference() == null) {
					try {
						booking.setPaymentReference(
								BankInterface.processPayment(booking.getIban(), booking.getAmount()));
					} catch (HotelException | BankException | RemoteAccessException ex) {
						failedToProcess.add(booking);
						continue;
					}
				}
				InvoiceData invoiceData = new InvoiceData(booking.getNif(), booking.getHotelNif(), booking.getReference(),
						booking.getAmount(), booking.getDeparture());
				try {
					booking.setInvoiceReference(TaxInterface.submitInvoice(invoiceData));
				} catch (RemoteAccessException | TaxException e) {
					failedToProcess.add(booking);
				}
			} else {
				try {
					if (booking.getCancellation() != null) {
						BankInterface.cancelPayment(booking.getPaymentReference());
					}
					TaxInterface.cancelInvoice(booking.getInvoiceReference());
				} catch (TaxException | BankException | RemoteAccessException e) {
					failedToProcess.add(booking);
				}
			}
		}

		this.bookingToProcess.clear();
		this.bookingToProcess.addAll(failedToProcess);

	}

	public void clean() {
		this.bookingToProcess.clear();
	}

}
