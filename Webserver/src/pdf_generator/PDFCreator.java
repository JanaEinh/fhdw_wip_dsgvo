//Author: Niklas
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
	
	private static final String PDF_PATH = "C:\\Users\\niklas.frank\\Documents\\Theoriephase\\4. Semester\\WIP Projekt\\PDFs";
	
	public static String generatePDFFromCustomerData(CustomerRelatedData customerData) throws IOException {
		//Extracting information out of the CustomerRelatedData Object
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
		ArrayList<Offer> offers = customerData.getOffers();
	    ArrayList<Order> orders = customerData.getOrders();
	    ArrayList<Invoice> invoices = customerData.getInvoices();
		
		
		//Creating PDF document object 
	    PDDocument document = new PDDocument();
	    
	    int offersCount = offers.size();
	    int ordersCount = orders.size();
	    int invoicesCount = invoices.size();
	    
	    //Creating one blank PDF Page for each Offer plus one title Page
	    for(int i = 0; i<=offersCount; i++) {
	    	PDPage blankPage = new PDPage();
		    document.addPage(blankPage);
	    }
	    
	    //Setting general information about the PDF Document
	    PDDocumentInformation pdd = document.getDocumentInformation();
	    
	    pdd.setAuthor("Malermeister Mustermann");
	    if(isPrivatePerson) {
	    	pdd.setTitle("Kundenbezogene Daten für " + last_name + ", " + first_name);
	    }
	    else {
	    	pdd.setTitle("Kundenbezogene Daten für " + company_name + " " + legal_form);
	    }		
	    
	    //Getting the first Page as Title Page
	    PDPage titlePage = document.getPage(0);
	    
	    //Opening a content stream to write onto the PDF
	    PDPageContentStream csBasicData = new PDPageContentStream(document, titlePage);
	    
	    csBasicData.beginText();
	    
	    //Generating the single Strings which are to be shown on the title Page
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
	    
	    
	    //Actually write the generated Strings onto the title Page
	    csBasicData.setFont(PDType1Font.HELVETICA_BOLD, 32);
	    csBasicData.newLineAtOffset(50, 750);
	    csBasicData.setLeading(35f);
	    
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
	    
	    //Iterating through all offers to write the information about them on the correct Page
	    for(int i =1; i<=offersCount;i++) {
	    	//Getting the Page for the current offer and opening a content stream
	    	PDPage currentPage = document.getPage(i);
	    	PDPageContentStream currentCS = new PDPageContentStream(document, currentPage);
	    	
	    	//Getting the current offer and extracting information about it
	    	Offer currentOffer = offers.get(i-1);
	    	String offerID = currentOffer.getId();
	    	String date_of_creation = currentOffer.getDate_of_creation();
	    	String gross_price = currentOffer.getGross_price();
	    	String real_estate_adress = currentOffer.getReal_estate_street() + " " + currentOffer.getReal_estate_house_no() + ", " + currentOffer.getReal_estate_postal_code() + " " + currentOffer.getReal_estate_city();
	    	
	    	//Initializing Strings for the information about corresponding offers and invoices which are to be filled with information later on 
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
			
			//Initializing the first Part of each line which is to be Shown on the Page
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
	    	
	    	//Initializing variables for corresponding orders and invoices
	    	boolean hasCorrespondingOrder = false;
	    	int indexOfCorrespondingOrder = 10;
	    	boolean hasCorrespondingInvoice = false;
	    	int indexOfCorrespondingInvoice = 10;
	    	
	    	//Iterating through all orders to find out whether there is an order which belongs to the current offer
	    	for(int j = 0; j<ordersCount; j++) {
	    		//Getting the current order
	    		Order currentOrder = orders.get(j);
	    		//Evaluating whether the current orders offer id matches the id of the current offer
	    		if (offerID.equals(currentOrder.getOffer_id())){
	    			//Saving the index of the order that corresponds to the current offer
	    			hasCorrespondingOrder = true;
	    			indexOfCorrespondingOrder = j;
	    			//Adding the size of the orders array list to ensure there is no further iteration
	    			j += ordersCount;
	    			//Iterating through all invoices to find a correpsonding invoice to the current order
	    			for(int k = 0; k<invoicesCount;k++) {
	    				//Getting the current invoice
	    				Invoice currentInvoice = invoices.get(k);
	    				//Evaluationg whether the invoices order id matches the id of the current order
	    				if(currentOrder.getId().equals(currentInvoice.getOrder_id())) {
	    					hasCorrespondingInvoice = true;
	    					indexOfCorrespondingInvoice = k;
	    					//Adding the size of the invoices array list to ensure there is no further iteration
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
	    	
	    	//Writing the title on the Page 
	    	currentCS.beginText();
	    	
	    	currentCS.setFont(PDType1Font.HELVETICA_BOLD, 28);
		    currentCS.newLineAtOffset(50, 750);
		    currentCS.setLeading(30.625f);
		    currentCS.showText("Gespeicherte Angebote, Aufträge");
		    currentCS.newLine();
		    currentCS.showText("und Rechnungen:");
		    
		    currentCS.endText();
		    
		    //Writing the information about the current offer onto the Page
		    currentCS.beginText();
		    
		    currentCS.setFont(PDType1Font.HELVETICA, 20);
		    currentCS.newLineAtOffset(50, 650);
		    currentCS.showText(offerTitle);
		    
		    currentCS.endText();
		    
		    //First the first part of each line is added which describes, what information is shown
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
	    	
	    	//And after that the corresponding Value is added 
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
	    	
	    	//This part is only executed if there is a corresponding Order
	    	if(hasCorrespondingOrder) {
	    		//Extracting information from the corresponding order
	    		Order order = orders.get(indexOfCorrespondingOrder);
	    		orderID = order.getId();
	    		orderDate_of_creation = order.getDate_of_creation();
	    		begin_date = order.getBegin_date();
	    		end_date = order.getEnd_date();
	    		offerID_fromOrder = order.getOffer_id();
	    		
	    		orderTitle += indexOfCorrespondingOrder + 1 + ": ";
	    		
	    		//Only executed if corresponding invoice
	    		if(hasCorrespondingInvoice) {
	    			//Extracting information from the corresponding invoice
	    			Invoice invoice = invoices.get(indexOfCorrespondingInvoice);
	    			invoiceID = invoice.getId();
	    			amount = invoice.getAmount();
	    			invoice_date = invoice.getInvoice_date();
	    			incoterms = invoice.getIncoterms();
	    			comment = invoice.getComment();
	    			orderID_fromInvoice = invoice.getOrder_id();
	    			
	    			//Splitting the comment in parts of 40 characters max to ensure the fit in the line
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
	    	
	    	//If there is a corresponding order the information about it is written onto the PDF
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
		    	
		    	//If there is a corresponding invoice the information about it is written onto the PDF
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
			    	currentCS.showText(invoice_date);
			    	currentCS.newLine();
			    	currentCS.showText(amount + "€");
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
	    
	    return (PDF_PATH + filename);
		
	}
}
