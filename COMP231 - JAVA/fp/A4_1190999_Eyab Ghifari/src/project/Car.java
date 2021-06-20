package project;

public class Car extends Vehicles {

	// declare variables of Car attributes
	private int numberOfSeats;
	private boolean airConditionON;
	double carPrice;
	// none-argument constructor
	public Car() {
		super();
		numberOfSeats = 0;
	}

	// main argument constructor
	public Car(String modelNmae, String modleNo, String brand, String engineType, double tankSize,
			double fuelConsumption, Owner owner, int numberOfSeats) {
		// fill the car attributes from arguments
		super(modelNmae, modleNo, brand, engineType, tankSize, fuelConsumption, owner);
		this.numberOfSeats = numberOfSeats;
	}

	@Override
	public double costFor100km(Petroleum PtlType) {
		costFor100km = (100 / fuelConsumption) * Petroleum.getGasolinePrice();
		return costFor100km;
	}

	@Override
	public boolean setAirConditionON() {
		if (!airConditionON) {
			double LiterPerKilometer = 1 / fuelConsumption;
			double IncreaseOfLiterPerKilometer = LiterPerKilometer * 1.1; // Increase the fuel Consumption by 10%
			this.setFuelConsumption(1 / IncreaseOfLiterPerKilometer);
			System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
					+ " | Modele Number: " + this.modleNo + ") has turned on successfully.");
			return this.airConditionON = true; // that means the car air condition turnd on successfully
		}
		System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
				+ " | Modele Number: " + this.modleNo + ") is already on!");
		return false; // that means the car air condition is already on

	}

	@Override
	public boolean setAirConditionOff() {
		if (airConditionON) {
			double LiterPerKilometer = 1 / fuelConsumption;
			double IncreaseOfLiterPerKilometer = LiterPerKilometer / 1.1; // decrease the fuel Consumption by 20%
			this.setFuelConsumption(1 / IncreaseOfLiterPerKilometer);
			System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
					+ " | Modele Number: " + this.modleNo + ") has turned on successfully.");
			this.airConditionON = false;
			return true; // that means the car air condition turnd off successfully
		}
		System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
				+ " | Modele Number: " + this.modleNo + ") is already off!");
		return false; // that means the car air condition is already off

	}

	/**
	 * 
	 * @return numberOfSeats
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	/**
	 * set numberOfSeats
	 * 
	 * @param numberOfSeats
	 */
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}



	public double getCarPrice() {
		return carPrice;
	}

	/**
	 * set price
	 * 
	 * @param carPrice
	 */
	public void setCarPrice(double carPrice) {
		this.carPrice = carPrice;
	}

	@Override
	/**
	 * print all info
	 */
	public String toString() {
		String carInfo = super.toString();
		carInfo += "number Of Seats: " + numberOfSeats + "\n";
		carInfo += "is air Condition ON ?: " + airConditionON + "\n";

		return carInfo;
	}

	@Override
	/**
	 * clone car
	 */
	public int compareTo(Vehicles o) {

		return Double.compare(this.costFor100km, o.costFor100km);
	}

}
