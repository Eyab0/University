package ass3_1190999;

/**
									 ***********************
									 *      COMP231        *
									 *    Assignment #3    *
									 ***********************

					******************** DOCUMENTATION ********************
					* >> driver class which create objects of Employees   * 
					* and print all  the information about it from other  *
					* classes. 											  *
					* >> find the Employee object who is is total         *
					* salary above average car price and print all info	  *
					* about it                                            *
					*******************************************************
	
	 								 ******** @author *******
									 *						*
									 * Name : Eyab Ghifari  *
									 * ID : 1190999         *
									 * Lab Section : 13     *
									 *						*
									 ************************ 

*/
import java.util.ArrayList;

public class DriverPalestinianDairyFactory {

	// main method
	public static void main(String[] args) {

		// create array list to add all Employee objects to it
		ArrayList<Employee> EmployeesArrayList = new ArrayList<Employee>();

		// Create Employee objects
		// fill array of Employee objects with informations
		Employee EmpNum1 = new ProductionWorker("Eyab", "111-E", 2019, 6, 16,
				new Address("Rwd-12", "West-Bank", "Ramallah", "99065"), 1, 8, 240);
		// add Employee objects to array list
		EmployeesArrayList.add(EmpNum1);

		Employee EmpNum2 = new ProductionWorker("tariq", "118-E", 2019, 3, 12,
				new Address("Rwd-12", "West-Bank", "Ramallah", "99065"), 1, 8, 240);
		EmployeesArrayList.add(EmpNum2);

		Employee EmpNum3 = new ShiftSupervisor("sameer", "430-F", 2017, 8, 13,
				new Address("Abu sera-12", "Gaza", "Rafah", "12121"), 1200, 8, 6, 27);
		EmployeesArrayList.add(EmpNum3);

		Employee EmpNum4 = new ShiftSupervisor("Omar", "970-C", 2018, 5, 14,
				new Address("Abu sera-12", "West-Bank", "Ramallah", "98267"), 1200, 5, 7, 22);
		EmployeesArrayList.add(EmpNum4);

		Employee EmpNum5 = new TeamLeader("Nabeel", "805-A", 2015, 11, 21,
				new Address("BirZeit Street", "West-Bank", "BirZeit", "12345"), 2, 10, 150, 16, 15, 16);
		EmployeesArrayList.add(EmpNum5);

		Employee EmpNum6 = new TeamLeader("Gorge", "410-M", 2011, 9, 27,
				new Address("BirZeit Street", "West-Bank", "BirZeit", "123456"), 2, 5, 110, 16, 12, 14);
		EmployeesArrayList.add(EmpNum6);

		// print all info about Employee
		System.out.println("------------------------------------------------------");
		System.out.println("\tWelcome to Palestinian Dairy Factory");
		System.out.println("------------------------------------------------------\n");
		for (int i = 0; i < EmployeesArrayList.size(); i++) {
			System.out.println("      ***********************");
			System.out.println("      * " + " Employee Number:" + (i + 1) + "  *");
			System.out.println("      ***********************");
			System.out.println(EmployeesArrayList.get(i));
			System.out.println("==============================================================\n");

		}
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("Info of employees whose gross salary is higher than the average of all employees : ");
		System.out.println("------------------------------------------------------------------------------------\n");
		// call method to print all info of Employee's salary is greater of average
		ListGreaterThanAverage(EmployeesArrayList);

	}

	public static void ListGreaterThanAverage(ArrayList<Employee> EmployeeList) {

		double netSalary = 0, avgSalary = 0;

		// get sum all employees salaries
		for (int empNum = 0; empNum < EmployeeList.size(); empNum++) {

			// using casting of employee object
			if (EmployeeList.get(empNum) instanceof ProductionWorker)
				netSalary += ((ProductionWorker) EmployeeList.get(empNum)).getTotalSalary();

			else if (EmployeeList.get(empNum) instanceof TeamLeader)
				netSalary += ((TeamLeader) EmployeeList.get(empNum)).getTotalSalary();

			else if (EmployeeList.get(empNum) instanceof ShiftSupervisor)
				netSalary += ((ShiftSupervisor) EmployeeList.get(empNum)).getTotalSalary();

		}

		// set average of employees salaries
		avgSalary = (netSalary / EmployeeList.size());

		for (int empNum = 0; empNum < EmployeeList.size(); empNum++) {

			// check if there is employee salary is grater of average
			// and print info all about him/her
			if (EmployeeList.get(empNum) instanceof ProductionWorker) {
				if (((ProductionWorker) EmployeeList.get(empNum)).getTotalSalary() > avgSalary) {
					System.out.println(((ProductionWorker) EmployeeList.get(empNum)).toString());
					System.out.println("====================================================================\n");
				}
			} else if (EmployeeList.get(empNum) instanceof TeamLeader) {
				if (((TeamLeader) EmployeeList.get(empNum)).getTotalSalary() > avgSalary) {
					System.out.println(((TeamLeader) EmployeeList.get(empNum)).toString());
					System.out.println("====================================================================\n");
				}
			} else if (EmployeeList.get(empNum) instanceof ShiftSupervisor) {
				if (((ShiftSupervisor) EmployeeList.get(empNum)).getTotalSalary() > avgSalary) {
					System.out.println(((ShiftSupervisor) EmployeeList.get(empNum)).toString());
					System.out.println("====================================================================\n");
				}
			}

		}

	}

}
