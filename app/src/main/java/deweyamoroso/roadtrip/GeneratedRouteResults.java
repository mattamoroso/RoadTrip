package deweyamoroso.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.View;

public class GeneratedRouteResults extends AppCompatActivity {

    //onCreate method initializes activity and links to xml file.
    //WIP: Eventually only extra will be array/list of nodes.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_route_results);

        //Bundle object created which holds all variables passed from previous activity.
        Bundle extras = getIntent().getExtras();
        //Strings taken individually from bundle by ID and cast into String objects using getString method.
        String[] nodearray = extras.getStringArray("EXTRA_MESSAGE");

        //for(int i =0; i< nodearray.length; i++){System.out.println(nodearray[i]);} //for debugging purposes

        //builds string to display on screen from elements of nodearray, lats and longs stored separately to be passed to map
        int k = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<nodearray.length; i++){
            String[] locations = new String[nodearray.length / 2];
            sb.append(nodearray[i] + "\n");
            sb.append(nodearray[i+1] + "\n");
            sb.append("\n");
            locations[k] = nodearray[i+2];
            k++;
            locations[k] = nodearray[i+3];
            k++;
            i = i + 3;

        }
        String route_output = sb.toString();

        //String departure_time = extras.getString("EXTRA_MESSAGE2");
        //String arrival_location = extras.getString("EXTRA_MESSAGE3");
        //String arrival_time = extras.getString("EXTRA_MESSAGE4");
        //String message5 = extras.getString("EXTRA_MESSAGE5");


        //Traversal method called. Takes array/list of nodes as input and returns string / array of strings.
        //These strings will be itinerary to be printed below for user. NOTE: Traversal.java not written yet. Below is just an example of how it would be called.
        //Traversal traverse = new Traversal(Node[] nodelist);
        //String[] results = traverse.resultsgen();


        //TextView objects created and then linked by ID to TextView sections denoted in xml file.
        //setText method changes value of Text attribute in xml TextView sections, which is what is displayed on screen.
        TextView layout1 = (TextView) findViewById(R.id.full_route);
        layout1.setText(route_output);
        layout1.setMovementMethod(new ScrollingMovementMethod());
        //TextView layout2 = (TextView) findViewById(R.id.departure_time);
        //layout2.setText(departure_time);
        //TextView layout3 = (TextView) findViewById(R.id.arrival_loc);
        //layout3.setText(arrival_location);
        //TextView layout4 = (TextView) findViewById(R.id.arrival_time);
        //layout4.setText(arrival_time);
        //TextView layout5 = (TextView) findViewById(R.id.randomlatlong);
       // layout5.setText(message5);
    }

    //Method executed upon clicking Map View button.
    public void MapView(View view) {

        //intent method sets up new activity
        //Eventually will just pass along array/list of nodes (which include lats and longs) to display map.
        Intent intent = new Intent(this, MapsActivity.class);
        //initializing TextView variables and linking them to TextView sections of xml file. Note: TextView displays text on screen.
        //TextView dl = (TextView) findViewById(R.id.departure_loc);
        //TextView al = (TextView) findViewById(R.id.arrival_loc);
        //Grabs Text attribute from TextView sections of xml and casts them as Strings.
        //String departure_location = dl.getText().toString();
       // String arrival_location = al.getText().toString();
        //Intent method used to pass variables along to next activity's onCreate method. Will eventually pass list/array of nodes.
        //intent.putExtra("EXTRA_MESSAGE1", locations);
        //intent.putExtra("EXTRA_MESSAGE3", arrival_location);

        startActivity(intent);
    }
}
