package deweyamoroso.roadtrip;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import com.google.android.gms.maps.model.*;
import android.graphics.Color;

/**
 * @author Tobin Dewey <deweyt16@gmail.com>
 * @version 1.0
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    //initializes map to be displayed
    //WIP: So far this map activity has acted as testbed of project and is currently a jumble of
    //testcode and "proof of concepts" to be used elsewhere in project.
    //This includes initializing google maps api properly, geocoding, calculating distance between latlng coords, and displaying markers.
    //Important to make sure before deleting any code that it has been copied elsewhere or otherwise saved.
    //EVENTUALLY input will simply be a list of nodes. LatLng coords will be extracted from each node and displayed as a marker on the map.
    //And a polyline will be drawn in order between the nodes to show the complete route.


    private GoogleMap mMap;
    String[] locations;

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
        locations = extras.getStringArray("EXTRA_MESSAGE1");
        for(int i = 0; i < locations.length; i++){System.out.println(locations[i]);}

    }
    //Simple method when clicking Go Back button. This ends the current activity and displays previous activity.
    //Note: Previous activity is still stored within memory so doing this does NOT cause results to be recalculated. (as far as I know)
    ///JAVADOC HERE///
    public void GoBack(View view){
        setContentView(R.layout.activity_generated_route_results);
        finish();
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
    ///JAVADOC HERE///
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Polyline route = mMap.addPolyline(new PolylineOptions()
                .width(8)
                .color(Color.BLUE));
        List<LatLng> points  = route.getPoints();
        for(int i = 0; i < locations.length; i++){
            LatLng nodelocation = new LatLng(Double.parseDouble(locations[i]), Double.parseDouble(locations[i+1]));
            mMap.addMarker(new MarkerOptions().position(nodelocation));
            points.add(nodelocation);
            if(i == 0){mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nodelocation, 14.0f));}
            i++;
        }
        route.setPoints(points);
    }
}
