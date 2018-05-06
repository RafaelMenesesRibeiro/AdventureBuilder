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
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.TaxPayerData;

@Controller
@RequestMapping(value = "/taxpayers")
public class TaxPayerController {
	private static Logger logger = LoggerFactory.getLogger(TaxPayerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String taxPayerForm(Model model) {
		logger.info("taxPayerForm");
		model.addAttribute("taxpayer", new TaxPayerData());
		model.addAttribute("taxpayers", TaxInterface.getTaxPayers());
		return "taxpayers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String taxPayerSubmit(Model model, @ModelAttribute TaxPayerData taxPayerData) {
		logger.info("taxPayerDataSubmit nif:{} name:{} address:{} class:{}", taxPayerData.getNif(), taxPayerData.getName(), taxPayerData.getAddress(), taxPayerData.getClass());

		try {
			TaxInterface.createTaxPayer(taxPayerData);
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the taxPayer");
			model.addAttribute("taxpayer", taxPayerData);
			model.addAttribute("taxpayers", TaxInterface.getTaxPayers());
			return "taxpayers";
		}

		return "redirect:/taxpayers";
	}
}
