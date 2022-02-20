package application;

public class Inshurance_companyData {
	private String companyName;
	private int companyDiscount;
	private int numberOfCustomer;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyDiscount() {
		return companyDiscount;
	}

	public void setCompanyDiscount(int i) {
		this.companyDiscount = i;
	}

	public int getNumberOfCustomer() {
		return numberOfCustomer;
	}

	public void setNumberOfCustomer(int numberOfCustomer) {
		this.numberOfCustomer = numberOfCustomer;
	}

	public Inshurance_companyData(String companyName, int companyDiscount, int numberOfCustomer) {
		super();
		this.companyName = companyName;
		this.companyDiscount = companyDiscount;
		this.numberOfCustomer = numberOfCustomer;
	}

}
