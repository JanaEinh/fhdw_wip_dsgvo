//Author: Niklas
package mail_converter;

import java.util.ArrayList;

public class StringConverter {
	
	public static String parseStringFromMail(String emailContent) {
		//Catching the case that the Database contains no matching Data or that no matching Mail could be found
		if(emailContent.startsWith("Keine Daten gefunden!")) {
			return "Zu diesen Eingaben konnten keine Kundendaten gefunden werden. Bitte überprüfen Sie Ihre Eingaben und versuchen Sie es erneut!";
		}
		else if(emailContent.equals("")) {
			return "Bei der Abfrage kam es zu unerwarteten Problemen. Bitte versuchen Sie es erneut!";
		}
		else {
			//Parsing the String to a CustomerRelatedDataObject and using its toString method to generate a "readable" String with all information
			CustomerRelatedData customerData = parseStringToCustomerData(emailContent);
			String customerDataAsString = customerData.toString();
			return customerDataAsString;
		}
	}
	
	public static String turnCustomerDataToString(CustomerRelatedData customerData) {
		String customerDataAsString = customerData.toString();
		return customerDataAsString;
	}
	
	public static CustomerRelatedData parseStringToCustomerData(String emailContent) {
		
		//Catching the case that the Database contains no matching Data or that no matching Mail could be found
		if(emailContent.startsWith("Keine Daten gefunden!")) {
			CustomerRelatedData noData = new CustomerRelatedData();
			return noData;
		}
		else if(emailContent.equals("")) {
			CustomerRelatedData noData = new CustomerRelatedData();
			return noData;
		}
		
		else {
			//Declaring array lists for each newline which delimits the datasets from each other and each single dataset
			ArrayList<Integer> zeilenumbrueche = new ArrayList<Integer>();
			ArrayList<String> einzelneDatensaetze = new ArrayList<String>();
			
			//searching for newline characters in the mail string and adding their position to the array list zeilenumbrueche
			for (int i = 0; i != -1;) {
			  i = emailContent.indexOf("\n", i);
			  if (i != -1) {
			    zeilenumbrueche.add(i);
			    i++;
			  }
			}
			
			//Declaring variables for the information in the Datasets
			int anzahlDatensaetze = zeilenumbrueche.size();
			Customer customer = new Customer();
			ArrayList<Offer> offers = new ArrayList<Offer>();
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<Invoice> invoices = new ArrayList<Invoice>();
			
			//Iterating through all lines in the E-Mail with each line representating one Dataset
			for(int i = 0; i<anzahlDatensaetze; i++) {
				//Getting the String for the Current Dataset from the E-Mail String 
				if(i == 0) {
					einzelneDatensaetze.add(emailContent.substring(0, (zeilenumbrueche.get(i))));
				}
				else {
					einzelneDatensaetze.add(emailContent.substring(zeilenumbrueche.get(i-1)+2, (zeilenumbrueche.get(i))));
				}
				
				//Declaring variables for the data delimitors which seperate the single values 
				String aktuellerDatensatz = einzelneDatensaetze.get(i);
				ArrayList<Integer> seperatoren = new ArrayList<Integer>();
				//Getting the posititon of each delimitor and adding it to the array list seperatoren
				for(int j = 0; j!= -1;) {
					j = aktuellerDatensatz.indexOf(";", j);
					if(j != -1) {
						seperatoren.add(j);
						j++;
					}
				}
				//Declaring an array list which contains all single values from the current dataset
				int anzahleinzelnerDaten = seperatoren.size();
				ArrayList<String> einzelneDaten = new ArrayList<String>();
				//Adding each Value to the array list einzelneDaten
				for(int j = 0; j<anzahleinzelnerDaten; j++) {
					if(j == 0) {
						einzelneDaten.add(aktuellerDatensatz.substring(0, seperatoren.get(0)));
					}
					else {
						einzelneDaten.add(aktuellerDatensatz.substring(seperatoren.get(j-1)+1, seperatoren.get(j)));
					}
				}
				
				//If we are dealing with the first dataset, we have to initialize the customer. This is not necessary in the following iterations as the Customer is always the same
				if(i == 0) {
					customer = new Customer(einzelneDaten.get(0), einzelneDaten.get(1), einzelneDaten.get(2), einzelneDaten.get(3), einzelneDaten.get(4), einzelneDaten.get(5), einzelneDaten.get(6), einzelneDaten.get(7), einzelneDaten.get(8), einzelneDaten.get(9), einzelneDaten.get(10), einzelneDaten.get(11));
				}
				else {
					
				}
				
				//Checking if there is an offer in this dataset and adding its information to an offer object which is then appended to the array list offers
				if(einzelneDaten.get(12).equals("None")) {
					
				}
				else {
					Offer currentOffer = new Offer(einzelneDaten.get(12), einzelneDaten.get(13), einzelneDaten.get(14), einzelneDaten.get(15), einzelneDaten.get(16), einzelneDaten.get(17), einzelneDaten.get(18));
					offers.add(currentOffer);
				}
				
				//Checking if there is an order in this dataset and adding its information to an order object which is then appended to the array list orders
				if(einzelneDaten.get(19).equals("None")) {
					
				}
				else {
					Order currentOrder = new Order(einzelneDaten.get(19), einzelneDaten.get(20), einzelneDaten.get(21), einzelneDaten.get(22), einzelneDaten.get(23));
					orders.add(currentOrder);
				}
				
				//Checking if there is an invoice in this dataset and adding its information to an invoice object which is then appended to the array list invoices
				if(einzelneDaten.get(24).equals("None")) {
					
				}
				else {
					Invoice currentInvoice = new Invoice(einzelneDaten.get(24), einzelneDaten.get(25), einzelneDaten.get(26), einzelneDaten.get(27), einzelneDaten.get(28), einzelneDaten.get(29));
					invoices.add(currentInvoice);
				}			
			}
			
			//Creating the CustomerRelatedData Object by using the customer and the array lists for offers, orders and invoices
			CustomerRelatedData customerRelatedData = new CustomerRelatedData(customer, offers, orders, invoices);
			return customerRelatedData;
		}
	}
}
