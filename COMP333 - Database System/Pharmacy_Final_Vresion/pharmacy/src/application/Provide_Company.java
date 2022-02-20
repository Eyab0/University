package application;

public class Provide_Company {
	private String companyName;
	private int phone;
	private String companyAddress;

	public Provide_Company() {
		super();
	}

	
	static Provide_Company prov;
	
	public Provide_Company(String companyName, int phone, String companyAddress) {
		super();
		this.companyName = companyName;
		this.phone = phone;
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

}
