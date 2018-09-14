package mail_converter;

import java.util.ArrayList;

public class CustomerRelatedData {
	private Customer customer;
	private ArrayList<Offer> offers;
	private ArrayList<Order> orders;
	private ArrayList<Invoice> invoices;
	
	public CustomerRelatedData(Customer customer, ArrayList<Offer> offers, ArrayList<Order> orders, ArrayList<Invoice> invoices) {
		this.customer = customer;
		this.offers = offers;
		this.orders = orders;
		this.invoices = invoices;
	}
	
	public CustomerRelatedData() {
		
	}
	
	public String toString() {
		String customerRelatedDataAsString = "";
		customerRelatedDataAsString += this.customer.toString();
		int anzahlAngebote = offers.size();
		if(anzahlAngebote == 0) {
			customerRelatedDataAsString += "\nZu diesem Kunden gibt es keine Angebote.\n";
		}
		else {
			for(int i = 0; i<anzahlAngebote; i++) {
				if(i==0) {
					customerRelatedDataAsString += "\nAngebote: \n";
					customerRelatedDataAsString += "\nAngebot Nr." + (i+1) + ": \n" + offers.get(i).toString();
				}
				else {
					customerRelatedDataAsString += "\nAngebot Nr." + (i+1) + ": \n" + offers.get(i).toString();
				}
			}
		}
		int anzahlAuftraege = orders.size();
		if(anzahlAuftraege == 0) {
			customerRelatedDataAsString += "\nZu diesem Kunden gibt es keine AuftrÃ¤ge.\n";
		}
		else {
			for(int i = 0; i<anzahlAuftraege; i++) {
				if(i==0) {
					customerRelatedDataAsString += "\nAuftrÃ¤ge: \n";
					customerRelatedDataAsString += "\nAuftrag Nr." + (i+1) + ": \n" + orders.get(i).toString();
				}
				else {
					customerRelatedDataAsString += "\nAuftrag Nr." + (i+1) + ": \n" + orders.get(i).toString();
				}
			}
		}
		int anzahlRechnungen = invoices.size();
		if(anzahlRechnungen == 0) {
			customerRelatedDataAsString += "\nZu diesem Kunden gibt es keine Rechnungen.\n";
		}
		else {
			for(int i = 0; i<anzahlRechnungen; i++) {
				if(i==0) {
					customerRelatedDataAsString += "\nRechnungen: \n";
					customerRelatedDataAsString += "\nRechnung Nr." + (i+1) + ": \n" + invoices.get(i).toString();
				}
				else {
					customerRelatedDataAsString += "\nRechnung Nr." + (i+1) + ": \n" + invoices.get(i).toString();
				}
			}
		}
//		customerRelatedDataAsString.replaceAll("Ae", "Ä");
//		customerRelatedDataAsString.replaceAll("ae", "ä");
//		customerRelatedDataAsString.replaceAll("Oe", "Ö");
//		customerRelatedDataAsString.replaceAll("oe", "ö");
//		customerRelatedDataAsString.replaceAll("Ue", "Ü");
//		customerRelatedDataAsString.replaceAll("ue", "ü");
		return customerRelatedDataAsString;
	}

	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ArrayList<Offer> getOffers() {
		return offers;
	}

	public void setOffers(ArrayList<Offer> offers) {
		this.offers = offers;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
}
