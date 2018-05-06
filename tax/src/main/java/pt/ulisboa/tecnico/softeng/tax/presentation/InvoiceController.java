package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;

@Controller
@RequestMapping(value = "/taxpayers/{nif}/invoices")
public class InvoiceController {
	private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String invoiceForm(Model model, @PathVariable String nif) {
		
		if(TaxInterface.isBuyer(nif)) {
			logger.info("invoiceForm");
			model.addAttribute("invoice", new InvoiceData());
			model.addAttribute("invoicesbuyer", TaxInterface.getInvoicesByTaxPayerNIF(nif));
			return "invoicesbuyer";
		} else {
			logger.info("invoiceForm");
			model.addAttribute("invoice", new InvoiceData());
			model.addAttribute("invoicesseller", TaxInterface.getInvoicesByTaxPayerNIF(nif));
			return "invoicesseller";
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String invoiceSubmit(Model model, @PathVariable String nif, @ModelAttribute InvoiceData invoiceData) {
		logger.info("InvoiceDataSubmit value:{} date:{} itemtype:{} buyerNIF:{} sellerNIF:{}", invoiceData.getValue(), invoiceData.getDate(), invoiceData.getItemType(), invoiceData.getBuyerNIF(), invoiceData.getSellerNIF());

		try {
			TaxInterface.createInvoice(invoiceData);
		} catch (TaxException te) {
			model.addAttribute("error", "Error: it was not possible to create the itemType");
			model.addAttribute("invoice", invoiceData);
			model.addAttribute("invoices", TaxInterface.getInvoicesByTaxPayerNIF(nif));
			return "invoices";
		}

		return "redirect:/taxpayers/" + nif + "/invoices";
	}
}