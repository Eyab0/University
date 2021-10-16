package project;

/**
								 ***********************
								 *      COMP231        *
								 *      Project        *
								 ***********************
								
					******************** DOCUMENTATION ********************
					* >> driver class which scan objects of vehicles from * 
					* file and store it in the array list 		          *
					* >> show menu to make changes in the array list      *
					*******************************************************
								
								 ******** @author *******
								 *						*
								 * Name : Eyab Ghifari  *
								 * ID : 1190999         *
								 * Lab Section : 13     *
								 *						*
								 ************************ 

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class DriverPalestinianVehicleAgency {

	static List<Vehicles> arrayListOfVehicles = new ArrayList<>(); // Create array list of vehicles objects
	static Scanner inputConsle = new Scanner(System.in);
	static Petroleum petroleum = new Petroleum();
	static boolean isFileEmpty;
	static boolean fileWriten;

	/**
	 * main method
	 * 
	 * @throws IOException                ----------> if file not exists
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		System.out.println("\t\t\t>>>Welcome to the Palestinian Vehicle Agency<<<");
		int option = 0;
		loop: while (true) {

			try {
				showMenu(); // call showMenu to print menu
				option = inputConsle.nextInt();
			} catch (Exception inputError) {
				// if user enter any thing not a number
				System.out.println(
						"Wrong input!\n" + "-make sure you you choose number from 1-9\n" + "-please try again later");
				System.out.println("\n>>Details of the error: ");
				System.out.println(inputError.toString());
				System.exit(0);
			}
			switch (option) {
			case 1: {
				// get info of vehicles objects
				// fill array of vehicles objects with informations
				fillDataIntoArrayListFromFile();
				if (isFileEmpty)
					System.out.println("warning!! \nNo Data in file , please check yout file.");
				else
					System.out.println(
							">>Input file readed successfully\n" + ">>Data stored in an arraylist successfully");

				break;
			}
			case 2: {
				// call method to set the Diesel and gasoline prices
				setPetroleumPrices();
				System.out.println(">>petroleum pricrs set successfully\n");
				break;
			}
			case 3: {
				// sort the array list according cost for 100km
				SortedListAccordingToCostPer100km();
				printVehiclesInfo();
				break;
			}
			case 4: {
				// check if there is a vehicle without owner name
				ownerNameChecker();
				// sort the array list according owner name
				Collections.sort(arrayListOfVehicles, new VehiclesSortingByOwnerName());
				printVehiclesInfo();
				break;
			}
			case 5: {
				// sort the array list according brand name
				Collections.sort(arrayListOfVehicles, new VehiclesDescendingSortingByBrandName());
				printVehiclesInfo();
				break;
			}
			case 6: {
				// clone vehicles
				try {
				cloneVehicle();
				} catch (Exception e) {
					// print errors
					System.out.println("Error occourd !!");
					System.out.println(e.toString());
				}
				break;
			}
			case 7: {

				turnAirconditionOn();
				break;
			}
			case 8: {
				if (arrayListOfVehicles.size() == 0) {
					// print errors if file is empty
					System.out.println("No Data to write it into file!!");
				} else {
					writeIntoFile();
				}
				break;
			}
			case 9: {
				if (fileWriten) {
					System.out.println("file data updated successfully");
					System.out.println(">>>>Thank you for use \"Palestinian Vehicle Agency\" System<<<<");
					System.out.println("Exiting...");

				} else {
					// check if the user didn't save the data into file
					System.out.println("warning!! \nyou didn't save Data in file.");
					System.out.println(
							"please enter number \n(1) to save your data in the file  \nor number (2) to exit any way.. ");
					int optionFile = inputConsle.nextInt();
					if (optionFile == 1)
						writeIntoFile();
					System.out.println("Thank you for use \"Palestinian Vehicle Agency\" System");
					System.out.println("Exiting...");

				}
				break loop;
			}
			default:
				// print errors if the user choose number not from 1-9
				System.out.println("Unexpected value:" + option);
				System.out.println("make sure you you choose number from 1-9");

			}

		}
	}

	/**
	 * print menu
	 */
	public static void showMenu() {

		System.out.println("\nMenu:");
		System.out.println("=====================================================================================");
		System.out.println("1) Read the data about objects from the file \"input.txt\" and store them in Arraylist");
		System.out.println("2) Set prices of petroleum types.");
		System.out.println("3) Print vehicles in ascending order base on cost for100km");
		System.out.println("4) Print vehicles in ascending order base on owner name");
		System.out.println("5) Print vehicles in descending order based on brand");
		System.out.println("6) Clone a vehicle");
		System.out.println("7) Turn air-conditions on");
		System.out.println("8) Write the sorted data in the \"output.txt\" file ");
		System.out.println("9) Exit System");
		System.out.println("=====================================================================================");
		System.out.println("Please Choose An Option from 1-9: ");
	}

	/**
	 * open file and fill data into array list
	 */
	public static void fillDataIntoArrayListFromFile() {

		File inputFile = new File("inputdata.txt");
		try {
			Scanner fileReader = new Scanner(inputFile);
			if (inputFile.length() == 0)
				isFileEmpty = true; // check if the file is empy

			else {
				while (fileReader.hasNextLine()) {
					// fill all data into its type
					StringBuilder fileData = new StringBuilder(fileReader.nextLine());
					String[] vehiclesData = fileData.toString().split(",");
					// car type
					if (vehiclesData[0].equalsIgnoreCase("Car")) {
						Car carObject = new Car();
						Owner carOwner = new Owner();
						carObject.setModelName(vehiclesData[1]);
						carObject.setModleNo(vehiclesData[2]);
						carObject.setBrand(vehiclesData[3]);
						carOwner.setName(vehiclesData[4]);
						carObject.setOwner(carOwner);
						carObject.setEngineType(vehiclesData[5].trim());
						carObject.setTankSize(Double.parseDouble(vehiclesData[6].trim()));
						carObject.setFuelConsumption(Double.parseDouble(vehiclesData[7].trim()));
						carObject.setNumberOfSeats(Integer.parseInt(vehiclesData[8].trim()));
						carObject.owner.setDateOfRegistration((GregorianCalendar) carObject.owner.dateOfRegistration);
						arrayListOfVehicles.add(carObject);

					}
					// truck type
					else if (vehiclesData[0].equalsIgnoreCase("Truck")) {
						Truck TruckObject = new Truck();
						Owner truckOwner = new Owner();
						TruckObject.setModelName(vehiclesData[1]);
						TruckObject.setModleNo(vehiclesData[2]);
						TruckObject.setBrand(vehiclesData[3]);
						truckOwner.setName(vehiclesData[4]);
						TruckObject.setOwner(truckOwner);
						TruckObject.setEngineType(vehiclesData[5].trim());
						TruckObject.setTankSize(Double.parseDouble(vehiclesData[6].trim()));
						TruckObject.setFuelConsumption(Double.parseDouble(vehiclesData[7].trim()));
						TruckObject.setNumberOfSeats(Integer.parseInt(vehiclesData[8].trim()));
						TruckObject.setPower(Integer.parseInt(vehiclesData[9]));
						TruckObject.owner
								.setDateOfRegistration((GregorianCalendar) TruckObject.owner.dateOfRegistration);
						arrayListOfVehicles.add(TruckObject);
					}
					// minivan type
					else if (vehiclesData[0].equalsIgnoreCase("MiniVan")) {
						Minivan MinivanObject = new Minivan();
						Owner minivanOwner = new Owner();
						MinivanObject.setModelName(vehiclesData[1]);
						MinivanObject.setModleNo(vehiclesData[2]);
						MinivanObject.setBrand(vehiclesData[3]);
						minivanOwner.setName(vehiclesData[4]);
						MinivanObject.setOwner(minivanOwner);
						MinivanObject.setEngineType(vehiclesData[5].trim());
						MinivanObject.setTankSize(Double.parseDouble(vehiclesData[6].trim()));
						MinivanObject.setFuelConsumption(Double.parseDouble(vehiclesData[7].trim()));
						MinivanObject.setNumberOfSeats(Integer.parseInt(vehiclesData[8].trim()));
						MinivanObject.setHasAutoDoors(Boolean.valueOf(vehiclesData[9]));
						MinivanObject.owner
								.setDateOfRegistration((GregorianCalendar) MinivanObject.owner.dateOfRegistration);
						arrayListOfVehicles.add(MinivanObject);
					}
				}
				fileReader.close();
			}
		} catch (FileNotFoundException fileError) {
			// print errors
			System.out.println("Error!!\n" + "File Not Found!\n" + "-please try again later");
			System.out.println("\n>>Details of the error: ");
			System.out.println(fileError.toString());
			System.exit(0);
		}

	}

	/**
	 * set the Diesel and gasoline prices
	 */
	public static void setPetroleumPrices() {
		Petroleum petroleum = new Petroleum();

		System.out.print("Enter price for Diesel: ");
		double dieselPrice = inputConsle.nextDouble(); // get value from user
		Petroleum.setDieselPrice(dieselPrice);

		System.out.print("Enter price for Gasoline: ");
		double gas = inputConsle.nextDouble(); // get value from user
		Petroleum.setGasolinePrice(gas);

		for (Vehicles vehicles : arrayListOfVehicles) {
			vehicles.costFor100km(petroleum);
		}

	}

	/**
	 * sort the array list according cost for 100km
	 */
	public static void SortedListAccordingToCostPer100km() {

		Collections.sort(arrayListOfVehicles);

	}

	/**
	 * print the info of the array list
	 */
	public static void printVehiclesInfo() {
		for (Vehicles VehiclesInfo : arrayListOfVehicles) {
			System.out.println(VehiclesInfo.toString());
		}
	}

	/**
	 * check if there is a vehicle without owner name
	 */
	public static void ownerNameChecker() {
		for (Vehicles VehiclesObject : arrayListOfVehicles) {
			String owneName = (VehiclesObject.owner).name;

			if (owneName == null || owneName.isEmpty()) {
				System.out
						.println("vehicle name: " + VehiclesObject.modelName + "\nmodleNo: " + VehiclesObject.modleNo);
				System.out.println("\nplease enter name of this vehicle owner: ");
				inputConsle.nextLine();
				VehiclesObject.owner.setName(inputConsle.nextLine()); // set the name from the user
			}
		}
	}

	/**
	 * clone vehicles
	 * 
	 * @throws CloneNotSupportedException
	 */
	public static void cloneVehicle() throws CloneNotSupportedException {

		int vehicleNumber = 0;
		for (Vehicles vehicleObj : arrayListOfVehicles) {
			System.out.println("************************************************");
			System.out.println((vehicleNumber + 1) + ") " + vehicleObj.toString());
			vehicleNumber++;
		}
		System.out.println("\nChoose a Vehicle number above to clone it: ");
		int objectToClone = inputConsle.nextInt();
		if (objectToClone < 0 || objectToClone > arrayListOfVehicles.size()) {
			System.out.println("Unexpected value:" + objectToClone);
			System.out.println("this vehicles not exists !!");
		} else {
			Vehicles clonedVehicle = (Vehicles) arrayListOfVehicles.get(objectToClone - 1).clone();

			clonedVehicle.setOwner(new Owner());
			clonedVehicle.owner.setName(null);

			arrayListOfVehicles.add(clonedVehicle);
			System.out.println("Cloned Vehicle: \n" + clonedVehicle.toString());

		}
	}

	/**
	 * set all vehicles Air condition On
	 */
	public static void turnAirconditionOn() {
		for (Vehicles Vehicle : arrayListOfVehicles) {
			Vehicle.setAirConditionON();
		}
		for (Vehicles Vehicle : arrayListOfVehicles) {
			Vehicle.costFor100km(petroleum);
		}

		System.out.println("--AirCondition turned ON for all vehicles--");
	}

	/**
	 * fill all info into file
	 * 
	 * @throws IOException
	 */
	public static void writeIntoFile() throws IOException {

		File outFile = new File("output.txt");
		PrintWriter outFileToWrite = new PrintWriter(outFile);
		for (Vehicles VehiclesInfo : arrayListOfVehicles) {
			outFileToWrite.println(VehiclesInfo.toString());
		}
		fileWriten = true;
		outFileToWrite.flush();
		outFileToWrite.close();

	}

}
