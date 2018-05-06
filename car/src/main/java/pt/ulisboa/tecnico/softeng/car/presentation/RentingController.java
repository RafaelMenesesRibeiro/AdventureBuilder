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
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;

@Controller
@RequestMapping(value = "/rentacars/{code}/vehicles/{plate}/rentings")
public class RentingController {
    private static Logger logger = LoggerFactory.getLogger(RentingController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String rentingForm(Model model, @PathVariable String code, @PathVariable String plate) {
        logger.info("rentingForm rentACarCode:{}, plate", code, plate);

        RentACarData renterData = CarInterface.getRentACarDataByCode(code);
        VehicleData vehicleData = CarInterface.getVehicleDataByPlate(code, plate);

        if (renterData == null) {
            model.addAttribute("error",
                    "Error: it does not exist a rentACar with code " + plate);
            model.addAttribute("rentACar", new RentACarData());
            model.addAttribute("rentACars", CarInterface.getRentACars());
            return "rentACar";
        }
        else if (vehicleData == null) {
            model.addAttribute("error",
                    "Error: it does not exist a vehicle with plate " + plate + " in rentACar with code " + code);
            model.addAttribute("rentACar", new RentACarData());
            model.addAttribute("rentACars", CarInterface.getRentACars());
            return "rentACar";
        } else {
            model.addAttribute("renting", new RentingData());
            model.addAttribute("rentACar", renterData);
            model.addAttribute("vehicle", vehicleData);
            return "rentings";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String rentingSubmit(Model model, @PathVariable String code, @PathVariable String plate,
            @ModelAttribute RentingData renting) {
        logger.info("rentingSubmit rentACarCode:{}, plate:{}, drivingLincense:{}, begin:{}, end:{}, nif:{}, iban:{}, renting:{}", code, plate,
                renting.getDrivingLicense(), renting.getBegin(), renting.getEnd(), renting.getNif(), renting.getIban(), renting);
        
        try {
            CarInterface.createRenting(code, plate, renting);
        } catch (CarException ce) {
            model.addAttribute("error", "Error: it was not possible to rent the vehicle");
            model.addAttribute("renting", renting);
            model.addAttribute("vehicle", CarInterface.getVehicleDataByPlate(code, plate));
            model.addAttribute("rentACar", CarInterface.getRentACarDataByCode(code));
            return "rentings";
        }

        return "redirect:/rentacars/" + code + "/vehicles/" + plate + "/rentings";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String checkout(Model model, @PathVariable String code, @PathVariable String plate,
            @ModelAttribute RentingData renting) {
        logger.info(
                "checkout rentACarCode:{}, plate:{}, kilometers:{}, reference:{}",
                code, plate, renting.getKilometers(), renting.getReference());

        try {
            CarInterface.checkout(renting.getReference(), renting.getKilometers());
        } catch (CarException ce) {
            model.addAttribute("error", "Error: it was not possible to checkout the renting");
            model.addAttribute("renting", renting);
            model.addAttribute("vehicle", CarInterface.getVehicleDataByPlate(code, plate));
            model.addAttribute("rentACar", CarInterface.getRentACarDataByCode(code));
            return "rentings";
        }

        return "redirect:/rentacars/" + code + "/vehicles/" + plate + "/rentings";
    }
}
