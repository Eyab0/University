import java.util.Date;

public class Car {
	// declare variables of car attributes
	private String PlateNo;
	private int yearManufacture;
	private int monthManufacture;
	private String color;
	private double price;
	private String manufactureBy;
	private int guaranteeDueYear;
	private int guaranteeDueMonth;

	@SuppressWarnings("deprecation")
	// none-argument constructor
	public Car() {
		Date currentDate = new Date();
		// fill the car attributes
		PlateNo = "0123-A";
		yearManufacture = (currentDate.getYear() + 1900);
		monthManufacture = (currentDate.getMonth() + 1);
		color = "red";
		price = 50000.00;
		manufactureBy = "Mercedes";
		guaranteeDueYear = (currentDate.getYear() + 1900);
		guaranteeDueMonth = (currentDate.getMonth() + 1) + 6;
		/*
		 * check if coming guarantee month grater of 12 to correct the values of the
		 * year and month guarantee
		 */ if (guaranteeDueMonth > 12) {
			guaranteeDueMonth -= 12;
			guaranteeDueYear += 1;
		}

	}

	// main argument constructor
	public Car(String PlateNo, int yearManufacture, int monthManufacture, String color, double price,
			String manufactureBy, int gauranteeDueYear, int gauranteeDueMonth) {

		// fill the car attributes
		this.PlateNo = PlateNo;
		this.yearManufacture = yearManufacture;
		this.monthManufacture = monthManufacture;
		this.color = color;
		this.price = price;
		this.manufactureBy = manufactureBy;
		this.guaranteeDueYear = gauranteeDueYear;
		this.guaranteeDueMonth = gauranteeDueMonth;
	}

	/**
	 * 
	 * @return the year of Manufacture
	 */
	public int getYearManufacture() {
		return yearManufacture;
	}

	/**
	 * 
	 * @return the month of Manufacture
	 */
	public int getMonthManufacture() {
		return monthManufacture;
	}

	/**
	 * 
	 * @return the car color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 
	 * @return the car price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * to set the year of Manufacture
	 * 
	 * @param yearManufacture
	 */
	public void setYearManufacture(int yearManufacture) {

		this.yearManufacture = yearManufacture;
	}

	/**
	 * to set the month of Manufacture
	 * 
	 * @param monthManufacture
	 */
	public void setMonthManufacture(int monthManufacture) {

		this.monthManufacture = monthManufacture;
	}

	/**
	 * to set the car color
	 * 
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * to set the year and month of Guarantee
	 * 
	 * @param guaranteeDueMonth
	 * @param guaranteeDueYear
	 */
	public void setGuarantee(int guaranteeDueMonth, int guaranteeDueYear) {

		this.guaranteeDueMonth = guaranteeDueMonth;
		this.guaranteeDueYear = guaranteeDueYear;

	}

	/**
	 * 
	 * @return the name of Manufacture company
	 */
	public String getManufactureBy() {
		return manufactureBy;
	}

	/**
	 * 
	 * @return age of the car
	 */
	@SuppressWarnings("deprecation")
	public String getCarAge() {
		Date currentDate = new Date();
		String CarAge = null;

		// check if The car is not yet manufactured
		if (yearManufacture > (currentDate.getYear() + 1900) || (((currentDate.getYear() + 1900) == yearManufacture)
				&& ((currentDate.getMonth() + 1) < monthManufacture))) {
			CarAge = "The car is not yet manufactured !";

		}
		// check if the enter month is invalid
		else if (monthManufacture > 12 || monthManufacture < 1 || guaranteeDueMonth > 12 || guaranteeDueMonth < 1) {
			CarAge = "the car not have an age !";
		}
		// Default case
		else {
			int ageYear, ageMonth;
			// check if the Manufacture month is grater than current month
			if (monthManufacture > (currentDate.getMonth() + 1)) {

				ageYear = (currentDate.getYear() + 1900) - yearManufacture - 1;
				ageMonth = 12 - (monthManufacture - (currentDate.getMonth() + 1));
			}
			// Default case
			else {
				ageYear = (currentDate.getYear() + 1900) - yearManufacture;
				ageMonth = (currentDate.getMonth() + 1) - monthManufacture;

			}
			CarAge = "car's age : " + "\"Years : " + ageYear + " & " + "months : " + ageMonth + "\"";
		}
		return CarAge.toUpperCase();
	}

	/**
	 * 
	 * @return all informations about the car object
	 */
	@SuppressWarnings("deprecation")
	public String printCarInfo() {
		Date currentDate = new Date();
		String carInfo = null;

		// check if The car is not yet manufactured
		if (yearManufacture > (currentDate.getYear() + 1900) || (((currentDate.getYear() + 1900) == yearManufacture)
				&& ((currentDate.getMonth() + 1) < monthManufacture))) {
			carInfo = "The car has not been produced yet!";

		}

		// check if the enter month is invalid
		else if (monthManufacture > 12 || monthManufacture < 1 || guaranteeDueMonth > 12 || guaranteeDueMonth < 1) {
			carInfo = "Invalid date!\r\n" + "Make sure the date is in the range of 1 to 12.";
		}

		// Default case
		else {
			carInfo = "1) car plate no : " + PlateNo + "\n";
			carInfo += "2) car color : " + color + "\n";
			carInfo += "3) cae is manufacture By : " + manufactureBy + "\n";
			carInfo += "4) car price : " + price + "\n";
			carInfo += "5) car Manufacture date (month/year) : " + monthManufacture + "/" + yearManufacture + "\n";
			carInfo += "6) car guarantee Due date (month/year) : " + guaranteeDueMonth + "/" + guaranteeDueYear + "\n";
			carInfo += "7) " + getCarAge() + "\n";
			carInfo += "8) " + getCalculateGuarantee();
		}
		return carInfo.toUpperCase();
	}

	/**
	 * 
	 * @return remaining time for Guarantee
	 */
	@SuppressWarnings("deprecation")
	public String getCalculateGuarantee() {
		Date currentDate = new Date();
		String timeLeftOfGuarantee = null;

		// check if the current date is grater of the Guarantee date
		if (((currentDate.getYear() + 1900) > guaranteeDueYear) || (((currentDate.getYear() + 1900) == guaranteeDueYear)
				&& ((currentDate.getMonth() + 1) > guaranteeDueMonth))) {
			timeLeftOfGuarantee = "guarantee expired !";

		}

		// check if the enter month is invalid
		else if (monthManufacture > 12 || monthManufacture < 1 || guaranteeDueMonth > 12 || guaranteeDueMonth < 1) {
			timeLeftOfGuarantee = "the car not have a guarantee date !";
		}

		// Default case
		else {
			int leftYear, leftMonth;
			// check if the Guarantee month is grater of the current month
			if (guaranteeDueMonth < (currentDate.getMonth() + 1)) {
				leftYear = guaranteeDueYear - (currentDate.getYear() + 1900) - 1;
				leftMonth = 12 - ((currentDate.getMonth() + 1) - guaranteeDueMonth);
			} else {
				leftYear = guaranteeDueYear - (currentDate.getYear() + 1900);
				leftMonth = guaranteeDueMonth - (currentDate.getMonth() + 1);

			}
			timeLeftOfGuarantee = "remaining time for the guarantee : " + "\"Years : " + leftYear + " & " + "months : "
					+ leftMonth + "\"";

		}
		return timeLeftOfGuarantee.toUpperCase();

	}

}
