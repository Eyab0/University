import java.util.Scanner;

//
//								 ***********************
//								 *      COMP231        *
//								 *    Assignment #1    *
//								 ***********************
//	
//				****************** STEPS OF MY CODE : *****************
//				* In the beginning, my program scan the values ​​from   *
//				* the user: the right number, the left number,        *
//				* and the excluded digit.							  *
//				* >>Then it passes the values ​​to the method,          *
//				* which first checks that the left number is          *
//				* less than the right number and that the numbers     *
//				* are not negative. 								  *
//				* >> Then it enters the For Loop and starts from      *   
//				* the left number and increases the number up to      *
//				* the right number.									  *
//				* >> And inside the first loop,                       *
//				* it enters the While Loop to make sure that the      *
//				* current number does not contain the excluded digit, * 
//				* then gives the value “False” to the variable of     *  
//				* the type Boolean .								  *
//				* >> Then enters into the if-statement and if the     *
//				* current number does not contain the excluded digit. *
//				* >> it enters the While Loop To make sure if the     *
//				* number is nice or not by taking the last digit of   *
//				* the number and comparing it to the next digit if    *
//				*  it is smaller than it or not.                      *
//				* then the number takes the next digit and adds them  * 
//				* and stores them in a variable to make sure that they* 
//				* are less than the next digit and so on ...          *
//				* and if the condition is not met in one of the steps,*
//				* it gives the value "False" to the variable "Is Nice"*
//				* >> Then it goes into the if-Statement and           *
//				* checks whether the number is nice or not ..         *
//				* >> and prints the number if it is nice number.      *
//				*******************************************************
//	      
//    						 ********Done By:********
//							 *						*
//							 * Name : Eyab Ghifari  *
//							 * ID : 1190999         *
//							 * Lab Section : 13     *
//					    	 *						*
//	 				    	 ************************

public class NiceNumber {

	public static void main(String[] args) {
		int LeftNum, RightNum, ExcludeNum; //declare variables
		Scanner input = new Scanner(System.in);
		System.out.println("<< Welcome To My Program That Check And Print Nice Numbers in a Specific Range >>\n");
		System.out.print("Enter Left Bound : ");
		LeftNum = input.nextInt(); // scan inputs
		System.out.print("Enter Right Bound : ");
		RightNum = input.nextInt();
		System.out.print("Enter Digit To Exclude It : ");
		ExcludeNum = input.nextInt();
		System.out.println("-------------------------------");
		niceNumbers(LeftNum, RightNum, ExcludeNum); // call the method 

	}

	public static void niceNumbers(int left, int right, int digit) {

		//declare variables
		int Num, CheckExcludeNum, CheckNiceNum, SumOfPreDigits, NextDigit, Counter = 0;
		boolean ExcludeNumExist, isNiceNum;

		if (left > right || right < 0 || left < 0) {

			// check if number not negative and left number less than right number >> and
			// print error message

			System.out
					.println("Error ,\n-Make Sure that the number on the left is less than the number on the right.\r\n"
							+ "-And that the two numbers are not less than zero ( not negative) .");
			System.exit(1); // exit from the program  
		}

		for (Num = left; Num <= right; Num++) {

			CheckExcludeNum = Num; // set value of current number in "CheckExcludeNum" variable as temporary value  
			ExcludeNumExist = false;
			while (CheckExcludeNum > 0) {
				// check number digit by digit if it contain the excluded digit or not
				if (CheckExcludeNum % 10 == digit) { 
					ExcludeNumExist = true; // if it contain the excluded digit set "ExcludeNumExist" as true
					break;
				}

				CheckExcludeNum /= 10;

			}

			// if the number not contain the ExcludeNumExist >> will enter if-Statement
			if (!ExcludeNumExist) {

				isNiceNum = true;
				CheckNiceNum = Num; // set value of current number in "CheckNiceNum" variable as temporary value
				SumOfPreDigits = CheckNiceNum % 10; // take the last digit from the number
				CheckNiceNum /= 10; // remove the last digit from the number

				while (CheckNiceNum > 0) {
					NextDigit = CheckNiceNum % 10; // take the next digit of previous digit
					// check that the next digits is grater then the sum of previous digits
					if (NextDigit > SumOfPreDigits) {
						SumOfPreDigits += NextDigit; // store the next digit and previous digits to the sum of previous
														// digits
						CheckNiceNum /= 10;
					}

					else {
						isNiceNum = false; // if one of the steps of the if-statement not pass >> then number not nice
						break;
					}

				}

				if (isNiceNum) {

					Counter++; // counter variable to count how many nice numbers in the given range
					System.out.println(Counter + ") " + Num);
				}

			}

		}
		System.out.println("-------------------------------");
		System.out.println("There are " + Counter + " Nice Numbers in Range (" + left + ") to (" + right
				+ ") With Excluded Number (" + digit + ") ");

	}

}