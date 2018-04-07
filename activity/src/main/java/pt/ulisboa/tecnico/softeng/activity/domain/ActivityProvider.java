package pt.ulisboa.tecnico.softeng.activity.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.dataobjects.ActivityReservationData;
import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityProvider {
	public static Set<ActivityProvider> providers = new HashSet<>();

	static final int CODE_SIZE = 6;

	private final String name;
	private final String code;
	private final String NIF;
	private final String IBAN;
	private final Set<Activity> activities = new HashSet<>();

	private final Processor processor = new Processor();

	public ActivityProvider(String code, String name, String NIF, String IBAN) {
		checkArguments(code, name, NIF, IBAN);

		this.code = code;
		this.name = name;
		this.NIF = NIF;
		this.IBAN = IBAN;

		ActivityProvider.providers.add(this);
	}

	private void checkArguments(String code, String name, String NIF, String IBAN) {
		if (code == null || name == null || code.trim().equals("") || name.trim().equals("") || NIF == null
				|| NIF.trim().length() == 0 || IBAN == null || IBAN.trim().length() == 0) {
			throw new ActivityException();
		}

		if (code.length() != ActivityProvider.CODE_SIZE) {
			throw new ActivityException();
		}

		for (ActivityProvider activityProvider : providers) {
			if (activityProvider.getCode().equals(code) || activityProvider.getName().equals(name)) {
				throw new ActivityException();
			}
		}

		for (ActivityProvider activityProvider : providers) {
			if (activityProvider.getNif().equals(NIF)) {
				throw new ActivityException();
			}
		}
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getNif() {
		return this.NIF;
	}

	public String getIban() {
		return this.IBAN;
	}

	public Processor getProcessor() {
		return this.processor;
	}

	int getNumberOfActivities() {
		return this.activities.size();
	}

	void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public List<ActivityOffer> findOffer(LocalDate begin, LocalDate end, int age) {
		List<ActivityOffer> result = new ArrayList<>();
		for (Activity activity : this.activities) {
			result.addAll(activity.getOffers(begin, end, age));
		}
		return result;
	}

	private Booking getBooking(String reference) {
		for (Activity activity : this.activities) {
			Booking booking = activity.getBooking(reference);
			if (booking != null) {
				return booking;
			}
		}
		return null;
	}

	private static Booking getBookingByReference(String reference) {
		for (ActivityProvider provider : ActivityProvider.providers) {
			Booking booking = provider.getBooking(reference);
			if (booking != null) {
				return booking;
			}
		}
		return null;
	}

	public static String reserveActivity(LocalDate begin, LocalDate end, int age, String NIF, String IBAN) {
		List<ActivityOffer> offers;
		for (ActivityProvider provider : ActivityProvider.providers) {
			offers = provider.findOffer(begin, end, age);
			if (!offers.isEmpty()) {
				Booking booking = new Booking(provider, offers.get(0), NIF, IBAN);
				provider.getProcessor().submitBooking(booking);
				return booking.getReference();
			}
		}
		throw new ActivityException();
	}

	public static String cancelReservation(String reference) {
		Booking booking = getBookingByReference(reference);
		if (booking != null) {
			return booking.cancel();
		}
		throw new ActivityException();
	}

	public static ActivityReservationData getActivityReservationData(String reference) {
		for (ActivityProvider provider : ActivityProvider.providers) {
			for (Activity activity : provider.activities) {
				for (ActivityOffer offer : activity.getOffers()) {
					Booking booking = offer.getBooking(reference);
					if (booking != null) {
						return new ActivityReservationData(provider, offer, booking);
					}
				}
			}
		}
		throw new ActivityException();
	}

}
