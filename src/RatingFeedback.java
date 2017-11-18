
public class RatingFeedback {
	
	int ratingID;
	String customer;
	int rating;

public RatingFeedback(int Id, String customer, int rate){

 ratingID = Id;
 this.customer = customer;
 rating = rate;
 
	
}

public int getratingID(){
	return ratingID;
	
}

public String getcustomer(){
	return customer;
	
}
public int getrating(){
	return rating;
}
	
public String toString() {
	String str = "Customer = " + customer + 
		"Rating Given: " + rating;
	
	return str;
}
	
}
