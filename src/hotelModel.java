import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	/* 
	 * Constructor
	 */
	public hotelModel() {
		TODAY.clear(Calendar.HOUR);	//The clear method sets calendar field(s) undefined
		TODAY.clear(Calendar.MINUTE);
		TODAY.clear(Calendar.SECOND);
		TODAY.clear(Calendar.MILLISECOND);
		
		currentUser = null;
		currentRole = null;
		reservations = new ArrayList<Reservation>();
	}
	
	public void setCurrentRole(String role) {
		currentRole = role;
	}
	
	public String getCurrentRole() {
		return currentRole;
	}
	
	public Account getCurrentUser() {
		return currentUser;
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	
	public void clearReservations() {
		reservations = new ArrayList<Reservation>();
	}
	
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
	
	public Complaint getComplaint(int id) {
		String sql = "SELECT * FROM complaint WHERE compaintID = " + id;
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
	
	public boolean updateCompaint(int id, String resolvedBy, String solution) {
		solution = solution.replace("'", "''");
		String sql = "UPDATE complaint SET resolvedBy = '" + resolvedBy + 
				"', solution = '" + solution + 
				"' where complaint ID = " + id;
		try {
			state.execute(sql);
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
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
	
	public boolean resolveTask(int id) {
		String sql = "UPDATE roomService SET completedBy = '" 
				+ currentUser.getUsername() + " WHERE taskID = "
				+ id;
		try {
			state.execute(sql);
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void updateRating(String username, int rate) {
		String sql = "UPDATE rating SET rating = '" + rate + 
				"WHERE customer = '" + username + "'";
		try {
			state.executeUpdate(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
