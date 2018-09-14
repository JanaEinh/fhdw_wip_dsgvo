package mail_converter;

import java.util.ArrayList;

public class StringConverter {
//	public static void main(String args[]) {
//		String testkundePrivat = "Timm;Reinholdt;1997-09-13;+4917698765421;testMitHasan@hasancito.de;None;None;None;Detmolderstrasse;48;33102;Paderborn;1;2018-08-30;20000;Busdorfmauer;22;33098;Paderborn - Kernstadt;2;2018-08-30;2018-08-31;2018-09-01;1;3;2018-09-01;1500;30 Tage Netto;Herzlichen GlÃ¼ckwunsch, ist 500 EUR gÃ¼nstiger geworden!;2;\nTimm;Reinholdt;1997-09-13;+4917698765421;testMitHasan@hasancito.de;None;None;None;Detmolderstrasse;48;33102;Paderborn;2;2018-09-07;1000;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\nTimm;Reinholdt;1997-09-13;+4917698765421;testMitHasan@hasancito.de;None;None;None;Detmolderstrasse;48;33102;Paderborn;3;2018-09-09;1250;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\n";
//		String testkundeUnternehmen = "None;None;None;+495251396847;Support@Oqusoft.de;Oqusoft;AG;3131123456789;Detmolderstrasse;69;33102;Paderborn;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;\r\n";
//		
//		System.out.println(parseStringFromMail(testkundePrivat));
//		//System.out.println(parseStringFromMail(testkundeUnternehmen));
//	}
	
	public static String parseStringFromMail(String emailContent) {		
		if(emailContent.startsWith("Keine Daten gefunden!")) {
			return "Zu diesen Eingaben konnten keine Kundendaten gefunden werden. Bitte überprüfen Sie Ihre Eingaben und versuchen Sie es erneut!";
		}
		else if(emailContent.equals("")) {
			return "Bei der Abfrage kam es zu unerwarteten Problemen. Bitte versuchen Sie es erneut!";
		}
		else {
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
		
		if(emailContent.startsWith("Keine Daten gefunden!")) {
			CustomerRelatedData noData = new CustomerRelatedData();
			return noData;
		}
		else if(emailContent.equals("")) {
			CustomerRelatedData noData = new CustomerRelatedData();
			return noData;
		}
		
		else {

			emailContent = emailContent.replaceAll("Ae", "Ä");
			emailContent = emailContent.replaceAll("ae", "ä");
			emailContent = emailContent.replaceAll("Oe", "Ö");
			emailContent = emailContent.replaceAll("oe", "ö");
			emailContent = emailContent.replaceAll("Ue", "Ü");
			emailContent = emailContent.replaceAll("ue", "ü");
			
			ArrayList<Integer> zeilenumbrueche = new ArrayList<Integer>();
			ArrayList<String> einzelneDatensaetze = new ArrayList<String>();
			for (int i = 0; i != -1;) {
			  i = emailContent.indexOf("\n", i);
			  if (i != -1) {
			    zeilenumbrueche.add(i);
			    i++;
			  }
			}
			
			int anzahlDatensaetze = zeilenumbrueche.size();
			Customer customer = new Customer();
			ArrayList<Offer> offers = new ArrayList<Offer>();
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<Invoice> invoices = new ArrayList<Invoice>();
			
			for(int i = 0; i<anzahlDatensaetze; i++) {
				if(i == 0) {
					einzelneDatensaetze.add(emailContent.substring(0, (zeilenumbrueche.get(i))));
				}
				else {
					einzelneDatensaetze.add(emailContent.substring(zeilenumbrueche.get(i-1)+2, (zeilenumbrueche.get(i))));
				}
				
				String aktuellerDatensatz = einzelneDatensaetze.get(i);
				ArrayList<Integer> seperatoren = new ArrayList<Integer>();
				for(int j = 0; j!= -1;) {
					j = aktuellerDatensatz.indexOf(";", j);
					if(j != -1) {
						seperatoren.add(j);
						j++;
					}
				}
				int anzahleinzelnerDaten = seperatoren.size();
				ArrayList<String> einzelneDaten = new ArrayList<String>();
				for(int j = 0; j<anzahleinzelnerDaten; j++) {
					if(j == 0) {
						einzelneDaten.add(aktuellerDatensatz.substring(0, seperatoren.get(0)));
					}
					else {
						einzelneDaten.add(aktuellerDatensatz.substring(seperatoren.get(j-1)+1, seperatoren.get(j)));
					}
				}
				
				if(i == 0) {
					customer = new Customer(einzelneDaten.get(0), einzelneDaten.get(1), einzelneDaten.get(2), einzelneDaten.get(3), einzelneDaten.get(4), einzelneDaten.get(5), einzelneDaten.get(6), einzelneDaten.get(7), einzelneDaten.get(8), einzelneDaten.get(9), einzelneDaten.get(10), einzelneDaten.get(11));
				}
				else {
					
				}
				
				if(einzelneDaten.get(12).equals("None")) {
					
				}
				else {
					Offer currentOffer = new Offer(einzelneDaten.get(12), einzelneDaten.get(13), einzelneDaten.get(14), einzelneDaten.get(15), einzelneDaten.get(16), einzelneDaten.get(17), einzelneDaten.get(18));
					offers.add(currentOffer);
				}
				if(einzelneDaten.get(19).equals("None")) {
					
				}
				else {
					Order currentOrder = new Order(einzelneDaten.get(19), einzelneDaten.get(20), einzelneDaten.get(21), einzelneDaten.get(22), einzelneDaten.get(23));
					orders.add(currentOrder);
				}
				if(einzelneDaten.get(24).equals("None")) {
					
				}
				else {
					Invoice currentInvoice = new Invoice(einzelneDaten.get(24), einzelneDaten.get(25), einzelneDaten.get(26), einzelneDaten.get(27), einzelneDaten.get(28), einzelneDaten.get(29));
					invoices.add(currentInvoice);
				}			
			}
			
			CustomerRelatedData customerRelatedData = new CustomerRelatedData(customer, offers, orders, invoices);
			return customerRelatedData;
		}
	}
}
