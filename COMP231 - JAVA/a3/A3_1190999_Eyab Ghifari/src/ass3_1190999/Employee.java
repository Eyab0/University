package ass3_1190999;

import java.util.Date;

public class Employee {

	// declare variables of Employee attributes
	private String nameOfEmployee;
	private String numberOfEmployee;
	private Date hireDateOfEmployee;
	private Address addressOfEmployee;

	@SuppressWarnings("deprecation")
	// none-argument constructor
	public Employee() {
		// fill the Employee attributes with default values
		nameOfEmployee = "Unavailable";
		numberOfEmployee = "XXX–L";
		hireDateOfEmployee = new Date();
		setHireDateOfEmployee((hireDateOfEmployee.getYear() + 1900), (hireDateOfEmployee.getMonth() + 1),
				hireDateOfEmployee.getDate());
		addressOfEmployee = new Address();
	}

	// main argument constructor
	public Employee(String nameOfEmployee, String numberOfEmployee, int year, int month, int day,
			Address addressOfEmployee) {

		// fill the Employee attributes from arguments
		this.nameOfEmployee = nameOfEmployee;
		setNumberOfEmployee(numberOfEmployee);
		setHireDateOfEmployee(year, month, day);
		this.addressOfEmployee = addressOfEmployee;
	}

	/**
	 * 
	 * @return name of employee
	 */
	public String getNameOfEmployee() {
		return nameOfEmployee;
	}

	/**
	 * set the name of employee
	 * 
	 * @param name Of Employee
	 */
	public void setNameOfEmployee(String nameOfEmployee) {
		this.nameOfEmployee = nameOfEmployee;
	}

	/**
	 * 
	 * @return number of employee
	 */
	public String getNumberOfEmployee() {
		return numberOfEmployee;
	}

	/**
	 * set number of employee
	 * 
	 * @param number Of Employee
	 */
	public void setNumberOfEmployee(String numberOfEmployee) {

		if (isEmpNumberValid(numberOfEmployee))
			this.numberOfEmployee = numberOfEmployee;
		else
			this.numberOfEmployee = "Invalid Employee Number";
	}


	/**
	 * 
	 * @return hire date of employee
	 */
	@SuppressWarnings("deprecation")
	public String getHireDateOfEmployee() {
		String hireDate = "";
		hireDate += hireDateOfEmployee.getDate() + "-" + (hireDateOfEmployee.getMonth() + 1) + "-"
				+ hireDateOfEmployee.getYear();
		return hireDate;
	}


	/**
	 * set the hire date of employee
	 * 
	 * @param year  year of hire date
	 * @param month month of hire date
	 * @param day   day of hire date
	 */
	@SuppressWarnings("deprecation")
	public void setHireDateOfEmployee(int year, int month, int day) {
		boolean checkDate = true;

		// check if the user enter a wrong day number [1-31]
		if (day > 31)
			checkDate = false;
		// check if the user enter a wrong month number[1-12]
		if (month > 12)
			checkDate = false;
		// check if day and month enter correctly
		if (checkDate)
			hireDateOfEmployee = new Date(year, month - 1, day);
		else {
			// Default case
			// check if the day and month enter wrong >> the program will stopped
			System.out.println("Invalid Date !");
			System.out.println("Exit...");
			System.exit(0);

		}
	}

	/**
	 * 
	 * @return address of the employee
	 */
	public Address getAddressOfEmployee() {
		return addressOfEmployee;
	}

	/**
	 * set address of the employee
	 * 
	 * @param address Of Employee
	 */

	public void setAddressOfEmployee(Address addressOfEmployee) {
		this.addressOfEmployee = addressOfEmployee;
	}

	/**
	 * check if the employee number id valid or not
	 * 
	 * @param numberOfEmployee
	 * @return boolean
	 */
	public boolean isEmpNumberValid(String numberOfEmployee) {
		boolean checker = false;
		// number length must be 5
		if (numberOfEmployee.length() == 5) {
			// number must have '-' in index 3
			if (numberOfEmployee.charAt(3) == '-') {
				// format xxx–L
				// where each x is a digit within the range 0–9
				// L is a letter within the range A–M
				if (numberOfEmployee.charAt(4) >= 'A' && numberOfEmployee.charAt(4) <= 'M') {
					for (int i = 0; i < 3; i++) {
						if (numberOfEmployee.charAt(i) >= '0' && numberOfEmployee.charAt(i) <= '9')
							checker = true;
						else
							return false;

					}

				}
			}

		}

		return checker;

	}

	@Override
	/**
	 * @return all info about employee
	 */
	public String toString() {

		String EmployeeInfo = "";
		EmployeeInfo += "----------------------------------------" + "\n";
		EmployeeInfo += "          Employee info : \n";
		EmployeeInfo += "----------------------------------------" + "\n";
		EmployeeInfo += "Emoloyee Name: " + nameOfEmployee + "\n";
		EmployeeInfo += "Emoloyee Number: " + numberOfEmployee + "\n";
		EmployeeInfo += "Hire Date: " + getHireDateOfEmployee() + "\n";
		EmployeeInfo += addressOfEmployee.toString();

		return EmployeeInfo;

	}

}
