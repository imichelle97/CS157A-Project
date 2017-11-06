import java.util.Date;

public class Reservation {
	
	private int reservationID;
	private String username;
	private Date startDate;
	private Date endDate;
	private int numOfDays;
	private double totalCost;
	private boolean cancelled;
	//private Room room;
	
	public Reservation(int reservationID, String username, Date startDate, Date endDate, 
			int numOdDays, double totalCost, boolean cancelled)	{
		this.reservationID = reservationID;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numOfDays = numOfDays;
		this.totalCost = totalCost;
		this.cancelled = cancelled;
	}
	
	public int getReservationID() {
		return reservationID;
	}
	
	public String getCustomer() {
		return username;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public int getNumOfDays() {
		return numOfDays;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public boolean getCancelled() {
		return cancelled;
	}
	
	/*public String toString() {
		
	}
	*/

}
