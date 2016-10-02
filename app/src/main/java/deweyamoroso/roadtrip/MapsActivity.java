package deweyamoroso.roadtrip;

import android.content.Intent;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.fitness.request.ListClaimedBleDevicesRequest;
//import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import java.util.List;
import android.widget.TextView;



import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    //initializes map to be displayed
    //WIP: So far this map activity has acted as testbed of project and is currently a jumble of
    //testcode and "proof of concepts" to be used elsewhere in project.
    //This includes initializing google maps api properly, geocoding, calculating distance between latlng coords, and displaying markers.
    //Important to make sure before deleting any code that it has been copied elsewhere or otherwise saved.
    //EVENTUALLY input will simply be a list of nodes. LatLng coords will be extracted from each node and displayed as a marker on the map.
    //And a polyline will be drawn in order between the nodes to show the complete route.


    private GoogleMap mMap;
    private double lat, lon, lat2, lon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                //Links fragment object to xml by id.
                .findFragmentById(R.id.map);
        //mapasync method has to do with not displaying map until google api request finishes.
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        String message1 = extras.getString("EXTRA_MESSAGE1");
        String message2 = extras.getString("EXTRA_MESSAGE3");

        //Meat of activity. Uses geocoder object to obtain lats and longs from location addresses.
        try {
        Geocoder g;
        g = new Geocoder(this);
        List<Address> addressList = g.getFromLocationName(message1, 1);
        lat = (addressList.get(0).getLatitude());
        lon = (addressList.get(0).getLongitude());

            List<Address> addressList2 = g.getFromLocationName(message2, 1);
            lat2 = (addressList2.get(0).getLatitude());
            lon2 = (addressList2.get(0).getLongitude());


    } catch (IOException e) {
        e.printStackTrace();
    }

//TEST CODE; WRITTEN HERE JUST FOR EASY ACCESS TO LATS AND LONGS
        // TEST CODE WHICH ESTIMATES DISTANCE BETWEEN TWO SETS OF LATS AND LONGS
Location departure = new Location("");
        departure.setLatitude(lat);
        departure.setLongitude(lon);
        Location arrival = new Location("");
        arrival.setLatitude(lat2);
        arrival.setLongitude(lon2);
        double distance = departure.distanceTo(arrival);
        distance = (distance / 1609.34);
        String distance1 = "DISTANCE IN MILES:"+distance+"";
        TextView layout = (TextView) findViewById(R.id.distance);
        layout.setText(distance1);


    }
    //Simple method when clicking Go Back button. This ends the current activity and displays previous activity.
    //Note: Previous activity is still stored within memory so doing this does NOT cause results to be recalculated. (as far as I know)
    public void GoBack(View view){
        finish();
    }

    //Method for loading activity page. Only exists for testing/figuring out url requests. Sends along lat/long to be possibly used as inputs for url.
    public void Activities(View view){


        Intent intent = new Intent(this, ActivityListActivity.class);
        intent.putExtra("EXTRA_MESSAGE_LAT", lat);
        intent.putExtra("EXTRA_MESSAGE_LON", lon);
        startActivity(intent);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in the two locations and move the camera
        LatLng departure = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(departure).title("Marker at Departure"));
        LatLng arrival = new LatLng(lat2, lon2);
        mMap.addMarker(new MarkerOptions().position(arrival).title("Marker at Arrival"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(departure));
    }
}
