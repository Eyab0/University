package application;

public class invoiceData {

	private int order_id;
	private int quantity;
	private double full_sale_price;
	private double full_original_price;
	private int par_code;
	private int itemCat;
	private String itemName;
	private int emp_id;
	private String expDate;

	public invoiceData() {
		super();
	}

	public invoiceData(int order_id, int quantity, double full_sale_price, double full_original_price, int par_code,
			int itemCat, String itemName, int emp_id, String expDate) {
		super();
		this.order_id = order_id;
		this.quantity = quantity;
		this.full_sale_price = full_sale_price;
		this.full_original_price = full_original_price;
		this.par_code = par_code;
		this.itemCat = itemCat;
		this.itemName = itemName;
		this.emp_id = emp_id;
		this.expDate = expDate;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getFull_sale_price() {
		return full_sale_price;
	}

	public void setFull_sale_price(double full_sale_price) {
		this.full_sale_price = full_sale_price;
	}

	public double getFull_original_price() {
		return full_original_price;
	}

	public void setFull_original_price(double full_original_price) {
		this.full_original_price = full_original_price;
	}

	public int getPar_code() {
		return par_code;
	}

	public void setPar_code(int par_code) {
		this.par_code = par_code;
	}

	public int getItemCat() {
		return itemCat;
	}

	public void setItemCat(int itemCat) {
		this.itemCat = itemCat;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	@Override
	public String toString() {
		return "invoiceData [order_id=" + order_id + ", quantity=" + quantity + ", full_sale_price=" + full_sale_price
				+ ", full_original_price=" + full_original_price + ", par_code=" + par_code + ", itemCat=" + itemCat
				+ ", itemName=" + itemName + ", emp_id=" + emp_id + "]";
	}

}
