package pdf_generator;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import mail_converter.Customer;
import mail_converter.CustomerRelatedData;
import mail_converter.Invoice;
import mail_converter.Offer;
import mail_converter.Order;
import mail_converter.StringConverter;


public class PDFCreator {
	
	private static final String PDF_PATH = "C://Users/niklas.frank/Documents/Theoriephase/4. Semester/WIP Projekt/PDFs/";
	
//	public static void main(String args[]) {
//		String testkundePrivat = "Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;1;2018-08-30;20000;Busdorfmauer;22;33098;Paderborn - Kernstadt;2;2018-08-30;2018-08-31;2018-09-01;1;3;2018-09-01;1500;30 Tage Netto;Herzlichen Glueckwunsch, ist 500 EUR guenstiger geworden!;2;\n" + 
//				"Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;2;2018-09-07;1000;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\n" + 
//				"Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;3;2018-09-09;1250;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\n" + 
//				"";
//		String testkundeUnternehmen = "None;None;None;+495251396847;Support@Oqusoft.de;Oqusoft;AG;3131123456789;Detmolderstrasse;69;33102;Paderborn;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;\n";
//		
//		CustomerRelatedData customerDataPrivate = StringConverter.parseStringToCustomerData(testkundePrivat);
//		CustomerRelatedData customerDataCommercial = StringConverter.parseStringToCustomerData(testkundeUnternehmen);
//		
//		try {
//			generatePDFFromCustomerData(customerDataPrivate);
//			generatePDFFromCustomerData(customerDataCommercial);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static String generatePDFFromCustomerData(CustomerRelatedData customerData) throws IOException {
		boolean isPrivatePerson = customerData.getCustomer().isPrivatePerson();
		String first_name = customerData.getCustomer().getFirst_name();
		String last_name = customerData.getCustomer().getLast_name();
		String date_of_birth = customerData.getCustomer().getDate_of_birth();
		String telephone_no = customerData.getCustomer().getTelephone_no();
		String email = customerData.getCustomer().getEmail();
		String company_name = customerData.getCustomer().getCompany_name();
		String legal_form = customerData.getCustomer().getLegal_form();
		String tax_id = customerData.getCustomer().getTax_id();
		String street = customerData.getCustomer().getStreet();
		String house_no = customerData.getCustomer().getHouse_no();
		String postal_code = customerData.getCustomer().getPostal_code();
		String city = customerData.getCustomer().getCity();
		
		
		//Creating PDF document object 
	    PDDocument document = new PDDocument();
	    
	    ArrayList<Offer> offers = customerData.getOffers();
	    ArrayList<Order> orders = customerData.getOrders();
	    ArrayList<Invoice> invoices = customerData.getInvoices();
	    
	    int offersCount = offers.size();
	    int ordersCount = orders.size();
	    int invoicesCount = invoices.size();
	    
	    for(int i = 0; i<=offersCount; i++) {
	    	PDPage blankPage = new PDPage();
		    document.addPage(blankPage);
	    }
	    
	    PDDocumentInformation pdd = document.getDocumentInformation();
	    
	    pdd.setAuthor("Malermeister Mustermann");
	    if(isPrivatePerson) {
	    	pdd.setTitle("Kundenbezogene Daten für " + last_name + ", " + first_name);
	    }
	    else {
	    	pdd.setTitle("Kundenbezogene Daten für " + company_name + " " + legal_form);
	    }		
	    
	    PDPage titlePage = document.getPage(0);
	    
	    PDPageContentStream csBasicData = new PDPageContentStream(document, titlePage);
	    
	    csBasicData.beginText();
	    
    	String titlePart1 = "Kundenbezogene Daten für ";
	    String titlePart2;
	    String heading = "Generelle Daten: ";
	    String line1;
	    String line2;
	    String line3 = "Adresse:";
	    String line4 = "Telefonnummer:";
	    String line5 = "E-Mail Adresse:";
	    String line1Part2;
	    String line2Part2;
	    String line3Part2 = street + " " + house_no + ", " + postal_code + " " + city;
	    String line4Part2 = telephone_no;
	    String line5Part2 = email;
	    if(isPrivatePerson) {
		    titlePart2 =  last_name + ", " + first_name;
	    	line1 = "Name:";
	    	line2 = "Geburtsdatum:";
	    	line1Part2 = last_name + ", " + first_name;
	    	line2Part2 = date_of_birth;
	    }
	    else {
	    	titlePart2 = company_name + " " + legal_form;
	    	line1 = "Firmenname:";
	    	line2 = "Steuernummer:";
	    	line1Part2 = company_name + " " + legal_form;
	    	line2Part2 = tax_id;  	
	    }
	    
	    csBasicData.setFont(PDType1Font.HELVETICA_BOLD, 32);
	    csBasicData.newLineAtOffset(50, 750);
	    csBasicData.setLeading(35f);
	    
	    //Adding text in the form of string 
	    csBasicData.showText(titlePart1);
	    csBasicData.newLine();
	    csBasicData.showText(titlePart2);
	    csBasicData.newLine();
	    
	    csBasicData.endText();
	    
	    csBasicData.beginText();
	    csBasicData.setFont(PDType1Font.HELVETICA, 20);
	    csBasicData.newLineAtOffset(50, 250);
	    csBasicData.showText(heading);
	    csBasicData.endText();
	    
	    csBasicData.beginText();
	    
	    csBasicData.setFont(PDType1Font.HELVETICA, 16);
	    csBasicData.setLeading(18f);
	    csBasicData.newLineAtOffset(50, 220);
	    
	    csBasicData.showText(line1);
	    csBasicData.newLine();
	    csBasicData.showText(line2);
	    csBasicData.newLine();
	    csBasicData.showText(line3);
	    csBasicData.newLine();
	    csBasicData.showText(line4);
	    csBasicData.newLine();
	    csBasicData.showText(line5);	
	    
	    csBasicData.endText();
	    
	    csBasicData.beginText();
	    
	    csBasicData.setFont(PDType1Font.HELVETICA, 16);
	    csBasicData.setLeading(18f);
	    csBasicData.newLineAtOffset(220, 220);
	    
	    csBasicData.showText(line1Part2);
	    csBasicData.newLine();
	    csBasicData.showText(line2Part2);
	    csBasicData.newLine();
	    csBasicData.showText(line3Part2);
	    csBasicData.newLine();
	    csBasicData.showText(line4Part2);
	    csBasicData.newLine();
	    csBasicData.showText(line5Part2);
	    
	    
	    csBasicData.endText();
	    csBasicData.close();
	    
	    for(int i =1; i<=offersCount;i++) {
	    	PDPage currentPage = document.getPage(i);
	    	PDPageContentStream currentCS = new PDPageContentStream(document, currentPage);
	    	
	    	Offer currentOffer = offers.get(i-1);
	    	String offerID = currentOffer.getId();
	    	String date_of_creation = currentOffer.getDate_of_creation();
	    	String gross_price = currentOffer.getGross_price();
	    	String real_estate_adress = currentOffer.getReal_estate_street() + " " + currentOffer.getReal_estate_house_no() + ", " + currentOffer.getReal_estate_postal_code() + " " + currentOffer.getReal_estate_city();
	    	
	    	String orderID = null;
    		String orderDate_of_creation = null;
    		String begin_date = null;
    		String end_date = null;
    		String offerID_fromOrder = null;
    		
    		String invoiceID = null;
			String amount = null;
			String invoice_date = null;
			String incoterms = null;
			String comment = null;
			String orderID_fromInvoice = null;
			ArrayList<String> comments = new ArrayList<String>();
	    	
	    	String offerTitle = "Angebot Nr. " + i +": ";
	    	String offerLine1 = "Angebotsnummer: ";
	    	String offerLine2 = "Erstelldatum: ";
	    	String offerLine3 = "Geschätzter Preis: ";
	    	String offerLine4 = "An folgender Adresse: ";
	    	
	    	String orderTitle = "Auftrag Nr. ";
	    	String orderLine1 = "Auftragsnummer: ";
	    	String orderLine2 = "Erstelldatum: ";
	    	String orderLine3 = "Beginn der Ausführung: ";
	    	String orderLine4 = "Ende der Ausführung: ";
	    	String orderLine5 = "Zugehörige Angebotsnummer: ";
	    	
	    	String invoiceTitle = "Rechnung Nr. ";
	    	String invoiceLine1 = "Rechnungsnummer: ";
	    	String invoiceLine2 = "Rechnungsdatum: ";
	    	String invoiceLine3 = "Rechnungsbetrag: ";
	    	String invoiceLine4 = "Zahlungsbedingungen: ";
	    	String invoiceLine5 = "Kommentar: ";
	    	String invoiceLine6 = "Zugehörige Auftragsnummer: ";
	    	
	    	boolean hasCorrespondingOrder = false;
	    	int indexOfCorrespondingOrder = 10;
	    	boolean hasCorrespondingInvoice = false;
	    	int indexOfCorrespondingInvoice = 10;
	    	
	    	for(int j = 0; j<ordersCount; j++) {
	    		Order currentOrder = orders.get(j);
	    		if (offerID.equals(currentOrder.getOffer_id())){
	    			hasCorrespondingOrder = true;
	    			indexOfCorrespondingOrder = j;
	    			j += ordersCount;
	    			for(int k = 0; k<invoicesCount;k++) {
	    				Invoice currentInvoice = invoices.get(k);
	    				if(currentOrder.getId().equals(currentInvoice.getOrder_id())) {
	    					hasCorrespondingInvoice = true;
	    					indexOfCorrespondingInvoice = k;
	    					k += invoicesCount;
	    				}
	    				else {
	    					hasCorrespondingInvoice = false;
	    				}
	    			}
	    		}
	    		else {
	    			hasCorrespondingOrder = false;
	    			hasCorrespondingInvoice = false;
	    		}
	    	}
	    	
	    	currentCS.beginText();
	    	
	    	currentCS.setFont(PDType1Font.HELVETICA_BOLD, 28);
		    currentCS.newLineAtOffset(50, 750);
		    currentCS.setLeading(30.625f);
		    currentCS.showText("Gespeicherte Angebote, Aufträge");
		    currentCS.newLine();
		    currentCS.showText("und Rechnungen:");
		    
		    currentCS.endText();
		    
		    currentCS.beginText();
		    
		    currentCS.setFont(PDType1Font.HELVETICA, 20);
		    currentCS.newLineAtOffset(50, 650);
		    currentCS.showText(offerTitle);
		    
		    currentCS.endText();
		    
		    currentCS.beginText();
		    
	    	currentCS.setFont(PDType1Font.HELVETICA, 16);
	    	currentCS.setLeading(18f);
	    	currentCS.newLineAtOffset(50, 620);
	    	currentCS.showText(offerLine1);
	    	currentCS.newLine();
	    	currentCS.showText(offerLine2);
	    	currentCS.newLine();
	    	currentCS.showText(offerLine3);
	    	currentCS.newLine();
	    	currentCS.showText(offerLine4);
	    	
	    	currentCS.endText();
	    	
	    	currentCS.beginText();
	    	
	    	currentCS.setFont(PDType1Font.HELVETICA, 16);
	    	currentCS.setLeading(18f);
	    	currentCS.newLineAtOffset(220, 620);
	    	currentCS.showText(offerID);
	    	currentCS.newLine();
	    	currentCS.showText(date_of_creation);
	    	currentCS.newLine();
	    	currentCS.showText(gross_price + "€");
	    	currentCS.newLine();
	    	currentCS.showText(real_estate_adress);
	    	
	    	currentCS.endText();
	    	
	    	if(hasCorrespondingOrder) {
	    		Order order = orders.get(indexOfCorrespondingOrder);
	    		orderID = order.getId();
	    		orderDate_of_creation = order.getDate_of_creation();
	    		begin_date = order.getBegin_date();
	    		end_date = order.getEnd_date();
	    		offerID_fromOrder = order.getOffer_id();
	    		
	    		orderTitle += indexOfCorrespondingOrder + 1 + ": ";
	    		
	    		if(hasCorrespondingInvoice) {
	    			Invoice invoice = invoices.get(indexOfCorrespondingInvoice);
	    			
	    			invoiceID = invoice.getId();
	    			amount = invoice.getAmount();
	    			invoice_date = invoice.getInvoice_date();
	    			incoterms = invoice.getIncoterms();
	    			comment = invoice.getComment();
	    			orderID_fromInvoice = invoice.getOrder_id();
	    			
	    			if(comment.length() > 40) {
	    				int counter = 0;
	    				for(int l = 0; l<comment.length(); l += 40) {
	    					int remainingLength = (comment.length()- counter*40);
	    					if(remainingLength > 40) {
	    						comments.add(comment.substring(l, l+40));
	    					}
	    					else {
	    						comments.add(comment.substring(l, l+remainingLength));
	    					}
	    					counter += 1;
	    				}
	    			}
	    			else {
	    				comments.add(comment);
	    			}
	    
	    			invoiceTitle += indexOfCorrespondingInvoice + 1 + ": ";
	    		}
	    		else {
	    			
	    		}
	    	}
	    	else {
	    		
	    	}
	    	
	    	if(hasCorrespondingOrder) {
	    		currentCS.beginText();
	    		
			    currentCS.setFont(PDType1Font.HELVETICA, 20);
			    currentCS.newLineAtOffset(50, 450);
			    currentCS.showText(orderTitle);
			    
			    currentCS.endText();
			    
			    currentCS.beginText();
			    
		    	currentCS.setFont(PDType1Font.HELVETICA, 16);
		    	currentCS.setLeading(18f);
		    	currentCS.newLineAtOffset(50, 420);
		    	currentCS.showText(orderLine1);
		    	currentCS.newLine();
		    	currentCS.showText(orderLine2);
		    	currentCS.newLine();
		    	currentCS.showText(orderLine3);
		    	currentCS.newLine();
		    	currentCS.showText(orderLine4);
		    	currentCS.newLine();
		    	currentCS.showText(orderLine5);
		    	
		    	currentCS.endText();
		    	
		    	currentCS.beginText();
			    
		    	currentCS.setFont(PDType1Font.HELVETICA, 16);
		    	currentCS.setLeading(18f);
		    	currentCS.newLineAtOffset(270, 420);
		    	currentCS.showText(orderID);
		    	currentCS.newLine();
		    	currentCS.showText(orderDate_of_creation);
		    	currentCS.newLine();
		    	currentCS.showText(begin_date);
		    	currentCS.newLine();
		    	currentCS.showText(end_date);
		    	currentCS.newLine();
		    	currentCS.showText(offerID_fromOrder);
		    	
		    	currentCS.endText();
		    	
		    	if(hasCorrespondingInvoice) {
		    		currentCS.beginText();
		    		
				    currentCS.setFont(PDType1Font.HELVETICA, 20);
				    currentCS.newLineAtOffset(50, 250);
				    currentCS.showText(invoiceTitle);
				    
				    currentCS.endText();
				    
				    currentCS.beginText();
				    
			    	currentCS.setFont(PDType1Font.HELVETICA, 16);
			    	currentCS.setLeading(18f);
			    	currentCS.newLineAtOffset(50, 220);
			    	currentCS.showText(invoiceLine1);
			    	currentCS.newLine();
			    	currentCS.showText(invoiceLine2);
			    	currentCS.newLine();
			    	currentCS.showText(invoiceLine3);
			    	currentCS.newLine();
			    	currentCS.showText(invoiceLine4);
			    	currentCS.newLine();
			    	currentCS.showText(invoiceLine5);
			    	currentCS.newLine();
			    	if(comments.size() > 1) {
			    		for(int m = 1; m<comments.size(); m++) {
			    			currentCS.showText("");
			    			currentCS.newLine();
			    		}
			    	}
			    	currentCS.showText(invoiceLine6);
			    	
			    	currentCS.endText();
			    	
			    	currentCS.beginText();
				    
			    	currentCS.setFont(PDType1Font.HELVETICA, 16);
			    	currentCS.setLeading(18f);
			    	currentCS.newLineAtOffset(270, 220);
			    	currentCS.showText(invoiceID);
			    	currentCS.newLine();
			    	currentCS.showText(amount);
			    	currentCS.newLine();
			    	currentCS.showText(invoice_date);
			    	currentCS.newLine();
			    	currentCS.showText(incoterms);
			    	currentCS.newLine();
			    	for(int m = 0; m < comments.size(); m++) {
			    		currentCS.showText(comments.get(m));
			    		currentCS.newLine();
			    	}
			    	currentCS.showText(orderID_fromInvoice);
			    	
			    	currentCS.endText();
		    	}
		    	else {
		    		currentCS.beginText();
		    		
				    currentCS.setFont(PDType1Font.HELVETICA, 20);
				    currentCS.newLineAtOffset(50, 250);
				    currentCS.showText("Zu diesem Auftrag gibt es keine Rechnung!");
				    
				    currentCS.endText();
		    	}
	    	}
	    	else {
	    		currentCS.beginText();
	    		
			    currentCS.setFont(PDType1Font.HELVETICA, 20);
			    currentCS.newLineAtOffset(50, 450);
			    currentCS.showText("Zu diesem Angebot gibt es weder Auftrag noch Rechnung!");
			    
			    currentCS.endText();
	    	}
	    	
	    	//System.out.println("Gibt es einen Auftrag? " + hasCorrespondingOrder + indexOfCorrespondingOrder + "Und eine Rechnung? " + hasCorrespondingInvoice + indexOfCorrespondingInvoice);
	    	currentCS.close();
	    	
	    	
	    }
	    
	    String filename;
	    
	    //Saving the document
	    if(isPrivatePerson) {
	    	filename = "Kundenbezogene Daten fuer " + last_name + "_" + first_name + ".pdf";
	    }
	    else {
	    	filename = "Kundenbezogene Daten fuer " + company_name + " " + legal_form + ".pdf";
	    }
	    
	    document.save(PDF_PATH + filename);
	    
	    //Closing the document  
	    document.close();
	    
	    System.out.println("Dokument gespeichert unter: " + PDF_PATH + filename);
	    
	    return (PDF_PATH + filename);
		
	}
}
