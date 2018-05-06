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
@RequestMapping(value = "/rentacars/{code}/vehicles")
public class VehicleController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String vehicleForm(Model model, @PathVariable String code) {
        logger.info("vehicleForm rentACarCode:{}", code);
        logger.info("vehicleForm rentACarNumVehicles:{}", CarInterface.getRentACarDataByCode(code).getVehicles().size());

        RentACarData rentACarData = CarInterface.getRentACarDataByCode(code);

        if (rentACarData == null) {
            model.addAttribute("error", "Error: it does not exist a rentACar with the code " + code);
            model.addAttribute("rentACar", new RentACarData());
            model.addAttribute("rentACars", CarInterface.getRentACars());
            return "rentacars";
        } else {
            model.addAttribute("vehicle", new VehicleData());
            model.addAttribute("rentACar", rentACarData);
            return "vehicles";
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public String vehicleSubmit(Model model, @PathVariable String code, @ModelAttribute VehicleData vehicleData) {
        logger.info("vehicleSubmit rentACarCode:{}, plate:{}, type:{}", code, vehicleData.getPlate(), vehicleData.getType());

        try {
            CarInterface.createVehicle(code, vehicleData);
        } catch (CarException ce) {
            model.addAttribute("error", "Error: it was not possible to create the vehicle");
            model.addAttribute("vehicle", vehicleData);
            model.addAttribute("rentACar", CarInterface.getRentACarDataByCode(code));
            return "vehicles";
        }

        return "redirect:/rentacars/" + code + "/vehicles";
    }
}
