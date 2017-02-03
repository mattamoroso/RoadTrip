package deweyamoroso.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.View;

/**
 * @author Tobin Dewey <deweyt16@gmail.com>
 * @version 1.0
 */
public class GeneratedRouteResults extends AppCompatActivity {
    String[] locations;

    //onCreate method initializes activity and links to xml file.
    //WIP: Eventually only extra will be array/list of nodes.
    @Override
    ///JAVADOC HERE///
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_route_results);

        //Bundle object created which holds all variables passed from previous activity.
        Bundle extras = getIntent().getExtras();
        //Strings taken individually from bundle by ID and cast into String objects using getString method.
        String[] nodearray = extras.getStringArray("EXTRA_MESSAGE");

        for(int i =0; i< nodearray.length; i++){System.out.println(nodearray[i]);} //for debugging purposes
        System.out.println();
        //builds string to display on screen from elements of nodearray, lats and longs stored separately to be passed to map
        int k = 0;
        StringBuilder sb = new StringBuilder();
        locations = new String[nodearray.length / 4];


        locations[k] = nodearray[2];
        k++;
        locations[k] = nodearray[3];
        k++;
        nodearray[5] = nodearray[5].substring(0, (nodearray[5].length() - 9));
        sb.append("You will begin your road trip by leaving "+ nodearray[1] +"\n");
        sb.append("on "+ nodearray[5] +".\n");
        sb.append("\n");

int i = 0;
        for(i = 8; i<nodearray.length-8; i++){
            nodearray[i] = nodearray[i].substring(19, (nodearray[i].length() - 2));
            nodearray[i+1] = nodearray[i+1].substring(23, (nodearray[i+1].length() - 1));
            nodearray[i+4] = nodearray[i+4].substring(0, (nodearray[i+4].length() - 9));
            nodearray[i+5] = nodearray[i+5].substring(0, (nodearray[i+5].length() - 9));

            sb.append("Then travel to "+ nodearray[i] +",\n");
            sb.append("a "+ nodearray[i+6] +" at "+ nodearray[i + 1]+".\n");
            sb.append("You should arrive around "+ nodearray[i + 4] +",\n");
            sb.append("and leave around "+ nodearray[i + 5] +".\n");

            locations[k] = nodearray[i+2];
            k++;
            locations[k] = nodearray[i+3];
            k++;
            sb.append("\n");
            i = i + 7;

        }

        locations[k] = nodearray[i+2];
        k++;
        locations[k] = nodearray[i+3];
        k++;
        nodearray[i+5] = nodearray[i+5].substring(0, (nodearray[i+5].length() - 9));
        sb.append("You will finally arrive at your destination "+ nodearray[i+1] +"\n");
        sb.append("on "+ nodearray[i+4] +".\n");
        sb.append("\n");




        String route_output = sb.toString();







        //TextView objects created and then linked by ID to TextView sections denoted in xml file.
        //setText method changes value of Text attribute in xml TextView sections, which is what is displayed on screen.
        TextView layout1 = (TextView) findViewById(R.id.full_route);
        layout1.setText(route_output);
        layout1.setMovementMethod(new ScrollingMovementMethod());
    }

    public void GoBack2(View view){
        setContentView(R.layout.activity_main);
        finish();
    }

    //Method executed upon clicking Map View button.
    public void MapView(View view) {

        //intent method sets up new activity
        //Eventually will just pass along array/list of nodes (which include lats and longs) to display map.
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("EXTRA_MESSAGE1", locations);

        startActivity(intent);
    }
}
