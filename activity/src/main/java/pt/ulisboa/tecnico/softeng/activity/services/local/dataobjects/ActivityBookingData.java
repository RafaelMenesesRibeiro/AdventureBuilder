package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;

public class ActivityBookingData {
	private static final String SPORT_TYPE = "SPORT";
	private ActivityOffer offer;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;					 // offer begin
	private String reference;				 // activity provider code + integer
	private String providerNif;			 // activity provider nif
	private String buyerNif;				 // GET input
	private String buyerIban;        // GET input
	private String type;						 // activity type
	private int amount;				 			 // offer price
	private boolean cancelled;			 // default is false

	public ActivityBookingData() {
	}

	public ActivityBookingData(ActivityOffer offer, String buyerNif, String buyerIban) {
		this.offer = offer;
		this.date = offer.getBegin();
		this.reference = offer.getActivity().getActivityProvider().getCode() + Integer.toString(provider.getCounter())
		this.providerNif = offer.getActivity().getActivityProvider().getNif();
		this.buyerNif = buyerNif;
		this.buyerIban = buyerIban;
		this.type = SPORT_TYPE;
		this.amount = offer.getAmount();
		this.cancelled = false;
	}

	// @TODO Getters and Setters;

}
