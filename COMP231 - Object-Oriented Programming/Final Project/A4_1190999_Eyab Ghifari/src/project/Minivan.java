package project;

public class Minivan extends Vehicles {
	// declare variables of Minivan attributes
	private int numberOfSeats;
	private boolean airConditionON;
	private boolean hasAutoDoors;
	double minivanPrice;

	// none-argument constructor
	public Minivan() {
		super();
		this.numberOfSeats = 0;
	}

	// main argument constructor
	public Minivan(String modelNmae, String modleNo, String brand, String engineType, double tankSize,
			double fuelConsumption, Owner owner, int numberOfSeats, boolean airConditionON,
			boolean hasAutoDoors) {
		// fill the Minivan attributes from arguments
		super(modelNmae, modleNo, brand, engineType, tankSize, fuelConsumption, owner);
		this.numberOfSeats = numberOfSeats;
		this.airConditionON = airConditionON;
		this.hasAutoDoors = hasAutoDoors;
	}

	@Override
	public double costFor100km(Petroleum PtlType) {
		double PetroleumPrice;
		if (this.engineType.equalsIgnoreCase("Gasoline") || this.engineType.equalsIgnoreCase("Hybrid")) {
			PetroleumPrice = Petroleum.getGasolinePrice();
		} else {
			PetroleumPrice = Petroleum.getDieselPrice();
		}
		costFor100km = (100 / fuelConsumption) * PetroleumPrice;
		return costFor100km;
	}

	@Override
	public boolean setAirConditionON() {
		if (!airConditionON) {
			double LiterPerKilometer = 1 / fuelConsumption;
			double IncreaseOfLiterPerKilometer = LiterPerKilometer * 1.2;
			this.setFuelConsumption(1 / IncreaseOfLiterPerKilometer);
			System.out.println(">>The air condition of (Car: " + this.modelName + " | Brand: " + this.brand
					+ " | Modele Number: " + this.modleNo + ") has turned on successfully.");
			this.airConditionON = false;
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
			double IncreaseOfLiterPerKilometer = LiterPerKilometer / 1.2; // Increase the fuel Consumption by 20%
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

	public boolean isHasAutoDoors() {
		return hasAutoDoors;
	}

	public void setHasAutoDoors(boolean hasAutoDoors) {
		this.hasAutoDoors = hasAutoDoors;
	}

	public double getMinivanPrice() {
		return minivanPrice;
	}

	/**
	 * set price
	 * 
	 * @param minivanPrice
	 */
	public void setMinivanPrice(double minivanPrice) {
		this.minivanPrice = minivanPrice;
	}

	@Override
	public String toString() {
		String minivanInfo = super.toString();
		minivanInfo += "number Of Seats: " + numberOfSeats + "\n";
		minivanInfo += "is air Condition ON ?: " + airConditionON + "\n";
		minivanInfo += "is has Auto Doors ?: " + hasAutoDoors + "\n";

		return minivanInfo;
	}

	@Override
	public int compareTo(Vehicles o) {
		return Double.compare(this.costFor100km, o.costFor100km);

	}

}
