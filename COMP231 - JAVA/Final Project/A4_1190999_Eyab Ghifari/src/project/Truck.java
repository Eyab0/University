package project;

public class Truck extends Vehicles {

	// declare variables of Truck attributes
	private int numberOfSeats;
	private boolean airConditionON;
	private int power;
	double truckPrice;
	// none-argument constructor
	public Truck() {
		super();
		this.numberOfSeats = 0;
		this.power = 0;
	}

	// main argument constructor
	public Truck(String modelNmae, String modleNo, String brand, String engineType, double tankSize,
			double fuelConsumption, Owner owner, int numberOfSeats, boolean airConditionON,
			int power) {

		// fill the Truck attributes from arguments
		super(modelNmae, modleNo, brand, engineType, tankSize, fuelConsumption, owner);
		this.numberOfSeats = numberOfSeats;
		this.airConditionON = airConditionON;
		this.power = power;
	}

	@Override
	public double costFor100km(Petroleum PtlType) {
		costFor100km = (100 / fuelConsumption) * Petroleum.getDieselPrice();
		return costFor100km;
	}

	@Override
	public boolean setAirConditionON() {
		if (!airConditionON) {
			double LiterPerKilometer = 1 / fuelConsumption;
			double IncreaseOfLiterPerKilometer = LiterPerKilometer * 1.2; // Increase the fuel Consumption by 20%
			this.setFuelConsumption(1 / IncreaseOfLiterPerKilometer);
			System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
					+ " | Modele Number: " + this.modleNo + ") has turned on successfully.");
			return this.airConditionON = true;// that means the car air condition turnd on successfully
		}
		System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
				+ " | Modele Number: " + this.modleNo + ") is already on!");
		return false; // that means the car air condition is already on

	}

	@Override
	public boolean setAirConditionOff() {
		if (airConditionON) {
			double LiterPerKilometer = 1 / fuelConsumption;
			double IncreaseOfLiterPerKilometer = LiterPerKilometer / 1.2; // decrease the fuel Consumption by 20%
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

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	

	public double getTruckPrice() {
		return truckPrice;
	}

	/**
	 * set price
	 * 
	 * @param truckPrice
	 */
	public void setTruckPrice(double truckPrice) {
		this.truckPrice = truckPrice;
	}

	@Override
	/**
	 * print all info
	 */
	public String toString() {
		String truckInfo = super.toString();
		truckInfo += "number Of Seats: " + numberOfSeats + "\n";
		truckInfo += "is air Condition ON ?: " + airConditionON + "\n";
		truckInfo += "power: " + power + "\n";

		return truckInfo;
	}

	@Override
	/**
	 * clone truk
	 */
	public int compareTo(Vehicles o) {
		return Double.compare(this.costFor100km, o.costFor100km);
	}

}
