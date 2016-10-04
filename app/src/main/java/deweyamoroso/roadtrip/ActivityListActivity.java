package deweyamoroso.roadtrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.net.*;
import java.io.*;


//THIS ACTIVITY IS ONLY FOR URL REQUEST TESTING PURPOSES AND WILL BE EVENTUALLY REMOVED.


public class ActivityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        Bundle extras = getIntent().getExtras();
        String lat = extras.getString("EXTRA_MESSAGE_LATITUDE");
        String lon = extras.getString("EXTRA_MESSAGE_LONGITUDE");


        String output = "error";
        Log.d("CREATION", "start");
        JavaHttpUrlConnectionReader();
        Log.d("CREATION", "end");
        //TextView layout1 = (TextView) findViewById(R.id.output);
        //layout1.setText("test");
        while(true){
            //testFn();
            break;
        }

    }

    public void testFn(){
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {

        }

        //TextView layout1 = (TextView) findViewById(R.id.output);
        //layout1.setText("test");
    }

    public void JavaHttpUrlConnectionReader()
    {
        try
        {
            String myUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&name=cruise&key=AIzaSyBDOGDt8l-1_ON-1xCQIUwakCWOtqynrEM";
            // if your url can contain weird characters you will want to
            // encode it here, something like this:
            // myUrl = URLEncoder.encode(myUrl, "UTF-8");

            String results = doHttpUrlConnectionAction(myUrl);
            //Log.d("CREATION", results);
            TextView layout1 = (TextView) findViewById(R.id.output);
            layout1.setText("tyest");
        }
        catch (Exception e)
        {
            TextView layout1 = (TextView) findViewById(R.id.output);
            layout1.setText("ts2");
            // deal with the exception in your "controller"
        }
    }

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

    // Exception on HTTPURLConnection.connect method. Consider asynctask to solve. http://stackoverflow.com/questions/15496278/httpurlconnection-is-throwing-exception
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

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15*1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
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



//Hey Matt this was just what I was starting to mess with using AsyncTask before you arrived. -T

/*
public class ActivityListActivity extends AsyncTask<String, Void, String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        Bundle extras = getIntent().getExtras();
        String lat = extras.getString("EXTRA_MESSAGE_LATITUDE");
        String lon = extras.getString("EXTRA_MESSAGE_LONGITUDE");

        String output = "error";

        TextView layout1 = (TextView) findViewById(R.id.output);
        layout1.setText(output);


    }

    protected String doInBackground(String lat, String lon){
        String results = "x";
        return results;
    }
*/

//}








