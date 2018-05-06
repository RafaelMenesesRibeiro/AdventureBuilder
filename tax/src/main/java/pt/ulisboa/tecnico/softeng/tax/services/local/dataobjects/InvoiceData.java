package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;

import org.springframework.format.annotation.DateTimeFormat;

public class InvoiceData {
	private String sellerNIF;
	private String buyerNIF;
	private String itemType;
	private float value;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;

	public InvoiceData() {
	}

	public InvoiceData(String sellerNIF, String buyerNIF, String itemType, float value, LocalDate date) {
		this.sellerNIF = sellerNIF;
		this.buyerNIF = buyerNIF;
		this.itemType = itemType;
		this.value = value;
		this.date = date;
	}

	public InvoiceData(Invoice invoice) {
		this.buyerNIF = invoice.getBuyer().getNif();
		this.sellerNIF = invoice.getSeller().getNif();
		this.itemType = invoice.getItemType().getName();
		this.value = (float) invoice.getValue();
		this.date = invoice.getDate();
	}

	public String getSellerNIF() {
		return this.sellerNIF;
	}

	public void setSellerNIF(String sellerNIF) {
		this.sellerNIF = sellerNIF;
	}

	public String getBuyerNIF() {
		return this.buyerNIF;
	}

	public void setBuyerNIF(String buyerNIF) {
		this.buyerNIF = buyerNIF;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
