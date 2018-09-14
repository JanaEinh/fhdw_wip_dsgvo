//Author: Niklas

package pdf_generator;

import mail_converter.StringConverter;
import mail_converter.CustomerRelatedData;

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
import mail_converter.Invoice;
import mail_converter.Order;
import mail_converter.Offer;

public class PDFCreator {
	public static void main(String args[]) {
		String testkundePrivat = "Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;1;2018-08-30;20000;Busdorfmauer;22;33098;Paderborn - Kernstadt;2;2018-08-30;2018-08-31;2018-09-01;1;3;2018-09-01;1500;30 Tage Netto;Herzlichen Glückwunsch, ist 500 EUR günstiger geworden!;2;\n"
				+ "Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;2;2018-09-07;1000;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\n"
				+ "Timm;Reinholdt;1997-09-13;+4917698765421;Timm.Reinholdt@gmail.com;None;None;None;Detmolderstrasse;48;33102;Paderborn;3;2018-09-09;1250;Busdorfmauer;22;33098;Paderborn - Kernstadt;None;None;None;None;None;None;None;None;None;None;None;\n"
				+ "";
		String testkundeUnternehmen = "None;None;None;+495251396847;Support@Oqusoft.de;Oqusoft;AG;3131123456789;Detmolderstrasse;69;33102;Paderborn;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;None;\n";

		CustomerRelatedData customerDataPrivate = StringConverter.parseStringToCustomerData(testkundePrivat);
		CustomerRelatedData customerDataCommercial = StringConverter.parseStringToCustomerData(testkundeUnternehmen);

		try {
			generatePDFFromCustomerData(customerDataPrivate);
			// generatePDFFromCustomerData(customerDataCommercial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

		// Creating PDF document object
		PDDocument document = new PDDocument();

		ArrayList<Offer> offers = customerData.getOffers();
		ArrayList<Order> orders = customerData.getOrders();
		ArrayList<Invoice> invoices = customerData.getInvoices();

		int offersCount = offers.size();
		int ordersCount = orders.size();
		int invoicesCount = invoices.size();

		System.out.println("PDF created");

		for (int i = 0; i <= offersCount; i++) {
			PDPage blankPage = new PDPage();
			document.addPage(blankPage);
		}

		PDDocumentInformation pdd = document.getDocumentInformation();

		pdd.setAuthor("Malermeister Mustermann");
		pdd.setTitle("Kundenbezogene Daten f�r " + last_name + ", " + first_name);

		// PDFont font = PDTrueTypeFont.loadTTF(document, "Arial.ttf");

		PDPage titlePage = document.getPage(0);

		PDPageContentStream csBasicData = new PDPageContentStream(document, titlePage);

		csBasicData.beginText();

		String titlePart1 = "Kundenbezogene Daten für ";
		String titlePart2;
		String heading = "Generelle Daten: ";
		String line1;
		String line2;
		String line3 = "Adresse:        " + street + " " + house_no + ", " + postal_code + " " + city;
		String line4 = "Telefonnummer:  " + telephone_no;
		String line5 = "E-Mail Adresse: " + email;

		if (isPrivatePerson) {
			titlePart2 = last_name + ", " + first_name;
			line1 = "Name:         " + last_name + ", " + first_name;
			line2 = "Geburtsdatum: " + date_of_birth;
		} else {
			titlePart2 = company_name + " " + legal_form;
			line1 = "Firmenname:   " + company_name + " " + legal_form;
			line2 = "Steuernummer: " + tax_id;
		}

		csBasicData.setFont(PDType1Font.HELVETICA_BOLD, 32);
		csBasicData.newLineAtOffset(50, 730);
		csBasicData.setLeading(35f);

		// Adding text in the form of string
		System.out.println(titlePart1);
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
		csBasicData.setLeading(14.5f);
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
		csBasicData.close();

		for (int i = 1; i <= offersCount; i++) {
			PDPage currentPage = document.getPage(i);
			PDPageContentStream currentCS = new PDPageContentStream(document, currentPage);

			Offer currentOffer = offers.get(i - 1);
			String offerID = currentOffer.getId();
			String date_of_creation = currentOffer.getDate_of_creation();
			String gross_price = currentOffer.getGross_price();
			String real_estate_adress = currentOffer.getReal_estate_street() + " "
					+ currentOffer.getReal_estate_house_no() + ", " + currentOffer.getReal_estate_postal_code() + " "
					+ currentOffer.getReal_estate_city();

			String offerTitle = "Angebot Nr. " + i + ": ";
			String offerLine1 = "Angebotsnummer: " + offerID;
			String offerLine2 = "Erstelldatum: " + date_of_creation;
			String offerLine3 = "Geschätzter Preis: " + gross_price;
			String offerLine4 = "An folgender Adresse: " + real_estate_adress;

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

			for (int j = 0; j < ordersCount; j++) {
				Order currentOrder = orders.get(j);
				if (offerID.equals(currentOrder.getOffer_id())) {
					hasCorrespondingOrder = true;
					indexOfCorrespondingOrder = j;
					for (int k = 0; k < invoicesCount; k++) {
						Invoice currentInvoice = invoices.get(k);
						if (currentOrder.getId().equals(currentInvoice.getOrder_id())) {
							hasCorrespondingInvoice = true;
							indexOfCorrespondingInvoice = k;
						} else {
							hasCorrespondingInvoice = false;
						}
					}
				} else {
					hasCorrespondingOrder = false;
					hasCorrespondingInvoice = false;
				}
			}

			currentCS.beginText();

			currentCS.setFont(PDType1Font.HELVETICA_BOLD, 28);
			currentCS.newLineAtOffset(10, 730);
			currentCS.showText("Gespeicherte Angebote, Aufträge und Rechnungen: ");

			currentCS.endText();

			currentCS.beginText();

			currentCS.setFont(PDType1Font.HELVETICA, 20);
			currentCS.newLineAtOffset(50, 650);
			currentCS.showText(offerTitle);

			currentCS.endText();

			currentCS.beginText();

			currentCS.setFont(PDType1Font.HELVETICA, 16);
			currentCS.setLeading(14.5f);
			currentCS.newLineAtOffset(50, 620);
			currentCS.showText(offerLine1);
			currentCS.newLine();
			currentCS.showText(offerLine2);
			currentCS.newLine();
			currentCS.showText(offerLine3);
			currentCS.newLine();
			currentCS.showText(offerLine4);

			currentCS.endText();

			if (hasCorrespondingOrder) {
				Order order = orders.get(indexOfCorrespondingOrder);
				String orderID = order.getId();
				String orderDate_of_creation = order.getDate_of_creation();
				String begin_date = order.getBegin_date();
				String end_date = order.getEnd_date();
				String offerID_fromOrder = order.getOffer_id();

				orderTitle += indexOfCorrespondingOrder + 1 + ": ";
				orderLine1 += orderID;
				orderLine2 += orderDate_of_creation;
				orderLine3 += begin_date;
				orderLine4 += end_date;
				orderLine5 += offerID_fromOrder;

				if (hasCorrespondingInvoice) {
					Invoice invoice = invoices.get(indexOfCorrespondingInvoice);

					String invoiceID = invoice.getId();
					String amount = invoice.getAmount();
					String invoice_date = invoice.getInvoice_date();
					String incoterms = invoice.getIncoterms();
					String comment = invoice.getComment();
					String orderID_fromInvoice = invoice.getOrder_id();

					invoiceTitle += indexOfCorrespondingInvoice + 1 + ": ";
					invoiceLine1 += invoiceID;
					invoiceLine2 += amount;
					invoiceLine3 += invoice_date;
					invoiceLine4 += incoterms;
					invoiceLine5 += comment;
					invoiceLine6 += orderID_fromInvoice;
				} else {

				}
			} else {

			}

			if (hasCorrespondingOrder) {
				currentCS.beginText();

				currentCS.setFont(PDType1Font.HELVETICA, 20);
				currentCS.newLineAtOffset(50, 450);
				currentCS.showText(orderTitle);

				currentCS.endText();

				currentCS.beginText();

				currentCS.setFont(PDType1Font.HELVETICA, 16);
				currentCS.setLeading(14.5f);
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

				if (hasCorrespondingInvoice) {
					currentCS.beginText();

					currentCS.setFont(PDType1Font.HELVETICA, 20);
					currentCS.newLineAtOffset(50, 250);
					currentCS.showText(invoiceTitle);

					currentCS.endText();

					currentCS.beginText();

					currentCS.setFont(PDType1Font.HELVETICA, 16);
					currentCS.setLeading(14.5f);
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
					currentCS.showText(invoiceLine6);

					currentCS.endText();
				} else {
					currentCS.beginText();

					currentCS.setFont(PDType1Font.HELVETICA, 20);
					currentCS.newLineAtOffset(50, 250);
					currentCS.showText("Zu diesem Auftrag gibt es keine Rechnung!");

					currentCS.endText();
				}
			} else {
				currentCS.beginText();

				currentCS.setFont(PDType1Font.HELVETICA, 20);
				currentCS.newLineAtOffset(50, 450);
				currentCS.showText("Zu diesem Angebot gibt es weder Auftrag noch Rechnung!");

				currentCS.endText();
			}

			// System.out.println("Gibt es einen Auftrag? " + hasCorrespondingOrder +
			// indexOfCorrespondingOrder + "Und eine Rechnung? " + hasCorrespondingInvoice +
			// indexOfCorrespondingInvoice);
			currentCS.close();

		}

		// Saving the document
		document.save("C://Users/jana.einheuser/Desktop/PDFs/Dreckscheisse.pdf");

		// System.out.println("Page added");

		// Closing the document
		document.close();
		return ("C://Users/jana.einheuser/Desktop/PDFs/Dreckscheisse.pdf");

	}
}
