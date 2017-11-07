
public class RoomService {
final private int taskID;
private int reservationID;
private String task;
private String userName;
private String completedBy;
public RoomService(int taskid,int reservation, String tasks, String  user,String complete){
	this.taskID = taskid;
	this.reservationID= reservation;
	this.task = tasks;
	this.userName = user;
	this.completedBy = complete;
}

public int getReservationID()
{
	return this.reservationID;
}
public int getTaskID(){
	return taskID;
}
// should have specific content of options such as morningcall, cleaning(can change to enum)
public String getTask(){
	return task;
}
public String getCompletedBy(){
	return this.completedBy;
}
public String getUserName(){
	return userName;
}
public void setReservationID(int reserve ){
	reservationID = reserve;
}
public void setCompeletedBy(String complete){
	completedBy = complete;
}
public void setUserName(String user){
	userName = user;
}
public void setTask(String t){
	task = t;
}

}
