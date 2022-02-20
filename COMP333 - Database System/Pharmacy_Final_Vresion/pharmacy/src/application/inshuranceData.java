package application;

public class inshuranceData {
	private int customerID;
	private String coustumerName;
	private String inshurance_companyName;

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCoustumerName() {
		return coustumerName;
	}

	public void setCoustumerName(String coustumerName) {
		this.coustumerName = coustumerName;
	}

	public String getInshurance_companyName() {
		return inshurance_companyName;
	}

	public void setInshurance_companyName(String inshurance_companyName) {
		this.inshurance_companyName = inshurance_companyName;
	}

	public inshuranceData(int customerID, String coustumerName, String insurance_companyName) {
		super();
		this.customerID = customerID;
		this.coustumerName = coustumerName;
		this.inshurance_companyName = insurance_companyName;
	}

}
