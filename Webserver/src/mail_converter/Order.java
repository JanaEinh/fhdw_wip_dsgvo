package mail_converter;

public class Order {
	private String id;
	private String date_of_creation;
	private String begin_date;
	private String end_date;
	private String offer_id;
	
	public Order(String id, String date_of_creation, String begin_date, String end_date, String offer_id) {
		this.id = id;
		this.date_of_creation = DateConverter.convertFromSQLToNormal(date_of_creation);
		if(begin_date.equals("None")) {
			this.begin_date = "Die Ausf�hrung des Auftrags wurde noch nicht begonnen.";
			this.end_date = "Der Auftrag wurde noch nicht abgeschlossen.";
		}
		else if(end_date.equals("None")) {
			this.begin_date = DateConverter.convertFromSQLToNormal(begin_date);
			this.end_date = "Der Auftrag wurde noch nicht abgeschlossen.";
		}
		else {
			this.begin_date = DateConverter.convertFromSQLToNormal(begin_date);
			this.end_date = DateConverter.convertFromSQLToNormal(end_date);
		}
		this.offer_id = offer_id;
	}
	
	public String toString() {
		String orderAsString = "Kundenauftrags-ID: " + this.id + "\n" + "Angelegt am: " + this.date_of_creation + "\n" + "Beginn der Ausführung: " + 
								this.begin_date + "\n" + "Ende der Ausführung: " + this.end_date + "\n" + "Zugehörige Angebots-ID: " + this.offer_id + "\n";
		return orderAsString;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate_of_creation() {
		return date_of_creation;
	}

	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
}
