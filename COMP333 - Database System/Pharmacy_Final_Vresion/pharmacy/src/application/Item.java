package application;

public class Item {
	
	private int par_code;
	private String item_name;
	private int quantity;
	private String discription;
	private double sale_price;
	private double origen_price;
	private String provide_company_name;
	private int cat_id;
	private String exp_date;
	
	public Item() {
		super();
	}

	

	public Item(String item_name, int par_code, int quantity, String discription, double sale_price,
			double origen_price, String provide_company_name, int cat_id, String exp_date) {
		super();
		this.par_code = par_code;
		this.item_name = item_name;
		this.quantity = quantity;
		this.discription = discription;
		this.sale_price = sale_price;
		this.origen_price = origen_price;
		this.provide_company_name = provide_company_name;
		this.cat_id = cat_id;
		this.exp_date = exp_date;
	}







	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getPar_code() {
		return par_code;
	}

	public void setPar_code(int par_code) {
		this.par_code = par_code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public double getSale_price() {
		return sale_price;
	}

	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}

	public double getOrigen_price() {
		return origen_price;
	}

	public void setOrigen_price(double origen_price) {
		this.origen_price = origen_price;
	}

	public String getProvide_company_name() {
		return provide_company_name;
	}

	public void setProvide_company_name(String provide_company_name) {
		this.provide_company_name = provide_company_name;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}



	public String getExp_date() {
		return exp_date;
	}



	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	
	
}