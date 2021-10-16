/**
								 ***********************
								 *      COMP231        *
								 *    Assignment #2    *
								 ***********************
	
				******************** DOCUMENTATION ********************
		        * >> driver class which create objects of cars and    * 
                * print all  the information about it from Car class. *
				* >> find the maximum car price and print all info	  *
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

public class driver {

	// main method
	public static void main(String[] args) {
		// Create array of cars objects
		Car[] cars = new Car[5];
		// fill array of cars objects with informations
		cars[0] = new Car();
		cars[1] = new Car("011-g", 2017, 12, "white", 120000, "Bentley", 2021, 10);
		cars[2] = new Car("300-a", 2020, 2, "gray", 520000, "tesla Model Y", 2022, 5);
		cars[3] = new Car("420-w", 2016, 8, "dark blue", 330000, "range rover", 2020, 12);
		cars[4] = new Car("670-x", 2019, 6, "light yellow", 220000, "chevrolet", 2023, 7);
		// print the informations about cars
		System.out.println("<< WELCOME TO PALESTINIAN CAR AGENCY >>\n");
		System.out.println("ALL INFORMATION ON AVAILABLE CARS :");
		System.out.println("================================================================");
		// call method to print all cars informations
		printCarInfo(cars);

		// print info about maximum car price
		System.out.println("\n****************************************************************");
		System.out.println(">>INFORMATION ABOUT THE MOST EXPENSIVE CAR PRICE : \n");
		// call method of maxPrice to find car object of maximum car price
		System.out.println(maxPrice(cars).printCarInfo());
		System.out.println("****************************************************************");

	}

	/**
	 * this function print all cars informations
	 * 
	 * @param cars
	 */
	public static void printCarInfo(Car[] cars) {

		int counter = 0; // to print the car number
		for (Car car : cars) {
			System.out.println("-INFO FOR CAR (" + (counter + 1) + ")\n");
			System.out.println(car.printCarInfo());
			System.out.println("================================================================");
			counter++;
		}
	}

	/**
	 * 
	 * @param cars all cars objects
	 * @return car object with maximum price
	 */
	public static Car maxPrice(Car[] cars) {
		double max = cars[0].getPrice();
		int maxPriceIndex = 0;
		for (int i = 1; i < cars.length; i++) {
			// find max price of the array
			if (cars[i].getPrice() > max) {
				max = cars[i].getPrice();
				maxPriceIndex = i;
			}
		}
		return cars[maxPriceIndex];

	}

}
