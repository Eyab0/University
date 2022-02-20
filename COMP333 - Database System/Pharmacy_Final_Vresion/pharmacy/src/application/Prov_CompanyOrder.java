package application;


public class Prov_CompanyOrder {

	private String companyName;
	private String itemName;
	private int categoryID;
	private String categoryName;
	private int itemParCode;
    private double orgPrice; 
	
	public Prov_CompanyOrder() {
		super();
	}



	public Prov_CompanyOrder(String itemName,int itemParCode , int categoryID, String categoryName , double orgPrice) {
		super();
		this.itemName = itemName;
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.itemParCode = itemParCode;
		this.orgPrice=orgPrice;
	}



	public double getOrgPrice() {
		return orgPrice;
	}



	public void setOrgPrice(double orgPrice) {
		this.orgPrice = orgPrice;
	}



	public static Prov_CompanyOrder getProvOrder() {
		return provOrder;
	}



	public static void setProvOrder(Prov_CompanyOrder provOrder) {
		Prov_CompanyOrder.provOrder = provOrder;
	}



	public int getItemParCode() {
		return itemParCode;
	}

	public void setItemParCode(int itemParCode) {
		this.itemParCode = itemParCode;
	}



	static Prov_CompanyOrder provOrder = new Prov_CompanyOrder();

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
