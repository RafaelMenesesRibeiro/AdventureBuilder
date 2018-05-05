package pt.ulisboa.tecnico.softeng.activity.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;

@Controller
@RequestMapping(value = "/providers/{codeProvider}/activities/{codeActivity}/offers/{codeOffer}/reservations")
public class ActivityBookingController {
	private static Logger logger = LoggerFactory.getLogger(ActivityBookingController.class);
	@RequestMapping(method = RequestMethod.GET)
	public String bookingForm(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String codeOffer) {
		logger.info("bookingForm codeProvider:{}, codeActivity:{}, codeOffer:{}", codeProvider, codeActivity, codeOffer);

		ActivityProviderData providerData = ActivityInterface.getProviderDataByCode(codeProvider);
		ActivityData activityData = ActivityInterface.getActivityDataByCode(codeProvider, codeActivity);
		ActivityOfferData activityOfferData = ActivityInterface.getActivityOfferDataByCode(codeProvider, codeActivity, codeOffer);

		if (activityOfferData == null) {
			model.addAttribute("error", "Error: it does not exist an activity with code " + codeActivity + " in provider with code " + codeProvider);
			model.addAttribute("provider", new ActivityReservationData());
			model.addAttribute("providers", ActivityInterface.getProviders());
			return "providers";
		} else {
			model.addAttribute("provider", providerData);
			model.addAttribute("activity", activityData);
			model.addAttribute("offer", activityOfferData);
			model.addAttribute("reservation", new ActivityReservationData());
			return "reservations";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String bookingSubmit(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String codeOffer, @ModelAttribute ActivityReservationData reservation) {
		logger.info("bookingSubmit codeProvider:{}, codeActivity:{}, codeOffer:{}", codeProvider, codeActivity, codeOffer);
		/*
		try {
			ActivityInterface.createOffer(codeProvider, codeActivity, offer);
		} catch (ActivityException e) {
			model.addAttribute("error", "Error: it was not possible to create de offer");
			model.addAttribute("offer", offer);
			model.addAttribute("activity", ActivityInterface.getActivityDataByCode(codeProvider, codeActivity));
			return "reservations";
		}
		*/
		return "redirect:/providers/" + codeProvider + "/activities/" + codeActivity + "/offers/" + codeOffer + "/reservations";
	}
}
