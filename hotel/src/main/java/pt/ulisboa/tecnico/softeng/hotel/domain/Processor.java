package pt.ulisboa.tecnico.softeng.hotel.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.hotel.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.hotel.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Processor extends Processor_Base {

	public Processor() {}

	public void submitBooking(Booking booking) {
		getBookingToProcessSet().add(booking);
		processInvoices();
	}

	private void processInvoices() {
		final Set<Booking> failedToProcess = new HashSet<>();
		for (final Booking booking : getBookingToProcessSet()) {
			if (!booking.isCancelled()) {
				if (booking.getPaymentReference() == null) {
					try {
						booking.setPaymentReference(BankInterface.processPayment(booking.getBuyerIban(), booking.getPrice()));
					} catch (BankException | RemoteAccessException ex) {
						failedToProcess.add(booking);
						continue;
					}
				}
				final InvoiceData invoiceData = new InvoiceData(booking.getProviderNif(), booking.getBuyerNif(), Booking.getType(), booking.getPrice(), booking.getArrival());
				try {
					booking.setInvoiceReference(TaxInterface.submitInvoice(invoiceData));
				} catch (TaxException | RemoteAccessException ex) {
					failedToProcess.add(booking);
				}
			} else {
				try {
					if (booking.getCancelledPaymentReference() == null) {
						booking.setCancelledPaymentReference(
								BankInterface.cancelPayment(booking.getPaymentReference()));
					}
					if (!booking.isCancelledInvoice()) {
						TaxInterface.cancelInvoice(booking.getInvoiceReference());
						booking.setCancelledInvoice(true);
					}
				} catch (BankException | TaxException | RemoteAccessException ex) {
					failedToProcess.add(booking);
				}

			}
		}

		getBookingToProcessSet().clear();
		getBookingToProcessSet().addAll(failedToProcess);

	}

	public void clean() {
		getBookingToProcessSet().clear();
	}

	public void delete() {
		setHotel(null);

		for (Booking booking : getBookingToProcessSet()) {
			booking.delete();
		}

		deleteDomainObject();
	}

}
