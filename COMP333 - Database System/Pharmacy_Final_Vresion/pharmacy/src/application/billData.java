package application;

public class billData {

	private int order_id;
	private String order_date;
	private double  full_price;
	private double  profits;
	private int emp_id;
	private String bill_type;
	public billData() {
		super();
	}
	public billData(int order_id, String order_date, double full_price, double profits, int emp_id, String bill_type) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.full_price = full_price;
		this.profits = profits;
		this.emp_id = emp_id;
		this.bill_type = bill_type;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public double getFull_price() {
		return full_price;
	}
	public void setFull_price(double full_price) {
		this.full_price = full_price;
	}
	public double getProfits() {
		return profits;
	}
	public void setProfits(double profits) {
		this.profits = profits;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getBill_type() {
		return bill_type;
	}
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}
	@Override
	public String toString() {
		return "billData [order_id=" + order_id + ", order_date=" + order_date + ", full_price=" + full_price
				+ ", profits=" + profits + ", emp_id=" + emp_id + ", bill_type=" + bill_type + "]";
	}
	
	
}
