package application;

public class ordersData {

	private int order_id;
	private int empId;
	private String order_date;
	
	public ordersData() {
		super();
	}

	public ordersData(int order_id, int empId, String order_date) {
		super();
		this.order_id = order_id;
		this.empId = empId;
		this.order_date = order_date;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	
	
	
	
}
