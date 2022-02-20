package application;

public class Manegar {

	String name;
	String password;
	
	static Manegar mng = new Manegar();
	
	public Manegar() {
		super();
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
