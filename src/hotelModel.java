import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/*
 * This class is where the functional methods will be
 */
public class hotelModel {
	
	public static final GregorianCalendar TODAY = new GregorianCalendar();
	
	/*
	 * Variables for the transaction
	 */
	private Account currentUser;
	private String currentRole;	//Either customer, manager, or attendant
	private ArrayList<Reservation> reservations;
	
	
	private Connection conn = JDBCUtil.getConnectionByDriverManager();
   private Statement state = JDBCUtil.getStatement(conn);
	private ArrayList<ChangeListener> listeners;
	
	/* 
	 * Constructor
	 */
	public hotelModel() {
		TODAY.clear(Calendar.HOUR);	
		TODAY.clear(Calendar.MINUTE);
		TODAY.clear(Calendar.SECOND);
		TODAY.clear(Calendar.MILLISECOND);
		
		listeners = new ArrayList<>();
		currentUser = null;
		currentRole = null;
		reservations = new ArrayList<Reservation>();
	}
	
	public void setCurrentRole(String role) {
		currentRole = role;
		update();
	}
	
	public String getCurrentRole() {
		return currentRole;
	}
	
	public Account getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(String username) {
		if(username == null) {
			currentUser = null;
			currentRole = null;
		}
		else {
			currentUser = getUserAccount(username);
			currentRole = currentUser.getUserRole();
		}
		update();
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	
	public void clearReservations() {
		reservations = new ArrayList<Reservation>();
	}
	
	public String sqlDate(String date) {
		
		return "STR_TO_DATE('"+ date+ "','%m/%d/%Y')";
	}
	
	/*
	 * Add an account to the database
	 */
	public boolean addAccount(String username, String password, String firstName, String lastName, String userRole, int age, String gender) {
		username = username.replace("'", "''");
		password = password.replace("'", "''");
		firstName = firstName.replace("'", "''");
		lastName = lastName.replace("'", "''");
		String g= gender.equals("Female") ? "F" : "M";
		String sql = String.format("INSERT INTO user(username, firstName, lastName, password, age, gender, userRole)  VALUES('%s','%s','%s','%s',%d,'%s','%s')",
				username, firstName, lastName, password, age,g, userRole);
		System.out.println(username);
		System.out.println(userRole);
		System.out.println(age);
		System.out.println(gender);
		try {
			state.execute(sql);
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get account information
	 */
	public User getAccountInfo(String username) {
		User user = null;
		String sqlAccountInfo = "SELECT * FROM USER WHERE username = '" + username + "'";
		try {
			ResultSet rs = state.executeQuery(sqlAccountInfo);
			if(rs.next()) {
				user = new User(rs.getString("username"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("userRole"), 
						rs.getInt("age"), rs.getString("gender"), rs.getString("password"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/*
	 * Get user's account
	 */
	public Account getUserAccount(String username) {
		Account account = null;
		String sqlAccount = "SELECT username, firstName, lastName, userRole, age, gender FROM USER where username = '"
				+username+"'" ;
		String sqlResult =  "SELECT customerName, cancelled, reservationID, ROOM.roomID, "
				+ "startDate, endDate, totalNumOfDays, totalCost, costPerNight, roomType FROM ROOM right outer join RESERVATION ON ROOM.roomID = RESERVATION.roomID WHERE customerName = '"
				+ username + "'";
		try {
			ResultSet rs = state.executeQuery(sqlAccount);
			while(rs.next()) {
				account = new Account(rs.getString("username"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("userRole"), rs.getInt("age"),
						rs.getString("gender"));
			}
			rs.close();
			rs = state.executeQuery(sqlResult);
			while(rs.next()) {
				Room r = new Room(rs.getInt("roomID"), rs.getDouble("costPerNight"),
						rs.getString("roomType"));
				account.getReservation().add(new Reservation(rs.getInt("reservationID"),
						rs.getString("customerName"), r, rs.getDate("startDate"),
						rs.getDate("endDate"), rs.getInt("totalNumOfDays"), rs.getDouble("totalCost"),
						rs.getBoolean("cancelled")));
			}
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return account;
	}
	
	/*
	 * Change the user's account information/update the account information
	 */
	public boolean changeAccount(String password, String firstName, String lastName, int age, String gender) {
		String sql = "UPDATE USER SET password = '" + password + "' ," 
				+ " firstName = '" + firstName 
				+ "' ,"+ " lastName = '" 
				+ lastName + "' ,"
				+" age = " + age +","
				+ " gender = '" + gender + "'"+ " WHERE username = '" + currentUser.getUsername() + "'";
					try {
						state.executeUpdate(sql);
						return true;
					}
					catch(SQLException e) {
						e.printStackTrace();
						return false;
					}
	}
	
	/*
	 * Add reservations to the database
	 */
	public boolean addReservation(int roomID, String checkIn, String checkOut) {
		String sql = String.format("INSERT INTO RESERVATION(roomID, customerName, startDate, endDate) "
				+ "VALUES(%d,'%s',%s,%s)", roomID, currentUser.getUsername(), sqlDate(checkIn),
				sqlDate(checkOut));
		try {
			state.execute(sql);
			setCurrentUser(currentUser.getUsername());
			ArrayList<Reservation> reservation = currentUser.getReservation();
			reservations.add(reservation.get(reservation.size()-1));
			update();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get all reservations in the database
	 */
	public ArrayList<Reservation> getAllReservations() {
		ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
		String sql = "SELECT cancelled, customerName, reservationID, ROOM.roomID, startDate, endDate,"
				+ " totalNumOfDays, totalCost, costPerNight, roomType"
				+ " FROM ROOM right outer join RESERVATION"
				+ " ON ROOM.roomID = RESERVATION.roomID";
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				Room room = new Room(rs.getInt("roomID"), rs.getDouble("costPerNight"), rs.getString("roomType"));
				Reservation reservation = new Reservation(rs.getInt("reservationID"), rs.getString("customerName"),
						room, rs.getDate("startDate"), rs.getDate("endDate"), rs.getInt("totalNumOfDays"),
						rs.getDouble("totalCost"), rs.getBoolean("cancelled"));
				reservationList.add(reservation);
			}
			rs.close();
			return reservationList;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Get specific reservation based on min and max of total cost range
	 */
	public ArrayList<Reservation> getReservations(String orderBy, Double min, Double max) {
		ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
		String sql = "SELECT cancelled, customerName, reservationID, ROOM.roomID, startDate, endDate,"
				+ " totalNumOfDays, totalCost, costPerNight, roomType"
				+ " FROM ROOM right outer join RESERVATION"
				+ " ON ROOM.roomID = RESERVATION.roomID";
		if (min != null) {
			sql += " HAVING totalCost >= " + min;
			if (max != null)
				sql += " AND totalCost <= " + max;
		}
		else
			if (max != null)
				sql += " HAVING totalCost <= " + max;

		sql += " ORDER BY " + orderBy;
		
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				Room room = new Room(rs.getInt("roomID"), rs.getDouble("costPerNight"), rs.getString("roomType"));
				Reservation reservation = new Reservation(rs.getInt("reservationID"), rs.getString("customerName"),
						room, rs.getDate("startDate"), rs.getDate("endDate"), rs.getInt("totalNumOfDays"),
						rs.getDouble("totalCost"), rs.getBoolean("cancelled"));
				reservationList.add(reservation);
			}
			rs.close();
			return reservationList;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}	
	
	/*
	 * Cancel the reservation
	 */
	public boolean cancelReservation(Reservation r) {
		String sql = "UPDATE reservation SET cancelled = true WHERE reservationID = " + r.getReservationID();
		try {
			state.execute(sql);
			
			setCurrentUser(currentUser.getUsername());
			update();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get all the users, order them by userRole
	 */
	public ArrayList<Account> getUsersRole() {
		ArrayList<Account> users = new ArrayList<Account>();
		String sql = "SELECT * FROM USER ORDER BY userRole";
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				users.add(new Account(rs.getString("username"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("userRole"), rs.getInt("age"), rs.getString("gender")));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/*
	 * Get all users
	 */
	public ArrayList<Account> getAllUsers(Integer numOfReservations) {
		ArrayList<Account> userList = new ArrayList<Account>();
		String sqlUser = "";
		ArrayList<String> usernames = new ArrayList<String>();
		
		//User does not have any reservations
		if(numOfReservations == null)
			sqlUser = "SELECT username FROM USER";
		else {
			//User did make reservation(s)
			sqlUser = "SELECT username FROM USER RIGHT OUTER JOIN "
					+ "(SELECT customerName, reservationID, ROOM.roomID, startDate, "
					+ "endDate, totalNumOfDays, totalCost, costPerNight, roomType "
					+ "FROM ROOM RIGHT OUTER JOIN RESERVATION ON ROOM.roomID = RESERVATION.roomID "
					+ "GROUP BY customerName HAVING COUNT(*) >= " + numOfReservations + ") as reservations "
					+ "on USER.username = RESERVATION.customerName";			
		}
		try {
			ResultSet rs = state.executeQuery(sqlUser);
			while(rs.next()) {
				usernames.add(rs.getString("username"));
			}
			rs.close();
			for(String s : usernames) 
				userList.add(getUserAccount(s));
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return userList;			
	}
	
	/*
	 * Check to see if the user actually exists in the database
	 */
	public boolean checkUser(String username) {
		String sql = "SELECT username FROM USER";
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("username").equals(username))
					return true;
			}
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}	
		return false;	
	}
	
	/*
	 * Get the user's password
	 */
	public String getUserPassword(String username) {
		String password = "";
		String sql = "SELECT password FROM USER WHERE username = '" + username + "'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if(rs.next()) {
				password = rs.getString("password");
				rs.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		return password;
	}
	
	/*
	 * Check the user's password
	 */
	public boolean checkUserPassword(String username, String password) {
		String sql = "SELECT password FROM USER WHERE username = '" + username + "'";
		try {
			ResultSet rs = state.executeQuery(sql);
			if(rs.next() && rs.getString("password").equals(password)) {
				rs.close();
				return true;
			}			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Delete the user
	 */
	public void deleteUser(String username) {
		// for foriegn key constraint, need delete the query first 
		String delete = "delete from reservation where customerName = '" + username +"'";
		String deleteCompliant = "delete from complaint where customer = '" + username + "'";
		String deleteRating = "delete from ratingFeedback where customer = '" + username + "'";
		String deleteRoomService = "delete from roomService where username = '"+ username +"'";
		String sql = "DELETE FROM USER WHERE username = '" + username + "'";
		try {
			state.execute(delete);
			state.execute(deleteCompliant);
			state.execute(deleteRating);
			state.execute(deleteRoomService);
			//state.executeUpdate(delete);
			state.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all available rooms based on time
	 */
	public ArrayList<Room> getAvailableRooms(String checkIn, String checkOut) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		String in = sqlDate(checkIn);
		String out = sqlDate(checkOut);
		String sql = "SELECT * FROM ROOM WHERE roomID NOT IN"
				+ " (SELECT distinct ROOM.roomID"
				+ " FROM ROOM LEFT OUTER JOIN RESERVATION"
				+ " ON ROOM.roomID = RESERVATION.roomID"
				+ " WHERE " + in + " = RESERVATION.startDate"
				+ " OR " + in + " = RESERVATION.endDate"
				+ " OR " + out + "= RESERVATION.startDate"
				+ " OR " + out + " = RESERVATION.endDate"
				+ " OR " + "(RESERVATION.startDate < " + out + " AND RESERVATION.endDate > " + in + ")"
				+ " OR (" + in + " < RESERVATION.startDate and " + out + " > RESERVATION.startDate)"
				+ " OR (" + in + " < RESERVATION.endDate and " + out + " > RESERVATION.endDate))";
		try {
			ResultSet rs = state.executeQuery(sql);
			
			if(rs== null){
				return roomList;
			}
			
			while(rs.next()) {
				roomList.add(new Room(rs.getInt("roomID"), rs.getDouble("costPerNight"), rs.getString("roomType")));
			}
			rs.close();
			return roomList;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/*
	 * Add a complaint
	 */
	public boolean addComplaint(String customerName, String complaint) {
		String sql = String.format("INSERT INTO COMPLAINT(customer, complaint)"
				+ " VALUES('%s', '%s')", currentUser.getUsername(), complaint);
		try {
			state.execute(sql);
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get all the complaints
	 */
	public ArrayList<Complaint> getAllComplaints() {
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		
		String sql = "SELECT * FROM complaint";
		
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				complaints.add(new Complaint(rs.getInt("complaintID"),
						rs.getString("customer"),
						rs.getString("complaint"),
						rs.getString("resolvedBy"),
						rs.getString("solution"),
						rs.getDate("time")));						
			}
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return complaints;
	}
	
	/*
	 * Get specific complaint based on complaintID
	 */
	public Complaint getComplaint(int id) {
		String sql = "SELECT * FROM complaint WHERE complaintID = " + id;
		Complaint c = null;
		
		try {
			ResultSet rs = state.executeQuery(sql);
			if(rs.next()) {
				c = new Complaint(rs.getInt("complaintID"),
						rs.getString("customer"),
						rs.getString("complaint"),
						rs.getString("resolvedBy"),
						rs.getString("solution"),
						rs.getDate("time"));	
				rs.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return c;
	}
	
	/*
	 * Update complaint
	 */
	public boolean updateCompaint(int id, String resolvedBy, String solution) {
		solution = solution.replace("'", "''");
		String sql = "UPDATE complaint SET resolvedBy = '" + resolvedBy + 
				"', solution = '" + solution + 
				"' where complaintID = " + id;
		try {
			state.execute(sql);
			update();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/*
	 * Add room service based on username and task
	 */
	public boolean addRoomService(String username, String task) {
		String sql = String.format("INSERT INTO roomService(username, task)"
				+ " VALUES('%s', '%s')", username, task);
		try {
			state.execute(sql);
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get room service
	 */
	public ArrayList<RoomService> getRoomService() {
		ArrayList<RoomService> roomService = new ArrayList<RoomService>();
		String sql = "SELECT * FROM roomService WHERE completedBy is NULL";
		try {
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				roomService.add(new RoomService(rs.getInt("taskID"),
						rs.getInt("reservationID"),
						rs.getString("task"),
						rs.getString("username"),
						rs.getString("completedBy")));
			}
			return roomService;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * Resolve the task based on taskID
	 */
	public boolean resolveTask(int taskID) {
		String sql = "UPDATE roomService SET completedBy = '" 
				+ currentUser.getUsername() + "' WHERE taskID = "
				+ taskID;
		try {
			state.execute(sql);
			update();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Update rating
	 */
	public void updateRating(String username, int rate) {
		String sql = String.format("UPDATE ratingFeedback SET rating = %d WHERE customer = '%s'",rate,username);
		try {
			ResultSet zoeken = state.executeQuery("SELECT * FROM ratingFeedback WHERE customer = '" + username + "'");
		       boolean val = zoeken.next(); //next() returns false if there are no-rows retrieved 
		        if(val==false){
		        	String insertion = String.format("INSERT into ratingFeedback (customer, rating) VALUES('%s',%d)",username,rate);
					state.execute(insertion);
		         }
		        else{	
			state.executeUpdate(sql);}
			
			update();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Archive
	 */
	public boolean archive(Date date) {
		Timestamp ts = new Timestamp(date.getTime());
		try {
			CallableStatement call = conn.prepareCall("{call archiveAll(?)}");
			call.setTimestamp("cutoffDate", ts);
			call.execute();
			update();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Print out receipt
	 */
	public String getReceipt() {		
		String totalUser = "SELECT COUNT(*) FROM USER",
		totalCustomer = "SELECT COUNT(*) FROM USER WHERE userRole = 'Customer'",
		totalManager = "SELECT COUNT(*) FROM USER WHERE userRole = 'Manager'",
		avgAge = "SELECT AVG(age) FROM USER", 
		avgNumRes = "SELECT AVG(reservationCount) FROM (SELECT COUNT(*) AS reservationCount FROM RESERVATION GROUP BY customerName) counts",
		avgCost = "SELECT AVG(totalCost) FROM RESERVATION",
		avgRating = "SELECT AVG(rating) FROM ratingFeedback",
		result = "";
		
		try {
			int totalUsers = 0;
			ResultSet rs = state.executeQuery(totalUser);
			if (rs.next()) 
				totalUsers = Integer.parseInt(rs.getString(1));
			rs.close();
			
			rs = state.executeQuery(totalCustomer);
			int numOfCustomers = 0;
			int numOfEmployees = 0;
			if (rs.next()){
				numOfCustomers = Integer.parseInt(rs.getString(1));
				numOfEmployees = totalUsers - numOfCustomers;
			}
			rs.close();
			
			rs = state.executeQuery(totalManager);
			int numOfManagers = 0;
			int numOfRoomAttendants = 0;
			if (rs.next()){
				numOfManagers = Integer.parseInt(rs.getString(1));
				numOfRoomAttendants = numOfEmployees - numOfManagers;
			}

			result += "Total employee: " + numOfEmployees;
			result += "\nNumber of manager: " + numOfManagers;
			result += "\nNumber of room attendant: " + numOfRoomAttendants;
			result += "\nNumber of registered customer: " + numOfCustomers;	
			rs = state.executeQuery(avgAge);
			if (rs.next()){
				String avg = "0";
				if(rs.getString(1) != null){
					avg = String.format( "%.2f", Double.parseDouble(rs.getString(1)) );
				}
				result += "\nAverage age of users: " + avg;
			}
			rs.close();
			
			rs = state.executeQuery(avgNumRes);
			if (rs.next()){
				String avg = "0";
				if(rs.getString(1) != null){
					avg = String.format( "%.2f", Double.parseDouble(rs.getString(1)) );
				}
				result += "\nAverage number of reservations per customer: " + avg;
			}
			rs.close();
			
			rs = state.executeQuery(avgCost);
			if (rs.next()){
				String avg = "0";
				if(rs.getString(1) != null){
					avg = String.format( "%.2f", Double.parseDouble(rs.getString(1)) );
				}
				result += "\nAverage cost of a reservation: " + avg;
			}
			rs.close();
			
			rs = state.executeQuery(avgRating);
			if (rs.next()){
				String avg = "0";
				if(rs.getString(1) != null){
					avg = String.format( "%.2f", Double.parseDouble(rs.getString(1)) );
				}
				result += "\nAverage rating from customer: " + avg;
			}
			rs.close();
			
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	public void update() {
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}
}
