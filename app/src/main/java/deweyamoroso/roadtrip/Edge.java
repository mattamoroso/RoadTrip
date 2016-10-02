/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deweyamoroso.roadtrip;

/**
 *
 * @author mattamoroso
 */

public class Edge {
   
   //distance is the length between two nodes that this edge connects
   private int distance;
   
   //node A and node B are the endpoints for this edge
   private Node node_A;
   private Node node_B;
   
   //edge constructor
   //d is temporary
   public Edge(Node a, Node b, int d){
       node_A = a;
       node_B = b;
       
       //temporary
       distance = d;
       
       setEdges(node_A, node_B);
   }
   
   //tells each node that this edge connects that this edge exists
   public void setEdges(Node a, Node b){
        a.setEdge(this);
        b.setEdge(this);
    }

    // -T
    public int getDistance(){
        return distance;
    }

    //Returns neighbor node. Only called from traversal.djikstra. Returns error if node provided is not a member of the edge. -T
    public Node getNeighbor(Node n){
        Node ret = new Node(0, 0);
    if ((node_A.getLongitude() != n.getLongitude()) && (node_B.getLongitude() != n.getLongitude())){
        System.out.println("ERROR: edge.getNeighbor, incorrect input");
    }
    else if(node_A.getLongitude() != n.getLongitude()){
         ret = node_A;
    }
    else {
         ret = node_B;
    }
        return ret;
    }
   
   //For testing
   public String toString(){
       String s = "";
       s += (node_A.toString());
       s += (node_B.toString());
       s += (distance + "");
       
       return s;
   }
   
   
   
   
}