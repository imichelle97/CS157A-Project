
public class User extends Account {
	
	private String password;
	
	public User(String username, String firstName, String lastName, String userRole, 
			int age, String gender, String password) {
		super(username, firstName, lastName, userRole, age, gender);
		this.setPassword(password);
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
