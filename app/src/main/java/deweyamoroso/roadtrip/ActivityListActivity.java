package deweyamoroso.roadtrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        TextView layout1 = (TextView) findViewById(R.id.output);
        layout1.setText(output);


    }


}







