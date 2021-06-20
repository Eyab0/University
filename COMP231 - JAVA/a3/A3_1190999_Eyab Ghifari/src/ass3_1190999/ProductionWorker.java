package ass3_1190999;

public class ProductionWorker extends Employee {

	public static final int DAY_SHIFT = 1;
	public static final int NIGHT_SHIFT = 2;

	// declare variables of ProductionWorker attributes
	private int shift;
	private double payRateByHour;
	private int numOfHoursPerMonth;

	// none-argument constructor
	public ProductionWorker() {
		// fill the ProductionWorker attributes with default values
		super();
		shift = 1;
		payRateByHour = 0;
		numOfHoursPerMonth = 0;

	}

	// main argument constructor
	public ProductionWorker(String nameOfEmployee, String numberOfEmployee, int year, int month, int day,
			Address addressOfEmployee, int shift, double payRateByHour, int numOfHoursPerMonth) {
		// fill the ProductionWorker attributes from arguments
		super(nameOfEmployee, numberOfEmployee, year, month, day, addressOfEmployee);
		setShift(shift);
		setPayRateByHour(payRateByHour);
		setNumOfHoursPerMonth(numOfHoursPerMonth);

	}

	/**
	 * "1"-> DAY_SHIFT or "2"-> NIGHT_SHIFT
	 * 
	 * @return shift number
	 * 
	 */
	public int getShift() {
		return shift;
	}

	/**
	 * set shift number
	 * 
	 * @param shift
	 */
	public void setShift(int shift) {
		if (shift == DAY_SHIFT || shift == NIGHT_SHIFT)
			this.shift = shift;
		else {

			// Default case
			// check if the shift number invalid >> the program will stopped
			System.out.println("Invalid shift number !");
			System.out.println("Exit...");
			System.exit(0);

		}

	}

	/**
	 * 
	 * @return pay Rate By Hour number
	 */
	public double getPayRateByHour() {
		return payRateByHour;
	}

	/**
	 * set pay Rate By Hour number
	 * 
	 * @param payRateByHour
	 */
	public void setPayRateByHour(double payRateByHour) {
		this.payRateByHour = Math.abs(payRateByHour);
	}

	/**
	 * 
	 * @return num Of Hours Per Month
	 */
	public int getNumOfHoursPerMonth() {
		return numOfHoursPerMonth;
	}

	/**
	 * set num Of Hours Per Month
	 * 
	 * @param numOfHoursPerMonth
	 */
	public void setNumOfHoursPerMonth(int numOfHoursPerMonth) {

		this.numOfHoursPerMonth = Math.abs(numOfHoursPerMonth);
	}

	/**
	 * COMMPETITIVE PART ! check if the employee get a bonus days&hours or not
	 * 
	 * @return status of getting bonus
	 */
	public String getStatus() {

		String status = "";
		status += "Status of the bouns of Production Worker :\n";
		// check if is employee work in day or night shift
		if (shift == DAY_SHIFT) {
			double daysWork, bonusHours;
			double bonusDays;
			daysWork = ((double) numOfHoursPerMonth) / 8;
			// if employee work all require hours&days
			if (daysWork == 26.0) {
				status += ">>working hours completed without bouns<<\n";
				// if employee not work all require hours&days
			} else if (daysWork < 26.0) {
				status += ">>uncompleted working hours !<<\n";
				// if employee work all require hours&days with bonus hours&days
			} else if (daysWork > 26.0) {
				bonusDays = daysWork - 26;
				bonusHours = numOfHoursPerMonth - 208;
				status += ">>working hours completed with bouns<<\n";
				status += ">>Bonus days: " + ((int) bonusDays) + "\n";
				status += ">>bouns hours: " + bonusHours + "\n";
			}
			// check if is employee work in day or night shift
		} else if (shift == NIGHT_SHIFT) {

			double daysWork, bonusHours;
			double bonusDays;
			daysWork = ((double) numOfHoursPerMonth) / 7;
			// if employee work all require hours&days
			if (daysWork == 26.0) {
				status += ">>working hours completed without bouns<<\n";
				// if employee not work all require hours&days
			} else if (daysWork < 26.0) {
				status += ">>uncompleted working hours !<<" + "\n";
				// if employee work all require hours&days with bonus hours&days
			} else if (daysWork > 26.0) {
				bonusDays = daysWork - 26;
				bonusHours = numOfHoursPerMonth - 182;
				status += ">>working hours completed with bouns<<" + "\n";
				status += ">>Bonus days: " + ((int) bonusDays) + "\n";
				status += ">>bouns hours: " + bonusHours + "\n";
			}
		}
		// Default case
		else
			status += "Unavailable! \n";
		return status;
	}

	/**
	 * 
	 * @return total salary
	 */
	public double getTotalSalary() {

		double netSalary = 0, bonus;

		// check if is employee work in day or night shift
		switch (getShift()) {
		case DAY_SHIFT: {

			// work with bonus days&hours
			if (numOfHoursPerMonth > 26 * 8) {
				netSalary += 208 * payRateByHour;
				bonus = numOfHoursPerMonth - 208;
				netSalary += bonus * 1.25 * payRateByHour;
			}
			// work without bonus days&hours
			else
				netSalary += numOfHoursPerMonth * payRateByHour;

		}

		case NIGHT_SHIFT: {

			// work with bonus days&hours
			if (numOfHoursPerMonth > 26 * 7) {
				netSalary += 208 * payRateByHour;
				bonus = numOfHoursPerMonth - 208;
				netSalary += bonus * 1.5 * payRateByHour;
			}
			// work without bonus days&hours
			else
				netSalary += numOfHoursPerMonth * payRateByHour;

		}
		}

		return netSalary;
	}

	@Override
	/**
	 * @return all info of Production Worker
	 */
	public String toString() {
		String ProductionWorkerInfo = super.toString();
		ProductionWorkerInfo += "\n" + "----------------------------------------" + "\n";
		ProductionWorkerInfo += "      Production Worker Info : \n";
		ProductionWorkerInfo += "----------------------------------------" + "\n";
		ProductionWorkerInfo += "Shift: " + shift + "\n";
		ProductionWorkerInfo += "pay Rate By Hour: " + payRateByHour + "\n";
		ProductionWorkerInfo += "number Of Hours Per Month: " + numOfHoursPerMonth + "\n";
		ProductionWorkerInfo += getStatus();
		ProductionWorkerInfo += "Production Worker Toatal Salary: " + String.format("%.2f", getTotalSalary()) + "$"
				+ "\n";

		return ProductionWorkerInfo;
	}

}
