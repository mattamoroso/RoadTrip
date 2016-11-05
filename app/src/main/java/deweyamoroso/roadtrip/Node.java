/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deweyamoroso.roadtrip;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Geocoder;
import android.location.Address;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import android.widget.TextView;




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


    //filled in by djikstra's method in traversal
    private int Djik_Distance;
    private Node Djik_Predecessor;
    
    //edges array contains each edge that branches from this node
    //edge index goes up by 1 when we add an edge to this node
    //this will become an arrayList or linkedList
    //10 spots is temporary
    public Edge edges[] = new Edge[10];
    private int edgeIndex = 0;
    
    //utility measures the "value" of an activity
    private Utility utility;
    
    //node constructor
    public Node(String name, String address, double latitude, double longitude, String time_arrived, String time_left){
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
        this.time_arrived = time_arrived;
        this.time_left = time_left;
    }
    
    //called by traversal.djikstras to set shortest distance from source node to relevant node. -T
    public void setDjik_Distance(int n){
        Djik_Distance = n;
    }

    public int getDjik_Distance(){
        return Djik_Distance;
    }

    //Called by traversal.djikstras in order to record shortest path between source node and any particular point. -T
    public void setDjik_Predecessor(Node n){
        Djik_Predecessor = n;
    }

    public Node getDjik_Predecessor(){
        return Djik_Predecessor;
    }

    //Called by traversal.djikstras, returns number of edges branching off the node -T
    public int getNeighborCount(){
        return edgeIndex;
    }

    // -T
    public Edge getEdge(int index){
       return edges[index];
    }


    //called by Edge constructor to tell this node it is connected to a given edge
    //increments edgeindex by 1
    public void setEdge(Edge e){
        edges[edgeIndex] = e;
        edgeIndex++;
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

    //time_arrived getter
    public String getTime_Arrived(){
        return time_arrived;
    }

    //time_left getter
    public String getTime_Left(){
        return time_left;
    }
    
    //utility getter -T
    public int getUtility(){
        return utility.calcUtility();
    }
    
}