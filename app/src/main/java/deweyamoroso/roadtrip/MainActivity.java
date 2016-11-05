package deweyamoroso.roadtrip;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask;
import android.location.Location;

import java.io.*;
import java.lang.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

//USED FOR IMPLEMENTING TIME
import java.util.Date; //used to store time and date input in special "date" object format.
import java.text.SimpleDateFormat; //used to set the format for information being turned into a date object
import java.util.Calendar; //used to convert a "date" object into a "calendar" object, which is more flexible. Calendar objects can have time "added" to them so we can keep track of time as the algorithm runs.
import java.util.concurrent.TimeUnit; //used for finding the "difference" in minutes between two dates (departure and arrival)


//import com.google.android.gms.location.places.Places;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Iterator;

//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import java.net.URLEncoder;
//import java.net.*;
//import java.lang.String.*;
//import java.io.IOException;



public class MainActivity extends AppCompatActivity {
    List<Node> nodelist = new LinkedList(); //Initialize linked list of nodes, I.E. our route.
    String activity_types[] = {"amusement_park", "aquarium", "art_gallery", "bakery", "bar", "book_store", "bowling_alley", "cafe", "campground", "casino", "cemetery", "clothing_store", "department_store", "electronics_store", "jewelry_store", "library", "liquor_store", "lodging", "meal_delivery", "meal_takeaway", "movie_theater", "movie_rental", "museum", "night_club", "park", "restaurant", "shopping_mall", "spa", "store", "zoo"};
    int activity_time[] = {360, 240, 180, 30, 120, 30, 180, 60, 720, 240, 45, 30, 120, 30, 30, 60, 20, 720, 60, 60, 150, 20, 180, 180, 45, 90, 180, 180, 30, 240};
    int tmpflag = 0; //TESTING VALUE TO LIMIT NUMBER OF TIME ASYNCTASK LOOPS, REMOVE LATER ONCE PROPER IF CASE IS MADE
    long time_remaining;
    Calendar current_time = Calendar.getInstance();
    Node arrival;
    int rnd;

    //Oncreate function initializes page and links to xml file.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when the user clicks the Generate Roadtrip button */
    public void GenerateRoute(View view) throws Exception {

        //initializing EditText variables and linking them to EditText sections of xml file. Note: EditText creates space for user input.
        EditText dl = (EditText) findViewById(R.id.departure_location);
        EditText dd = (EditText) findViewById(R.id.departure_date);
        EditText dt = (EditText) findViewById(R.id.departure_time);
        EditText al = (EditText) findViewById(R.id.arrival_location);
        EditText ad = (EditText) findViewById(R.id.arrival_date);
        EditText at = (EditText) findViewById(R.id.arrival_time);

        //Grabs Text attribute (what user types in) of EditText sections of xml and casts them as Strings.
        String departure_location = dl.getText().toString();
        String departure_date = dd.getText().toString();
        String departure_time = dt.getText().toString();
        String arrival_location = al.getText().toString();
        String arrival_date = ad.getText().toString();
        String arrival_time = at.getText().toString();

        //Taking the date and time info from user input and creating Date objects.
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String datetemp = ""+ departure_date +" "+ departure_time + ":00";
        Date departure = new Date();
        departure = format.parse(datetemp);

        datetemp = ""+ arrival_date +" "+ arrival_time + ":00";
        Date arrival = new Date();
        arrival = format.parse(datetemp);

        long duration = arrival.getTime() - departure.getTime(); //finds duration between dates/times in milliseconds

        //This is where timeunit utility is used. Converts previous calculation into minutes.
        //This is used to decide when to end the loop.
        time_remaining = TimeUnit.MILLISECONDS.toMinutes(duration);

        //This is where current time is initialized from the departure date. The calendar object let us "add" time to it to keep track of the day/hour.
        current_time.setTime(departure);

        //Calls initialize method which starts node creation and the asynctask loop, eventually loading the GeneratedRouteResults page.
        initialize(departure_location, arrival_location);
    }

    //Initialization method. WIP: Will act as root method which branches into other methods in order to build complete list/array of nodes to be passed to results activity.
    // This method will eventually take all user inputs as arguments.
    // Currently: initializes starting node given departure_location String.
    public void initialize(String departure1, String arrival1) {
    //Uses geocoding to get latitude and longitude of user entered locations.
        double lattemp=0, lontemp=0;

        try {
            Geocoder g;
            g = new Geocoder(this);
            List<Address> addressList = g.getFromLocationName(departure1, 1);
            lattemp = (addressList.get(0).getLatitude());
            lontemp = (addressList.get(0).getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        String lat = Double.toString(lattemp);
        String lon = Double.toString(lontemp);

        Date datetemp = current_time.getTime();
        String time_arrived = datetemp.toString();
        Node departure = new Node( "Departure Location",departure1, lattemp, lontemp, time_arrived, time_arrived); //Create new node from departure information provided by user.
        nodelist.add(departure); //our departure node is the first node added to the list

        try {
            Geocoder g;
            g = new Geocoder(this);
            List<Address> addressList = g.getFromLocationName(arrival1, 1);
            lattemp = (addressList.get(0).getLatitude());
            lontemp = (addressList.get(0).getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        arrival = new Node ( "Destination",arrival1, lattemp, lontemp, time_arrived, time_arrived);

        //latitude and logitude cast as strings to be supplied to asynctask
        String radius = "50000"; //Highest possible radius. 50,000m or approx. 31 miles. Later on this might be a function of distance between departure and arrival.

        //async task arguments stored as array because you are not allowed to supply multiple args to it
        String[]TaskParameters = new String[3]; //initializing and filling array of doubles to be sent to request
        TaskParameters[0] = lat;
        TaskParameters[1] = lon;
        TaskParameters[2] = radius;

        //Async task object created and executed with appropriate arguments.
        AsyncTask x = new MyAsyncTask().execute(TaskParameters);
    }


//async task class, parameters ordered as follows: input type, in-progress output type, post-execute output type
    private class MyAsyncTask extends AsyncTask<String[], String, String> {

//doInBackground is the main method of the async class. This is the separate thread being run in background when async object is being executed.
        @Override
        protected String doInBackground(String[]... TaskParameters){ //When req. parameters presented in this form (as is required in this case) it means that the method call can pass zero or more of this type.
            //eg. execute(), execute(TaskParameters), and execute(TaskParameters, TaskParameters2) are all viable.


            String results = "";
            JavaHttpUrlConnectionReader j = new JavaHttpUrlConnectionReader(); //need to make instance of object to run object method
            try
            {
                //Url is built using inputs
                String myUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
                myUrl+=TaskParameters[0][0];
                myUrl+=",";
                myUrl+=TaskParameters[0][1];
                myUrl+="&radius=";
                myUrl+=TaskParameters[0][2];
                //myUrl+="&type=restaurant&name=cafe&key=AIzaSyBDOGDt8l-1_ON-1xCQIUwakCWOtqynrEM"; //example can include name, might be good for finding restaurant type for example
                myUrl+="&type=";
                //activity type is chosen arbitrarily right now
                rnd = new Random().nextInt(activity_types.length);
                myUrl+= activity_types[rnd];
                myUrl+="&key=AIzaSyBDOGDt8l-1_ON-1xCQIUwakCWOtqynrEM";


                results = j.doHttpUrlConnectionAction(myUrl); //calls method to make request itself

                if (results.contains("\"status\" : \"ZERO_RESULTS\"")){ //Basically if the url request can't find anything, the URL is rebuilt with a new type and tries again.
                    myUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
                    myUrl+=TaskParameters[0][0];
                    myUrl+=",";
                    myUrl+=TaskParameters[0][1];
                    myUrl+="&radius=";
                    myUrl+=TaskParameters[0][2];
                    //myUrl+="&type=restaurant&name=cafe&key=AIzaSyBDOGDt8l-1_ON-1xCQIUwakCWOtqynrEM"; //example can include name, might be good for finding restaurant type for example
                    myUrl+="&type=";
                    //activity type is chosen arbitrarily right now
                    rnd = new Random().nextInt(activity_types.length);
                    myUrl+= activity_types[rnd];
                    myUrl+="&key=AIzaSyBDOGDt8l-1_ON-1xCQIUwakCWOtqynrEM";

                    results = j.doHttpUrlConnectionAction(myUrl); //calls method to make request itself
                }

            }
            catch (Exception e)
            {
                // deal with the exception in your "controller"
            }


            return results; //upon completion the url request result is sent as input to the onPostExecute method
        }


//onPostExecute is immediately run on completion of the doInBackground task.
        @Override
        protected void onPostExecute(String results) {
            //remove comments to see what we grab from json responses in logcat
            //System.out.println(results);

            //our json string is split line by line into an array here
            String[] jsonresults = results.split(System.getProperty("line.separator"));

            //This for loop grabs info for the first result from the json response
            for(int i =0; i< jsonresults.length; i++){System.out.println(jsonresults[i]);}
            //System.out.println();

            int name = 0;
            int address = 0;
            int index = 0;
            while(name == 0 || address == 0){
                if(jsonresults[index].contains("\"name\"")){name = index;}
                else if(jsonresults[index].contains("\"vicinity\"")){address = index;}
               index++;
            }


//Latitude and Longitude numbers have to be isolated from the rest of the line before being cast as doubles. Kind of a quick and dirty solution to this.
            jsonresults[0] = jsonresults[0].substring(23, (jsonresults[0].length() - 1));
            jsonresults[1] = jsonresults[1].substring(23, jsonresults[1].length());
            //Node is created with the collected information, a new node is created, and then added to the node linked list.

            //adjusting current time by distance driven to get time arrived
            Node temp = nodelist.get(nodelist.size() - 1); //Gets last added node.
            //Following code chunk calculates number of miles between previous location and new location. NOTE: COULD PROBABLY BE MADE INTO A METHOD
            Location A = new Location("");
            A.setLatitude(temp.getLatitude());
            A.setLongitude(temp.getLongitude());
            Location B = new Location("");
            B.setLatitude(Double.parseDouble(jsonresults[0]));
            B.setLongitude(Double.parseDouble(jsonresults[1]));
            double distance = A.distanceTo(B);
            distance = (distance / 1609.34) * 2; //The 2 is an decent approximation from miles to minutes of travel for the distances we are dealing with.
            int distint = (int) distance; //number of minutes of travel

            current_time.add(Calendar.MINUTE, (distint)); //these minutes of driving are added to current_time
            Date datetemp = current_time.getTime();
            String time_arrived = datetemp.toString();

            //adjusting current time by time spent at activity to get time left
            current_time.add(Calendar.MINUTE, (activity_time[rnd]));
            datetemp = current_time.getTime();
            String time_left = datetemp.toString();

            Node next = new Node(jsonresults[name] ,jsonresults[address], Double.parseDouble(jsonresults[0]), Double.parseDouble(jsonresults[1]), time_arrived, time_left);
            nodelist.add(next);
            //Temp value to just arbitrarily stop this loop after a chosen number of iterations.
            tmpflag++;

            //To be replaced with a better condition. Ex. we're running out of time and need to travel to the final node.
if(tmpflag <= 5){
            //latitude and longitude cast as strings to be supplied to asynctask
            String radius = "50000"; //Highest possible radius. 50,000m or approx. 31 miles. Later on this might be a function of distance between departure and arrival.

            //async task arguments stored as array because you are not allowed to supply multiple args to it
            String[]TaskParameters = new String[3]; //initializing and filling array of doubles to be sent to request
            TaskParameters[0] = jsonresults[0];
            TaskParameters[1] = jsonresults[1];
            TaskParameters[2] = radius;

            //Async task object created and executed with appropriate arguments.
            AsyncTask x = new MyAsyncTask().execute(TaskParameters);}

//This condition is when we have made it to the final node and need to prepare the nodelist to be sent to generated route results.
else{
    String[] nodearray = new String[nodelist.size() * 6];
    int k = 0;
    for(int i = 0; i < nodelist.size(); i++ ){
        temp = nodelist.get(i);
        nodearray[k] = temp.getName();
        k++;
        nodearray[k] = temp.getAddress();
        k++;
        nodearray[k] = Double.toString(temp.getLatitude());
        k++;
        nodearray[k] = Double.toString(temp.getLongitude());
        k++;
        nodearray[k] = temp.getTime_Arrived();
        k++;
        nodearray[k] = temp.getTime_Left();
        k++;
    }

    for(int i = 0; i < nodearray.length; i++ ){System.out.println(nodearray[i]);}
            //A new activity can be started here. For example, when we reach our destination and our linked list is filled.
            Intent intent = new Intent(getApplicationContext(), GeneratedRouteResults.class);
            //Intent method used to pass variables along to next activity's onCreate method. Will eventually pass list/array of nodes.
            intent.putExtra("EXTRA_MESSAGE", nodearray);
            startActivity(intent);
}
        }


    }

    private class JavaHttpUrlConnectionReader
    {
        public JavaHttpUrlConnectionReader() {}

        /**
         * Returns the output from the given URL.
         *
         * I tried to hide some of the ugliness of the exception-handling
         * in this method, and just return a high level Exception from here.
         * Modify this behavior as desired.
         *
         * @param desiredUrl
         * @return
         * @throws Exception
         */
        private String doHttpUrlConnectionAction(String desiredUrl)
                throws Exception
        {
            URL url = null;
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try
            {
                // create the HttpURLConnection
                url = new URL(desiredUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // just want to do an HTTP GET here
                connection.setRequestMethod("GET");

                // give it 15 seconds to respond
                connection.setReadTimeout(15*1000);
                connection.connect();

                // read the output from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    System.out.println(line); //printed to logcat for debugging purposes
                    //JSON response trimmed to only include relevant info.
                    if(line.contains("\"name\"") || line.contains("\"price_level\"") || line.contains("\"rating\"") || line.contains("\"vicinity\"") || line.contains("\"lat\"") || line.contains("\"lng\"") || line.contains("\"status\" : \"ZERO_RESULTS\"")){
                    stringBuilder.append(line + "\n"); }
                }
                return stringBuilder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                // close the reader; this can throw an exception too, so
                // wrap it in another try/catch block.
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
            }
        }
    }







}
