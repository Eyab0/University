package project;

import java.util.Comparator;

class VehiclesDescendingSortingByBrandName implements Comparator<Vehicles> {

	@Override
	public int compare(Vehicles obj1, Vehicles obj2) {
		// compare by brand name
		return obj2.getBrand().compareTo(obj1.getBrand());
	}

}
