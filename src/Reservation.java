import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
	
	private int reservationID;
	private String username;
	private Date startDate;
	private Date endDate;
	private int numOfDays;
	private double totalCost;
	private boolean cancelled;
	private Room room;
	
	public Reservation(int reservationID, String username, Room room, Date startDate, Date endDate, 
			int numOfDays, double totalCost, boolean cancelled)	{
		this.reservationID = reservationID;
		this.username = username;
		this.room = room;
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
	
	public Room getRoom() {
		return room;
	}
	
	public String toString() {
		
		return String.format("%s \n  to %s \nTotal Cost: %f"
				, (String)new SimpleDateFormat("MM/dd/yyyy").format(startDate),
				  (String)new SimpleDateFormat("MM/dd/yyyy").format(endDate),
				  numOfDays * room.getCostsPerNight());
	}
	

}
