package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Owner implements Cloneable {
	// declare variables of Owner attributes
	String name;
	String registrationNo;
	String address;
	String telephoneNumber;
	Calendar dateOfRegistration = new GregorianCalendar();

//	 none-argument constructor
	public Owner() {

		this("Unavailable", "Unavailable", "Unavailable", "Unavailable", new GregorianCalendar());
	}

	// main argument constructor
	public Owner(String name, String registrationNo, String address, String tel, GregorianCalendar dateOfRegistration) {
		// fill the Owner attributes from arguments
		setName(name);
		this.registrationNo = registrationNo;
		this.address = address;
		this.telephoneNumber = tel;
		this.dateOfRegistration = dateOfRegistration;
	}

	String getName() {
		return name;
	}


	void setName(String name) {
		if (iscontainsDigit(name))
			this.name = "Invalid Name";
		else
			this.name = name;
	}

	String getRegistrationNo() {
		return registrationNo;
	}

	/**
	 * 
	 * @param registrationNo
	 */
	void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 */
	void setAddress(String address) {
		this.address = address;
	}

	String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * 
	 * @param tel
	 */
	void setTelephoneNumber(String tel) {
		this.telephoneNumber = tel;
	}

	GregorianCalendar getDateOfRegistration() {

		return (GregorianCalendar) dateOfRegistration;
	}

	/**
	 * 
	 * @param dateOfRegistration
	 */
	void setDateOfRegistration(GregorianCalendar dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	/**
	 * check if the name contain digits
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
	public String toString() {
		String truckInfo = "";
		truckInfo += "owner name: " + name + "\n";
		truckInfo += "registrationNo: " + registrationNo + "\n";
		truckInfo += "address: " + address + "\n";
		truckInfo += "date Of Registration: " + dateOfRegistration.get(Calendar.DATE) + "-"
				+ (dateOfRegistration.get(Calendar.MONTH) + 1) + "-" + dateOfRegistration.get(Calendar.YEAR) + "\n";

		return truckInfo;
	}
	@Override

	protected Object clone() throws CloneNotSupportedException {
		return (Owner) super.clone();
	}

}
