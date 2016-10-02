package deweyamoroso.roadtrip;

/**
 * Created by Tobin Dewey on 9/18/2016.
 */
public class Utility {

    private int FinalUtility;
    private String Type;
    private String Address;
    private String[] OpenHours;
    private int TimeToComplete;
    private double BaseUtility = (100 * (TimeToComplete * .01));


    //timetocomplete getter
    public int getTimeToComplete(){
        return TimeToComplete;
    }

    //BaseUtility getter
    public double getBaseUtility(){
        return BaseUtility;
    }

    //Type getter
    public String getType(){
        return Type;
    }

    //Address getter
    public String getAddress(){
        return Address;
    }

    //Type getter
    public String[] getOpenHours(){
        return OpenHours;
    }

    //longitude finalutility
    public int calcUtility(){
        return FinalUtility;
    }

}
