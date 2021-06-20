package project;

public class Petroleum {
	public static final int DIESEL = 1;
	public static final int GASOLINE = 2;
	private static double gasolinePrice = 5.23;
	private static double dieselPrice = 4.02;

	public static double getGasolinePrice() {
		return gasolinePrice;

	}

	public static void setGasolinePrice(double gasolinePrice) {
		Petroleum.gasolinePrice = gasolinePrice;
	}

	public static double getDieselPrice() {
		return dieselPrice;
	}

	public static void setDieselPrice(double dieselPrice) {
		Petroleum.dieselPrice = dieselPrice;
	}

}
