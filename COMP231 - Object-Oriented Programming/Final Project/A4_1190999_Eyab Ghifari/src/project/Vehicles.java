package project;

import java.text.DecimalFormat;

public abstract class Vehicles implements Cloneable, Comparable<Vehicles> {

	// declare variables of Vehicles attributes
	protected String modelName;
	protected String modleNo;
	protected String brand;
	protected String engineType;
	protected double tankSize;
	protected double fuelConsumption;
	public Owner owner;
	protected double costFor100km;

	// none-argument constructor
	public Vehicles() {

	}

	// main argument constructor
	public Vehicles(String modelNmae, String modleNo, String brand, String engineType, double tankSize,
			double fuelConsumption, Owner owner) {
		// fill the Vehicles attributes from arguments
		this.modelName = modelNmae;
		this.modleNo = modleNo;
		this.brand = brand;
		setEngineType(engineType);
		this.tankSize = tankSize;
		this.fuelConsumption = fuelConsumption;
		this.owner = owner;
	}

	public abstract double costFor100km(Petroleum PtlType);

	public abstract boolean setAirConditionON();

	public abstract boolean setAirConditionOff();

	public double MovableDistance() {

		return fuelConsumption * tankSize;
	}

	/**
	 * 
	 * @return modelName
	 */
	String getModelName() {
		return modelName;
	}

	/**
	 * set modelName
	 * 
	 * @param modelName
	 */
	void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 
	 * @return modelName
	 */
	String getModleNo() {
		return modleNo;
	}

	/**
	 * set modleNo
	 * 
	 * @param modleNo
	 */
	void setModleNo(String modleNo) {
		this.modleNo = modleNo;
	}

	String getBrand() {
		return brand;
	}

	/**
	 * set brand
	 * 
	 * @param brand
	 */
	void setBrand(String brand) {
		this.brand = brand;
	}

	String getEngineType() {
		return engineType;
	}

	/**
	 * set engineType
	 * 
	 * @param engineType
	 */
	void setEngineType(String engineType) {

		petroleumTypeChecker(engineType);
	}

	double getTankSize() {
		return tankSize;
	}

	/**
	 * set tankSize
	 * 
	 * @param tankSize
	 */
	void setTankSize(double tankSize) {
		this.tankSize = tankSize;
	}

	double getFuelConsumption() {
		return fuelConsumption;
	}

	/**
	 * 
	 * @param fuelConsumption
	 */
	void setFuelConsumption(double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	Owner getOwner() {
		return owner;
	}

	/**
	 * set owner
	 * 
	 * @param owner
	 */
	void setOwner(Owner owner) {
		this.owner = owner;
	}

	double getCostFor100km() {
		return costFor100km;
	}

	public void petroleumTypeChecker(String engineType) {

		// check if the their is wrong input for petroleum
		try {
			if (this instanceof Car) {
				if (!(engineType.equalsIgnoreCase("Gasoline") || engineType.equalsIgnoreCase("Hybrid"))) {
					throw new IllegalArgumentException("\nwrong input for car petroleum !!");
				}
			} else if (this instanceof Truck) {
				if (!(engineType.equalsIgnoreCase("Diesel"))) {
					throw new IllegalArgumentException("\nwrong input for Truck petroleum !!");
				}
			} else if (this instanceof Minivan) {
				if (!(engineType.equalsIgnoreCase("Gasoline") || engineType.equalsIgnoreCase("Diesel")
						|| engineType.equalsIgnoreCase("Hybrid"))) {
					throw new IllegalArgumentException("\nwrong input for Minivan petroleum !!");
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("ditels :");
			System.out.println(e.toString());
			System.out.println("try again..");
		}
		this.engineType = engineType;

	}

	@Override
	public String toString() {
		String vehicleType;
		// check the type of the vehicle
		if (this instanceof Car) {
			vehicleType = "Car";
		} else if (this instanceof Truck) {
			vehicleType = "Truck";
		} else {
			vehicleType = "Minivan";

		}
		String vehivleInfo = "";
		vehivleInfo += "\n" + "----------------------------------------" + "\n";
		vehivleInfo += "      " + vehicleType + " Info : \n";
		vehivleInfo += "----------------------------------------" + "\n";
		vehivleInfo += "model Name: " + modelName + "\n";
		vehivleInfo += "modleNo: " + modleNo + "\n";
		vehivleInfo += "brand: " + brand + "\n";
		vehivleInfo += "engine Type: " + engineType + "\n";
		vehivleInfo += "tank Size: " + tankSize + "\n";
		vehivleInfo += "fuel Consumption: " + fuelConsumption + " liter/kilo" + "\n";
		vehivleInfo += "tank Size: " + tankSize + "\n";
		vehivleInfo += "fuel Consumption: " + fuelConsumption + "\n";
		vehivleInfo += "cost For 100km: " + new DecimalFormat("0.00$").format(costFor100km) + "\n";
		vehivleInfo += owner.toString() + "\n";

		return vehivleInfo;
	}

	public Object clone() throws CloneNotSupportedException {

		Vehicles v = (Vehicles) super.clone();
		v.setOwner(null);

		return v;
	}

}
