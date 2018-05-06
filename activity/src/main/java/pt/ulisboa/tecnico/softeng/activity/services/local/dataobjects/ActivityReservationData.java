package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.activity.domain.Booking;

public class ActivityReservationData {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate cancellationDate;
	private double price;
	private String reference;
	private String cancellation;
	private String name;
	private String code;
	private String paymentReference;
	private String invoiceReference;
	private String buyerNif;
	private String buyerIban;
	private int age;

	public ActivityReservationData(ActivityProvider provider, ActivityOffer offer, Booking booking) {
		this.reference = booking.getReference();
		this.cancellation = booking.getCancel();
		this.name = provider.getName();
		this.code = provider.getCode();
		this.begin = offer.getBegin();
		this.end = offer.getEnd();
		this.cancellationDate = booking.getCancellationDate();
		this.price = offer.getPrice();
		this.paymentReference = booking.getPaymentReference();
		this.invoiceReference = booking.getInvoiceReference();
		this.buyerNif = booking.getBuyerNif();
		this.buyerIban = booking.getIban();
	}

	public ActivityReservationData() {

	}

	public ActivityReservationData(Booking booking) {
		this.reference = booking.getReference();
		this.cancellation = booking.getCancel();
		this.name = booking.getActivityOffer().getActivity().getActivityProvider().getName();
		this.code = booking.getActivityOffer().getActivity().getActivityProvider().getCode();
		this.begin = booking.getActivityOffer().getBegin();
		this.end = booking.getActivityOffer().getEnd();
		this.cancellationDate = booking.getCancellationDate();
		this.price = booking.getAmount();
		this.paymentReference = booking.getPaymentReference();
		this.invoiceReference = booking.getInvoiceReference();
		this.buyerNif = booking.getBuyerNif();
		this.buyerIban = booking.getIban();
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCancellation() {
		return this.cancellation;
	}

	public void setCancellation(String cancellation) {
		this.cancellation = cancellation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getBegin() {
		return this.begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}

	public void setCancellationDate(LocalDate date) {
		this.cancellationDate = date;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public void setPaymentReference(String reference) {
		this.paymentReference = reference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public void setInvoiceReference(String reference) {
		this.invoiceReference = reference;
	}

	public String getBuyerNif() {
		return this.buyerNif;
	}

	public void setBuyerNif(String buyerNif) {
		this.buyerNif = buyerNif;
	}

	public String getBuyerIban() {
		return this.buyerIban;
	}

	public void setBuyerIban(String buyerIban) {
		this.buyerIban = buyerIban;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
