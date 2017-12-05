/*import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;*/

public class HotelDemo {

	public static void main(String[] args) {
		hotelView view = new hotelView(new hotelModel());
	}
	
}

/*public class HotelDemo {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/hotelDB?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "Sallybears3";
	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public static void main(String[] args) {
		User currentUser = null;
	hotelModel model = new hotelModel();
	
		try {
			HotelDemo.createDatabase();
		Scanner input = new Scanner(System.in);
		String option = "";
<<<<<<< HEAD
		System.out.println("[L]og IN,  [C]reate Account, [Q]uit");
		option = input.nextLine();
		switch (option) {
		case "L":
			System.out.print("userName: ");
			String userName = input.nextLine();
		     System.out.print("password: ");
		     String passwords= input.nextLine();
		     currentUser = logIn(userName, passwords);
		     if(currentUser.getUsername().equals("")){
		    	 System.out.println("wrong user name or password");
		    	 break; }
		     
		     else{
		    	 // set correctly 
		    	 model.setCurrentUser(currentUser.getUsername());
		    	 ArrayList<Reservation> reservations=model.getAllReservations();
		    	 for(Reservation r: reservations){
		    	 System.out.println(r.toString()); }
		    	 // for manager operation
		    	 if(currentUser.getUserRole().equals("Manager")){
		    		 model.setCurrentRole("Manager");
		    		 System.out.println("[S]how all customers, [M]anage Accounts");
		    		 String managerOption = input.nextLine();
                 if(managerOption.equals("S")){
                	 showAllCustomer();
                	 }	 
		    	 // not finish
                 else if(managerOption.equals("M")){
		    		 
		    	 }
		    	 else{System.out.println("WRONG INPUT");}
		     }
		    	 // not finish
		    	 if(currentUser.getUserRole().equals("Customer")){
		    		 model.setCurrentRole("Customer");
		    		 model.getAllReservations();
		    		 System.out.println("[S]how Avialable rooms, [V]iew Accounts,[C]omplaint,[R]eservation history");
		    		 
		    		 
		    	 }
		    	 // for attendant 
		     }
		     
			break;
		case "C":	
			System.out.print("userName: ");
			String username = input.nextLine();
			System.out.println("FirstName");
			String firstname = input.nextLine();
			System.out.println("LastName");
			String lastname = input.nextLine();
			System.out.println("Age");
			int age = Integer.valueOf(input.nextLine());
			System.out.println("gender:");
			String gender =input.nextLine();
			System.out.println("[C]ustomer,[M]anager,[A]ttendant");
			String userRole ="Customer";
			String userR = input.nextLine();
			if(userR.equals("M")){userRole = "Manager";}
			if(userR.equals("A")){userRole = "Room Attendant";}
			String password ="";
			if(userR.equals("M")){password = "123";}
			else if(userR.equals("A")){password = "12345";}
			else{
				System.out.println("password:");
				password = input.nextLine();
			}
			User newUser = new User(username, firstname, lastname, userRole, age, gender, password);
			if (addUser(newUser)) {
				System.out.println("added succesfully");
			} else {
				System.out.println("username already exists");
=======
		// Enter character in the [] as option 
		while(!(option.equals("Q")))
		{
			System.out.println("[L]og IN,  [C]reate Account, [Q]uit");
			option = input.nextLine();
			switch (option) {
			case "L":
				System.out.print("userName: ");
				String userName = input.nextLine();
			     System.out.print("password: ");
			     String passwords= input.nextLine();
			     currentUser = logIn(userName, passwords);
			     if(currentUser.getUsername().equals("")){
			    	 System.out.println("wrong user name or password");
			    	 break; }
			     else{
			    	 // for manager operation
			    	 if(currentUser.getUserRole().equals("Manager")){
			    		 System.out.println("[S]how all customers, [M]anage Accounts");
			    		 String managerOption = input.nextLine();
                     if(managerOption.equals("S")){
                    	 showAllCustomer(); }	 
			    	 // not finish
                     else if(managerOption.equals("M")){
			    		 
			    	 }
			    	 else{System.out.println("WRONG INPUT");}
			     }
			    	 // not finish
			    	 if(currentUser.getUserRole().equals("Customer")){
			    		 System.out.println("[M]ake a reservation, [V]iew Accounts,[C]omplaint");
			    	 }
			    	 // for attendant 
			     }
			     
				break;
			case "C":	
				System.out.print("userName: ");
				String username = input.nextLine();
				System.out.println("FirstName");
				String firstname = input.nextLine();
				System.out.println("LastName");
				String lastname = input.nextLine();
				System.out.println("Age");
				int age = Integer.valueOf(input.nextLine());
				System.out.println("gender:");
				String gender =input.nextLine();
				System.out.println("[C]ustomer,[M]anager,[A]ttendant");
				String userRole ="Customer";
				String userR = input.nextLine();
				if(userR.equals("M")){userRole = "Mananger";}
				if(userR.equals("A")){userRole = "Room Attendant";}
				String password ="";
				if(userRole.equals("M")){password = "123";}
				if(userRole.equals("A")){password = "12345";}
				else{
					System.out.println("password");
					password = input.nextLine();
				}
				User newUser = new User(username, firstname, lastname, userRole, age, gender, password);
				if (addUser(newUser)) {
					System.out.println("added succesfully");
				} else {
					System.out.println("username already exists");
				}
			case "Q":
				break;
>>>>>>> origin/master
			}
		case "Q":
			break;
		
		// Enter character in the [] as option 
//		while(!(option.equals("Q")))
//		{
//			System.out.println("[L]og IN,  [C]reate Account, [Q]uit");
//			option = input.nextLine();
//			switch (option) {
//			case "L":
//				System.out.print("userName: ");
//				String userName = input.nextLine();
//			     System.out.print("password: ");
//			     String passwords= input.nextLine();
//			     currentUser = logIn(userName, passwords);
//			     if(currentUser.getUsername().equals("")){
//			    	 System.out.println("wrong user name or password");
//			    	 break; }
//			     
//			     else{
//			    	 model.setCurrentUser(currentUser.getUsername());
//			    	 // for manager operation
//			    	 if(currentUser.getUserRole().equals("Manager")){
//			    		 model.setCurrentRole("Manager");
//			    		 System.out.println("[S]how all customers, [M]anage Accounts");
//			    		 String managerOption = input.nextLine();
//                     if(managerOption.equals("S")){
//                    	 showAllCustomer();
//                    	 }	 
//			    	 // not finish
//                     else if(managerOption.equals("M")){
//			    		 
//			    	 }
//			    	 else{System.out.println("WRONG INPUT");}
//			     }
//			    	 // not finish
//			    	 if(currentUser.getUserRole().equals("Customer")){
//			    		 System.out.println("[S]how Avialable rooms, [V]iew Accounts,[C]omplaint");
//			    		 
//			    		 
//			    	 }
//			    	 // for attendant 
//			     }
//			     
//				break;
//			case "C":	
//				System.out.print("userName: ");
//				String username = input.nextLine();
//				System.out.println("FirstName");
//				String firstname = input.nextLine();
//				System.out.println("LastName");
//				String lastname = input.nextLine();
//				System.out.println("Age");
//				int age = Integer.valueOf(input.nextLine());
//				System.out.println("gender:");
//				String gender =input.nextLine();
//				System.out.println("[C]ustomer,[M]anager,[A]ttendant");
//				String userRole ="Customer";
//				String userR = input.nextLine();
//				if(userR.equals("M")){userRole = "Manager";}
//				if(userR.equals("A")){userRole = "Room Attendant";}
//				String password ="";
//				if(userR.equals("M")){password = "123";}
//				else if(userR.equals("A")){password = "12345";}
//				else{
//					System.out.println("password:");
//					password = input.nextLine();
//				}
//				User newUser = new User(username, firstname, lastname, userRole, age, gender, password);
//				if (addUser(newUser)) {
//					System.out.println("added succesfully");
//				} else {
//					System.out.println("username already exists");
//				}
//			case "Q":
//				break;
//			}
//		}	
			//User users = new User("customerT", "TestCustF", "TestCustL", "Customer", 21, "F", "2345");
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createDatabase() throws SQLException {

		// Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		String sql = "SELECT * FROM USER";
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();

		System.out.println("Database connected successfully...");
	}

	public static User logIn(String userName, String password) throws SQLException{
		
		String sql = "SELECT * FROM USER";
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if (resultSet.getString("username").equals(userName) && resultSet.getString("password").equals(password)) {
				return new User(resultSet.getString("username"), resultSet.getString("firstname"),
						resultSet.getString("lastname"), resultSet.getString("userRole"), resultSet.getInt("age"),
						resultSet.getString("gender"), resultSet.getString("password"));	
			}
		}
		return new User("", "", "", "", 0, "", "");
	}

	public static boolean addUser(User user) throws SQLException {

		String sql = "SELECT * FROM USER";
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if (resultSet.getString("username").equals(user.getUsername())) {
				return false;
			}
		}
		String insertion = "INSERT INTO user(username, firstName, lastName, password, age, gender, userRole) VALUES(?,?,?,?,?,?,?)";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement = conn.prepareStatement(insertion);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getFirstName());
		preparedStatement.setString(3, user.getLastName());
		preparedStatement.setString(4, user.getPassword());
		preparedStatement.setInt(5, user.getAge());
		preparedStatement.setString(6, user.getGender());
		preparedStatement.setString(7, user.getUserRole());
		preparedStatement.addBatch();
		System.out.println("After processing a batch of user");
		preparedStatement.executeBatch();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * from User");
		printResultSetfromFaculty(rs);
		return true;
	}
	// private static boolean addReservation(User user, ){

	// }
	// private static void showReservation(){

	// }
	private static void showAllCustomer() throws SQLException {
		ArrayList<User> users = new ArrayList<>();
		String sql = "SELECT * FROM USER";
		preparedStatement = conn.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if (resultSet.getString("userRole").equals("Customer")) {
				users.add(new User(resultSet.getString("username"), resultSet.getString("firstname"),
						resultSet.getString("lastname"), resultSet.getString("userRole"), resultSet.getInt("age"),
						resultSet.getString("gender"), resultSet.getString("password")));
			}
		}
		for (User user : users) {
			System.out.println("username:" + user.getUsername() + "firstName:" + user.getFirstName() + "lastName:"
					+ user.getLastName() + "age:" + user.getAge());
		}
	}

	private static void printResultSetfromFaculty(ResultSet rs) throws SQLException {
		while (rs.next()) {
			String name = rs.getString("username");
			int age = rs.getInt("age");
			System.out.println(" Name:" + name + " Age:" + age);
		}
	}
}
*/
