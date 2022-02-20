package application;

public class Employeedata {
	private int id;
	private String employee_name;
	private String birthday;
	private String date_of_employment;
	private String emp_password;
    private double work_hours;
    private double hour_price;
    private double amount_paid;
    
    static Employeedata emd;
    
	public Employeedata() {
		super();
	}

	public Employeedata(String employee_name, String birthday, String date_of_employment, String emp_password,
			double work_hours, double hour_price) {
		super();
		this.employee_name = employee_name;
		this.birthday = birthday;
		this.date_of_employment = date_of_employment;
		this.emp_password = emp_password;
		this.work_hours = work_hours;
		this.hour_price = hour_price;
	}

	
	public Employeedata(String employee_name, String birthday, String date_of_employment, String emp_password,
			double amount_paid) {
		super();
		this.employee_name = employee_name;
		this.birthday = birthday;
		this.date_of_employment = date_of_employment;
		this.emp_password = emp_password;
		this.amount_paid = amount_paid;
	}

	public double getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(double amount_paid) {
		this.amount_paid = amount_paid;
	}

	public double getWork_hours() {
		return work_hours;
	}

	public void setWork_hours(double work_hours) {
		this.work_hours = work_hours;
	}

	public double getHour_price() {
		return hour_price;
	}

	public void setHour_price(double hour_price) {
		this.hour_price = hour_price;
	}

	public int getId() {
		return id;
	}

	public String getEmp_password() {
		return emp_password;
	}

	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDate_of_employment() {
		return date_of_employment;
	}

	public void setDate_of_employment(String date_of_employment) {
		this.date_of_employment = date_of_employment;
	}

}
