package mail_converter;

public class Invoice {
	private String id;
	private String invoice_date;
	private String amount;
	private String incoterms;
	private String comment;
	private String order_id;
	
	public Invoice(String id, String invoice_date, String amount, String incoterms, String comment, String order_id) {
		this.id = id;
		this.invoice_date = DateConverter.convertFromSQLToNormal(invoice_date);
		this.amount = amount;
		this.incoterms = incoterms;
		this.comment = comment;
		this.order_id = order_id;
	}

	public String toString() {
		String invoiceAsString = "Rechnungsnummer: " + this.id + "\n" + "Rechnungsdatum: " + this.invoice_date + "\n" + "Rechnungsbetrag: " + this.amount + "€\n" + 
								 "Zahlungsbedingungen: " + this.incoterms + "\n" + "Rechnungstext: " + this.comment + "\n" + "Zugehörige Auftrags-ID: " + this.order_id + "\n";
		return invoiceAsString;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIncoterms() {
		return incoterms;
	}

	public void setIncoterms(String incoterms) {
		this.incoterms = incoterms;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
}
