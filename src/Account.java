import java.util.ArrayList;

public class Account {
	
	final private String username;
	private String firstName;
	private String lastName;
	private String userRole;
	private int age;
	private String gender;
	private ArrayList<Reservation> reservations;
	
	public Account(String username, String firstName, String lastName, String userRole, int age, String gender) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.age = age;
		this.gender = gender;
		reservations = new ArrayList<Reservation>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getUserRole() {
		return userRole;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public ArrayList<Reservation> getReservation() {
		return reservations;
	}
	
	public String toString() {
		return username + " " + firstName + " " + lastName + " " + userRole + " " + 
				age + " " + gender;
				
	}

}
