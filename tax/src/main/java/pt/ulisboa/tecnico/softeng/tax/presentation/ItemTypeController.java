package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;

@Controller
@RequestMapping(value = "/itemtypes")
public class ItemTypeController {
	private static Logger logger = LoggerFactory.getLogger(ItemTypeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String itemTypeForm(Model model) {
		logger.info("itemTypeForm");
		model.addAttribute("itemtype", new ItemTypeData());
		model.addAttribute("itemtypes", TaxInterface.getItemTypes());
		return "itemtypes";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String itemTypeSubmit(Model model, @ModelAttribute ItemTypeData itemTypeData) {
		logger.info("itemTypeDataSubmit name:{} tax:{}", itemTypeData.getName(), itemTypeData.getTax());

		try {
			TaxInterface.createItemType(itemTypeData);
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the itemType");
			model.addAttribute("itemtype", itemTypeData);
			model.addAttribute("itemtypes", TaxInterface.getItemTypes());
			return "itemtypes";
		}

		return "redirect:/itemtypes";
	}
}
