package deweyamoroso.roadtrip;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


//For the google places api test. TESTING ONLY.
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Oncreate function initializes page and links to xml file.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Generate Roadtrip button */
    public void GenerateRoute(View view) {

//intent method sets up and starts new activity, not 100% sure about everything it does
        Intent intent = new Intent(this, GeneratedRouteResults.class);

        //initializing EditText variables and linking them to EditText sections of xml file. Note: EditText creates space for user input.
        EditText dl = (EditText) findViewById(R.id.departure_location);
        EditText dt = (EditText) findViewById(R.id.departure_time);
        EditText al = (EditText) findViewById(R.id.arrival_location);
        EditText at = (EditText) findViewById(R.id.arrival_time);
        //Grabs Text attribute (what user types in) of EditText sections of xml and casts them as Strings.
        String departure_location = dl.getText().toString();
        String departure_time = dt.getText().toString();
        String arrival_location = al.getText().toString();
        String arrival_time = at.getText().toString();
        //this is just a test string to test traditional java code
        Node node1 = initialize(departure_location);

        //Intent method used to pass variables along to next activity's onCreate method. Will eventually pass list/array of nodes.

        intent.putExtra("EXTRA_MESSAGE1", departure_location);
        intent.putExtra("EXTRA_MESSAGE2", departure_time);
        intent.putExtra("EXTRA_MESSAGE3", arrival_location);
        intent.putExtra("EXTRA_MESSAGE4", arrival_time);
        //intent.putExtra("EXTRA_MESSAGE5", s1);


        //Starts activity at onCreate method.
        startActivity(intent);
    }

    //Initialization method. WIP: Will act as root method which branches into other methods in order to build complete list/array of nodes to be passed to results activity.
    // This method will eventually take all user inputs as arguments.
    // Currently: initializes starting node given departure_location String.
    public Node initialize(String departure1) {
    double lat=0, lon=0;

        try {
            Geocoder g;
            g = new Geocoder(this);
            List<Address> addressList = g.getFromLocationName(departure1, 1);
            lat = (addressList.get(0).getLatitude());
            lon = (addressList.get(0).getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Node departure = new Node(departure1, lat, lon);
        return departure;
    }


}
