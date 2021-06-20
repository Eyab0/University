package project;

import java.util.Comparator;

class VehiclesSortingByOwnerName implements Comparator<Vehicles> {

	@Override
	public int compare(Vehicles o1, Vehicles o2) {

		// compare by owner name
		return (o1.owner).getName().compareTo((o2.owner).getName());
	}

}
