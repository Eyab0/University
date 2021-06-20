package ass3_1190999;

public class TeamLeader extends ProductionWorker {

	// declare variables of TeamLeader attributes
	private double monthlyBonus;
	private int requiredTrainingHours;
	private int trainingHoursAttended;

	// main argument constructor
	public TeamLeader(String nameOfEmployee, String numberOfEmployee, int year, int month, int day,
			Address addressOfEmployee, int shift, double payRateByHour, int numOfHoursPerMonth, double monthlyBonus,
			int requiredTrainingHours, int trainingHoursAttended) {

		// fill the TeamLeader attributes from arguments
		super(nameOfEmployee, numberOfEmployee, year, month, day, addressOfEmployee, shift, payRateByHour,
				numOfHoursPerMonth);
		this.monthlyBonus = monthlyBonus;
		this.requiredTrainingHours = requiredTrainingHours;
		this.trainingHoursAttended = trainingHoursAttended;
	}

	/**
	 * 
	 * @return monthly Bonus
	 */
	public double getMonthlyBonus() {
		return monthlyBonus;
	}

	/**
	 * set monthly Bonus
	 * 
	 * @param monthlyBonus
	 */
	public void setMonthlyBonus(double monthlyBonus) {
		this.monthlyBonus = monthlyBonus;
	}

	/**
	 * @return required Training Hours
	 */
	public int getRequiredTrainingHours() {
		return requiredTrainingHours;
	}

	/**
	 * set required Training Hours
	 * 
	 * @param requiredTrainingHours
	 */
	public void setRequiredTrainingHours(int requiredTrainingHours) {
		this.requiredTrainingHours = requiredTrainingHours;
	}

	/**
	 * 
	 * 
	 * @return training Hours Attended
	 */
	public int getTrainingHoursAttended() {
		return trainingHoursAttended;
	}

	/**
	 * set training Hours Attended
	 * 
	 * @param trainingHoursAttended
	 */
	public void setTrainingHoursAttended(int trainingHoursAttended) {
		this.trainingHoursAttended = trainingHoursAttended;
	}

	@Override
	/**
	 * @return total salary
	 */
	public double getTotalSalary() {
		double netSalary = super.getTotalSalary();
		double bonusAchieved = monthlyBonus * ((double) trainingHoursAttended / requiredTrainingHours);
		return netSalary + bonusAchieved;
	}

	@Override
	/**
	 * @return all info about Team Leader
	 */

	public String toString() {
		String TeamLeaderInfo = super.toString();
		TeamLeaderInfo += "\n" + "----------------------------------------" + "\n";
		TeamLeaderInfo += "      Team Leader Info : \n";
		TeamLeaderInfo += "----------------------------------------" + "\n";
		TeamLeaderInfo += "Monthly Salary: " + monthlyBonus + "\n";
		TeamLeaderInfo += "Monthly Production Bonus Earned: " + requiredTrainingHours + "\n";
		TeamLeaderInfo += "Number Of Products That Produced: " + trainingHoursAttended + "\n";
		TeamLeaderInfo += "Team Leader Toatal Salary: " + String.format("%.2f", getTotalSalary()) + "$" + "\n";

		return TeamLeaderInfo;
	}

}
