

import java.util.Date;
import java.text.SimpleDateFormat;

public class Complaint {
		
private int complaintID;
	String customer;
	String complaint;
	Date time; 
	String resolvedBy;
	String solution;
public Complaint(int id, String customers, String com, String reso, String solut, Date t){
	complaintID = id;
	customer = customers;
	complaint = com;
	time = t;
	resolvedBy = reso;
	solution = solut;
}
public int getComplaintID(){
	return complaintID;
}
public String getCustomer(){
	return customer;
}
public String getComplaint(){
	return complaint;
}
public Date getTime(){
	return time;
}
public String getResolvedBy(){
	return resolvedBy;
}
public String getSolution(){
	return solution;
}

public String toString() {
	String str = "Username: " + customer + 
		"\nComplaint ID: " + complaintID + 
		"\nFiled on: " + new SimpleDateFormat("MM/dd/yyyy").format(time) + 
		"\nComplaint: " + complaint;
	if(resolvedBy != null) {
		str += "\nResolved By: " + resolvedBy + 
			"\nSolution: " + solution;
	}
	return str;
}

	

}
