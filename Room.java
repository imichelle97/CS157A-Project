

public class Room {
private int roomID;
private double costsPerNight;
private String roomType;

public Room(int id, double costs, String  type){
	
	roomID = id;
	costsPerNight = costs;
	roomType = type;
}
public int getRoomId(){
	return roomID;
}
public double getCostsPerNight(){
	return costsPerNight;
	
}
public String getRoomType(){
	return roomType;
}
public void setCostsPerNight(double newAmount){
	costsPerNight = newAmount;
}
public void setRoomType(String newRoomType){
	roomType = newRoomType;
}
@Override
public boolean equals(Object obj) {
    Room that = (Room) obj;
    if(this.roomID == that.roomID && this.costsPerNight == that.costsPerNight && this.roomType.equals(that.roomType) ){
    	return true;
    }
    else{
    	return false;
    }
}

}
