//Author: Niklas

package mail_converter;

public class Customer {
	private boolean isPrivatePerson;
	private String first_name;
	private String last_name;
	private String date_of_birth;
	private String telephone_no;
	private String email;
	private String company_name;
	private String legal_form;
	private String tax_id;
	private String street;
	private String house_no;
	private String postal_code;
	private String city;

	public Customer(String first_name, String last_name, String date_of_birth, String telephone_no, String email,
			String company_name, String legal_form, String tax_id, String street, String house_no, String postal_code,
			String city) {
		if (first_name.equals("None")) {
			isPrivatePerson = false;
		} else {
			isPrivatePerson = true;
		}

		this.first_name = first_name;
		this.last_name = last_name;
		this.date_of_birth = date_of_birth;
		this.telephone_no = telephone_no;
		this.email = email;
		this.company_name = company_name;
		this.legal_form = legal_form;
		this.tax_id = tax_id;
		this.street = street;
		this.house_no = house_no;
		this.postal_code = postal_code;
		this.city = city;
	}

	public Customer() {

	}

	public String toString() {
		String customerAsString;
		if (isPrivatePerson) {
			customerAsString = "Es handelt sich um einen Privatkunden. Die Kundendaten lauten: \n\n" + "Name: "
					+ this.last_name + ", " + this.first_name + "\n" + "Geboren am: " + this.date_of_birth + "\n"
					+ "Wohnhaft in: " + this.street + " " + this.house_no + ", " + this.postal_code + " " + this.city
					+ "\n" + "Telefonnummer: " + this.telephone_no + "\n" + "Email-Adresse: " + this.email + "\n";
		} else {
			customerAsString = "Es handelt sich um einen gewerblichen Kunden. Die Kundendaten lauten: \n\n"
					+ "Firmename und Rechtsform: " + this.company_name + " " + this.legal_form + "\n" + "Steuernummer: "
					+ this.tax_id + "\n" + "Ansässig in: " + this.street + " " + this.house_no + ", " + this.postal_code
					+ " " + this.city + "\n" + "Telefonnummer: " + this.telephone_no + "\n" + "Email-Adresse: "
					+ this.email + "\n";
		}
		return customerAsString;
	}

	public boolean isPrivatePerson() {
		return isPrivatePerson;
	}

	public void setPrivatePerson(boolean isPrivatePerson) {
		this.isPrivatePerson = isPrivatePerson;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone_no() {
		return telephone_no;
	}

	public void setTelephone_no(String telephone_no) {
		this.telephone_no = telephone_no;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse_no() {
		return house_no;
	}

	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLegal_form() {
		return legal_form;
	}

	public void setLegal_form(String legal_form) {
		this.legal_form = legal_form;
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}
}
