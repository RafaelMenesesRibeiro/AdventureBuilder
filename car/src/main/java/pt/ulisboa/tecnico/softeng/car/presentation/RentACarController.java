package pt.ulisboa.tecnico.softeng.car.presentation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

@Controller
@RequestMapping(value = "/rentacars")
public class RentACarController {
	private static Logger logger = LoggerFactory.getLogger(RentACarController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String renACarForm(Model model) {
		logger.info("rentACarForm");
		model.addAttribute("rentacar", new RentACarData());
		model.addAttribute("rentacars", CarInterface.getRentACars());
		return "rentacars";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String rentACarSubmit(Model model, @ModelAttribute RentACarData rentACarData) {
		logger.info("rentACarSubmit code:{} name:{} nif:{} Iban:{}", rentACarData.getCode(), rentACarData.getName(), rentACarData.getNif(), rentACarData.getIban());

		try {
			CarInterface.createRentACar(rentACarData);
		} catch (CarException ce) {
			model.addAttribute("error", "Error: it was not possible to create the rentACar");
			model.addAttribute("rentacar", rentACarData);
			model.addAttribute("rentacars", CarInterface.getRentACars());
			return "rentacars";
		}

		return "redirect:/rentacars";
	}
}
