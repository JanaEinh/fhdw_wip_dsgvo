//Author: Niklas

package mail_converter;

public class Offer {
	private String id;
	private String date_of_creation;
	private String gross_price;
	private String real_estate_street;
	private String real_estate_house_no;
	private String real_estate_postal_code;
	private String real_estate_city;

	public Offer(String id, String date_of_creation, String gross_price, String real_estate_street,
			String real_estate_house_no, String real_estate_postal_code, String real_estate_city) {
		this.id = id;
		this.date_of_creation = date_of_creation;
		this.gross_price = gross_price;
		this.real_estate_street = real_estate_street;
		this.real_estate_house_no = real_estate_house_no;
		this.real_estate_postal_code = real_estate_postal_code;
		this.real_estate_city = real_estate_city;
	}

	public String toString() {
		String offerAsString = "Angebots-ID: " + this.id + "\n" + "Angelegt am: " + this.date_of_creation + "\n"
				+ "Geschätzter Preis: " + this.gross_price + "€\n" + "Ausführungsadresse: " + this.real_estate_street
				+ " " + this.real_estate_house_no + ", " + this.real_estate_postal_code + " " + this.real_estate_city
				+ "\n";
		return offerAsString;
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

	public String getGross_price() {
		return gross_price;
	}

	public void setGross_price(String gross_price) {
		this.gross_price = gross_price;
	}

	public String getReal_estate_street() {
		return real_estate_street;
	}

	public void setReal_estate_street(String real_estate_street) {
		this.real_estate_street = real_estate_street;
	}

	public String getReal_estate_house_no() {
		return real_estate_house_no;
	}

	public void setReal_estate_house_no(String real_estate_house_no) {
		this.real_estate_house_no = real_estate_house_no;
	}

	public String getReal_estate_postal_code() {
		return real_estate_postal_code;
	}

	public void setReal_estate_postal_code(String real_estate_postal_code) {
		this.real_estate_postal_code = real_estate_postal_code;
	}

	public String getReal_estate_city() {
		return real_estate_city;
	}

	public void setReal_estate_city(String real_estate_city) {
		this.real_estate_city = real_estate_city;
	}
}
