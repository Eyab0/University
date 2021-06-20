package ass3_1190999;

public class ShiftSupervisor extends Employee {

	// declare variables of ShiftSupervisor attributes
	private double monthlySalary;
	private double monthlyProductionBonusEarned;
	private int numberProductsProduced;
	private int numberProductsNeedToProduced;

	// main argument constructor
	public ShiftSupervisor(String nameOfEmployee, String numberOfEmployee, int year, int month, int day,
			Address addressOfEmployee, double monthlySalary, double monthlyProductionBonusEarned,
			int numberProductsProduced, int numberProductsNeedToProduced) {
		// fill the ShiftSupervisor attributes from arguments
		super(nameOfEmployee, numberOfEmployee, year, month, day, addressOfEmployee);
		this.monthlySalary = monthlySalary;
		this.monthlyProductionBonusEarned = monthlyProductionBonusEarned;
		this.numberProductsProduced = numberProductsProduced;
		this.numberProductsNeedToProduced = numberProductsNeedToProduced;
	}

	/**
	 * 
	 * @return monthly Salary
	 */
	public double getMonthlySalary() {
		return monthlySalary;
	}

	/**
	 * set monthly Salary
	 * 
	 * @param monthlySalary
	 */
	public void setMonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	/**
	 * 
	 * @return monthly Production Bonus Earned
	 */
	public double getMonthlyProductionBonusEarned() {
		return monthlyProductionBonusEarned;
	}

	/**
	 * set monthly Production Bonus Earned
	 * 
	 * @param monthlyProductionBonusEarned
	 */
	public void setMonthlyProductionBonusEarned(double monthlyProductionBonusEarned) {
		this.monthlyProductionBonusEarned = monthlyProductionBonusEarned;
	}

	/**
	 * 
	 * @return number Products Produced
	 */
	public int getNumberProductsProduced() {
		return numberProductsProduced;
	}

	/**
	 * set number Products Produced
	 * 
	 * @param numberProductsProduced
	 */
	public void setNumberProductsProduced(int numberProductsProduced) {
		this.numberProductsProduced = numberProductsProduced;
	}

	/**
	 * 
	 * @return number Products Need To Produced
	 */
	public int getNumberProductsNeedToProduced() {
		return numberProductsNeedToProduced;
	}

	/**
	 * set number Products Need To Produced
	 * 
	 * @param numberProductsNeedToProduced
	 */
	public void setNumberProductsNeedToProduced(int numberProductsNeedToProduced) {
		this.numberProductsNeedToProduced = numberProductsNeedToProduced;
	}

	/**
	 * 
	 * @return total salary
	 */
	public double getTotalSalary() {

		if (numberProductsProduced >= numberProductsNeedToProduced)
			return (monthlySalary + monthlyProductionBonusEarned);
		else
			return monthlySalary;

	}

	@Override
	/**
	 * @return all info for Shift Supervisor
	 */
	public String toString() {
		String ShiftSupervisorInfo = super.toString();
		ShiftSupervisorInfo += "----------------------------------------" + "\n";
		ShiftSupervisorInfo += "      Shift Supervisor Info : \n";
		ShiftSupervisorInfo += "----------------------------------------" + "\n";
		ShiftSupervisorInfo += "Monthly Salary: " + monthlySalary + "\n";
		ShiftSupervisorInfo += "Monthly Production Bonus Earned: " + monthlyProductionBonusEarned + "\n";
		ShiftSupervisorInfo += "Number Of Products That Produced: " + numberProductsProduced + "\n";
		ShiftSupervisorInfo += "Number Of Products Must Produced: " + numberProductsNeedToProduced + "\n";
		ShiftSupervisorInfo += "Shift Supervisor Toatal Salary: " + String.format("%.2f", getTotalSalary()) + "$"
				+ "\n";

		return ShiftSupervisorInfo;
	}

}
