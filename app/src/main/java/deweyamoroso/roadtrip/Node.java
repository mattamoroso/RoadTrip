
package deweyamoroso.roadtrip;


/**
 *
 * @author mattamoroso
 */
public class Node {
    
    //longitude and latitude to mark location of the node.
    private double longitude;
    private double latitude;
    private String name;
    private String address;
    private String time_arrived;
    private String time_left;
    private String type;
    private int useless;

    //node constructor
    public Node(String name, String address, double latitude, double longitude, String time_arrived, String time_left, String type, int useless){
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
        this.time_arrived = time_arrived;
        this.time_left = time_left;
        this.type = type;
        this.useless = useless;
    }
    
    //longitude getter
    public double getLongitude(){
        return longitude;
    }
    
    //latitude getter
    public double getLatitude(){return latitude;}

    //name getter
    public String getName(){
        return name;
    }

    //address getter
    public String getAddress(){return address;}

    //time_arrived setter
    public void setTime_Arrived(String s){
        time_arrived = s;
    }

    //time_arrived getter
    public String getTime_Arrived(){
        return time_arrived;
    }

    //time_left getter
    public String getTime_Left(){
        return time_left;
    }

    //time_left setter
    public void setTime_Left(String s){
        time_left = s;
    }

    public String getType(){
        return type;
    }
    
}