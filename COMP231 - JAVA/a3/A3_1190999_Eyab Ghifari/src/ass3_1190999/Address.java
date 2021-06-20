package ass3_1190999;

public class Address {

	// declare variables of Address attributes
	private String street;
	private String state;
	private String city;
	private String zipCode;

	// none-argument constructor
	public Address() {
		// fill the Address attributes with default values
		this("Unavailable", "Unavailable", "Unavailable", "Unavailable");
	}

	// main argument constructor
	public Address(String street, String state, String city, String zipCode) {
		// fill the Address attributes from arguments
		this.street = street;
		setState(state);
		setCity(city);
		setZipCode(zipCode);
	}

	/**
	 * 
	 * @return street name
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * set street name
	 * 
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 
	 * @return state name
	 */
	public String getState() {
		return state;
	}

	/**
	 * set state name
	 * 
	 * @param state
	 */
	public void setState(String state) {

		if (iscontainsDigit(state))
			this.state = "Invalid Name";
		else
			this.state = state;

	}

	/**
	 * 
	 * @return city name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * set city name
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		if (iscontainsDigit(city))
			this.city = "Invalid Name";
		else
			this.city = city;
	}

	/**
	 * 
	 * @return zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * set zip code
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {

		// check if the zip code entered by the user is valid
		if (zipCode == "Unavailable")
			this.zipCode = zipCode;
		else if (isValidZipCode(zipCode))
			this.zipCode = zipCode;
		else
			this.zipCode = "Invalid zip code";

	}

	/**
	 * check if the zip code is valid this is only for palestine zip codes
	 * 
	 * @param zipCode
	 * @return boolean
	 */
	public boolean isValidZipCode(String zipCode) {

		/**
		 * in palesitne zip code length must be 5 or 6 and not contain letters
		 */
		boolean checker = true;
		if (zipCode.length() == 5) {
			// check if zip code contain letters
			for (int i = 0; i < 5; i++) {
				if (Character.isLetter(zipCode.charAt(i))) {
					return false;
				}
			}
		}

		else if (zipCode.length() == 6) {
			// check if zip code contain letters
			for (int i = 0; i < 5; i++) {
				if (Character.isLetter(zipCode.charAt(i))) {
					return false;
				}
			}
		} else
			return false;

		return checker;
	}

	/**
	 * check if the a string(city,state) contain digits
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean iscontainsDigit(String name) {
		boolean checker = false;

		// if string not empty or null
		if (name != null && !name.isEmpty()) {
			// check if contain digits
			for (char c : name.toCharArray()) {
				if (checker = Character.isDigit(c)) {
					break;
				}
			}
		}

		return checker;
	}

	@Override
	/**
	 * @return address info
	 */
	public String toString() {

		String address = "";
		address += "\n" + "----------------------------------------";
		address += "\n" + "          Address info : \n";
		address += "----------------------------------------" + "\n";
		address += "Street: " + street + "\n";
		address += "State: " + state + "\n";
		address += "City: " + city + "\n";
		address += "zipCode: " + zipCode + "\n";

		return address;
	}

}
